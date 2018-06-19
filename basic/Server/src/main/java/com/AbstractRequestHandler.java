package com;

import com.google.gson.Gson;
import com.model.*;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.ResponseType;
import com.repositories.MasinaHBNRepositpry;
import com.repositories.MasinaPunctControlRepository;
import com.repositories.PunctControlRepository;
import com.repositories.UserRepo;
import com.services.MasinaPunctControlService;
import com.services.MasinaService;
import com.services.PunctControlService;
import com.services.UsersService;
import com.util.JdbcUtils;
import jdk.nashorn.internal.scripts.JD;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public abstract class AbstractRequestHandler implements Runnable{

    Gson gson = new Gson();

    UsersService usersService = new UsersService(new UserRepo(JdbcUtils.getProps()));
    MasinaService masinaService = new MasinaService(new MasinaHBNRepositpry());
    PunctControlService punctControlService = new PunctControlService(new PunctControlRepository(JdbcUtils.getProps()));
    MasinaPunctControlService masinaPunctControlService = new MasinaPunctControlService(new MasinaPunctControlRepository(JdbcUtils.getProps()));

    public AbstractRequestHandler() { }

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

    protected NetResponse handleGetMasini(String usrJsonString) {
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);
        System.out.println(user);

        PunctControl punctControl = punctControlService.getAll().stream().filter((p) -> p.getUser().getUserId() == user.getUserId()).findFirst().get();

        if (punctControl.getNumarControl() == 0) {
            //send all cars

            List<MasinaPunctControl> masinas = masinaPunctControlService.findAll();

            MasinaPunctControl[] masinaArray = new MasinaPunctControl[masinas.size()];
            masinaArray = masinas.toArray(masinaArray);

            return new NetResponse(ResponseType.OK, "message", gson.toJson(masinaArray));
        } else {
            List<MasinaPunctControl> allMasinas = masinaPunctControlService.findAll();
            List<MasinaPunctControl> masinas = masinaPunctControlService.findAll().stream().filter((p) -> p.getPunctControl().getNumarControl() == (punctControl.getNumarControl() - 1)).collect(Collectors.toList());

            List<MasinaPunctControl> finalList = new ArrayList<>();

            for (MasinaPunctControl masinaPunctControl : masinas) {
                Comparator<MasinaPunctControl> byPunct = new Comparator<MasinaPunctControl>() {
                    @Override
                    public int compare(MasinaPunctControl o1, MasinaPunctControl o2) {
                        return o1.getPunctControl().getNumarControl().compareTo(o2.getPunctControl().getNumarControl());
                    }
                };

                if (allMasinas.stream().filter((pc) -> pc.getMasina().getId() == masinaPunctControl.getMasina().getId()).max(byPunct).get().getId() == masinaPunctControl.getId()) {
                    finalList.add(masinaPunctControl);
                }
            }
            MasinaPunctControl[] masinaArray = new MasinaPunctControl[finalList.size()];
            masinaArray = finalList.toArray(masinaArray);

            return new NetResponse(ResponseType.OK, "message", gson.toJson(masinaArray));
        }
    }

    protected NetResponse handlePassCheckpoint(String punctControlJson) {
        MasinaPunctControl control = gson.fromJson(punctControlJson, MasinaPunctControl.class);

        masinaPunctControlService.save(control);

        List<TSUser> punctControls = masinaPunctControlService.findAll().stream().filter((p) -> p.getPunctControl().getNumarControl() == control.getPunctControl().getNumarControl() + 1 || p.getPunctControl().getNumarControl() == 0).map((t) -> t.getPunctControl().getUser()).collect(Collectors.toList());

        for(TSUser crt : punctControls) {
            if (userMap.containsKey(crt)) {
                sendCustomNotification(userMap[crt], new NetResponse(ResponseType.Notify_new_car, "OK", gson.toJson(control, MasinaPunctControl.class)));
            }
        }

        return new NetResponse(ResponseType.OK, "ok");
    }
//    protected NetResponse handleNewGame(String usrJsonString){
//
//
//        System.out.println("GAMING SERVICE: " + gamingService);
//        PendingGame pendingGame = gamingService.startNewGame(user);
//        System.out.println(pendingGame);
//
//        if(pendingGame != null){
//            //pot sa incep
//            System.out.println(pendingGame.getPlayers());
//            pendingGame.getPlayers().forEach(p->{
//                if (!p.getUsername().equals(user.getUsername()))
//                    sendCustomNotification(userMap.get(p), new NetResponse(ResponseType.NOTIFY_GAME_START, "Game can start", gson.toJson(pendingGame.getTestCultura())));
//            });
//            return new NetResponse(ResponseType.OK, "Start", gson.toJson(pendingGame.getTestCultura()));
//        }else{
//            return new NetResponse(ResponseType.OK, "You still have to wait");
//        }
//    }
//
//    protected NetResponse handleGetAllQuestions(String jsonString) {
//        TestCultura testCultura = gson.fromJson(jsonString, TestCultura.class);
//
//
//        List<Intrebare> intrebareList = questionService.getAll().stream().filter(p -> p.getTestCultura().getId() == testCultura.getId()).collect(Collectors.toList());
//
//        Intrebare[] intrebareArray = new Intrebare[intrebareList.size()];
//        intrebareArray = intrebareList.toArray(intrebareArray);
//
//        return new NetResponse(ResponseType.OK, "OK", gson.toJson(intrebareArray));
//    }
//
//    private NetResponse handleGameOver(String jsonString) {
//        Game game = gson.fromJson(jsonString, Game.class);
//
//        List<Game> games = gamingService.gameOver(game);
//        if(games != null){
//
//            Game[] gameArray = new Game[games.size()];
//            gameArray = games.toArray(gameArray);
//
//
//            games.forEach(p->{
//                Game[] gameArrayLambda = new Game[games.size()];
//                gameArrayLambda = games.toArray(gameArrayLambda);
//
//                if (!p.getUser().getUsername().equals(game.getUser().getUsername()))
//                    sendCustomNotification(userMap.get(p.getUser()), new NetResponse(ResponseType.NOTIFY_GAME_OVER, "OVER", gson.toJson(gameArrayLambda)));
//            });
//
//
//            return new NetResponse(ResponseType.OK, "OVER", gson.toJson(gameArray));
//        }else{
//            return new NetResponse(ResponseType.OK, "Astept rezultate");
//        }
//    }

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
                case GetMasini:
                    response = handleGetMasini(request.getJsonString());
                    return response;
                case PassCheckpoint:
                    response = handlePassCheckpoint(request.getJsonString());
                    return response;
            }
        }
        return null;
    }


    public abstract void sendCustomNotification(String destination, NetResponse response);

    public abstract void sendNotification(NetResponse response);
}
