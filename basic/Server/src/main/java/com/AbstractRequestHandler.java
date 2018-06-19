package com;

import com.google.gson.Gson;
import com.model.*;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.ResponseType;
import com.repositories.GameRepository;
import com.repositories.IntrebareRepository;
import com.repositories.TestHBNRepository;
import com.repositories.UserRepo;
import com.services.GamingService;
import com.services.QuestionService;
import com.services.TestService;
import com.services.UsersService;
import com.util.JdbcUtils;
import jdk.nashorn.internal.scripts.JD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public abstract class AbstractRequestHandler implements Runnable{

    Gson gson = new Gson();

    UsersService usersService = new UsersService(new UserRepo(JdbcUtils.getProps()));
    TestService testService = new TestService(new TestHBNRepository());
    QuestionService questionService = new QuestionService(new IntrebareRepository(JdbcUtils.getProps()));
    GamingService gamingService;

    public AbstractRequestHandler() {
        gamingService = new GamingService(new GameRepository(JdbcUtils.getProps()));
    }

    public GamingService getGamingService() {
        return gamingService;
    }

    @Override
    public abstract void run() ;

    protected Map<TSUser, String> userMap = new HashMap<>();

    public Map<TSUser, String> getUserMap() {
        return userMap;
    }

    protected NetResponse handleSignIn(String usrJsonString, String senderId){
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);
        TSUser found = usersService.findByUsername(user.getUsername());

        if(found != null){
            if(found.getPassword().equals(user.getPassword())){
                if(this.userMap.containsKey(found)){
                    return new NetResponse(ResponseType.ERROR,"User Already logged in");
                }else{
                    this.userMap.put(found,senderId);
                }
                return new NetResponse(ResponseType.OK, "SignIn cu success", gson.toJson(found.getUserId()));
            }else{
                return new NetResponse(ResponseType.ERROR,"Imi pare rau, parola gresita!" );
            }
        }else{
            return new NetResponse(ResponseType.ERROR, "Imi pare rau, username invalid!" );
        }
    }

    protected NetResponse handleLogOut(String usrJsonString, String senderId){
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);

        Optional<TSUser> foundUser = userMap.keySet().stream().filter(p -> p.getUsername().equals(user.getUsername())).findFirst();
        if (foundUser.isPresent()){
            userMap.remove(foundUser.get());
            return new NetResponse(ResponseType.OK, "Log out cu success");
        }else{
            return new NetResponse(ResponseType.ERROR, "User is not logged in!");
        }
    }

    protected NetResponse handleNewGame(String usrJsonString){
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);
        System.out.println(user);

        System.out.println("GAMING SERVICE: " + gamingService);
        PendingGame pendingGame = gamingService.startNewGame(user);
        System.out.println(pendingGame);

        if(pendingGame != null){
            //pot sa incep
            System.out.println(pendingGame.getPlayers());
            pendingGame.getPlayers().forEach(p->{
                if (!p.getUsername().equals(user.getUsername()))
                    sendCustomNotification(userMap.get(p), new NetResponse(ResponseType.NOTIFY_GAME_START, "Game can start", gson.toJson(pendingGame.getTestCultura())));
            });
            return new NetResponse(ResponseType.OK, "Start", gson.toJson(pendingGame.getTestCultura()));
        }else{
            return new NetResponse(ResponseType.OK, "You still have to wait");
        }
    }

    protected NetResponse handleGetAllQuestions(String jsonString) {
        TestCultura testCultura = gson.fromJson(jsonString, TestCultura.class);


        List<Intrebare> intrebareList = questionService.getAll().stream().filter(p -> p.getTestCultura().getId() == testCultura.getId()).collect(Collectors.toList());

        Intrebare[] intrebareArray = new Intrebare[intrebareList.size()];
        intrebareArray = intrebareList.toArray(intrebareArray);

        return new NetResponse(ResponseType.OK, "OK", gson.toJson(intrebareArray));
    }

    private NetResponse handleGameOver(String jsonString) {
        Game game = gson.fromJson(jsonString, Game.class);

        List<Game> games = gamingService.gameOver(game);
        if(games != null){

            Game[] gameArray = new Game[games.size()];
            gameArray = games.toArray(gameArray);


            games.forEach(p->{
                Game[] gameArrayLambda = new Game[games.size()];
                gameArrayLambda = games.toArray(gameArrayLambda);

                if (!p.getUser().getUsername().equals(game.getUser().getUsername()))
                    sendCustomNotification(userMap.get(p.getUser()), new NetResponse(ResponseType.NOTIFY_GAME_OVER, "OVER", gson.toJson(gameArrayLambda)));
            });

            //Observable
            //i need userMap


//            PendingGame game1 = gamingService.initGameForExistingUsers();
//
//            if (game1 != null) {
//                game1.getPlayers().forEach(user ->{
//                    sendCustomNotification(userMap.get(user), new NetResponse(ResponseType.NOTIFY_GAME_START, "Game can start", gson.toJson(game1.getTestCultura())));
//                });
//            }


            return new NetResponse(ResponseType.OK, "OVER", gson.toJson(gameArray));
        }else{
            return new NetResponse(ResponseType.OK, "Astept rezultate");
        }
    }

    public NetResponse getResponseForRequest(NetRequest request, String senderId){
        if(request != null){
            NetResponse response;
            switch (request.getRequestType()) {
                case SignIn:
                    response = handleSignIn(request.getJsonString(), senderId);
                    return response;
                case LogOut:
                    response = handleLogOut(request.getJsonString(), senderId);
                    return response;
                case REQUEST_NEW_GAME:
                    response = handleNewGame(request.getJsonString());
                    return response;
                case GetAllQuestionsForTest:
                    response = handleGetAllQuestions(request.getJsonString());
                    return response;
                case REQUEST_GAME_OVER:
                    response = handleGameOver(request.getJsonString());
                    return response;
            }
        }
        return null;
    }


    public abstract void sendCustomNotification(String destination, NetResponse response);

    public abstract void sendNotification(NetResponse response);
}
