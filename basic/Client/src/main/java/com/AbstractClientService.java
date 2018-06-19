package com;

import com.controllers.MainAppView;
import com.controllers.ResultsController;
import com.exceptions.LogOutException;
import com.exceptions.SignInException;
import com.google.gson.Gson;
import com.model.Game;
import com.model.Intrebare;
import com.model.TSUser;
import com.model.TestCultura;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.RequestType;
import com.net.ResponseType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public abstract class AbstractClientService implements Runnable{

    protected MainAppView mainAppView;
    protected ResultsController resultsController;
    protected Gson gson = new Gson();

    public AbstractClientService(){}

    public void setMainClientView(MainAppView mainClientView) {
        this.mainAppView = mainClientView;
    }

    public void setResultsView(ResultsController controller) {
        this.resultsController = controller;
    }

    public Integer sendSignInRequest(TSUser user) throws SignInException {
        NetRequest bsRequest = new NetRequest(RequestType.SignIn, gson.toJson(user));
        NetResponse response = handleRequest(bsRequest);
        if(response.getType() == ResponseType.ERROR)
            throw new SignInException(response.getMessage());
        return gson.fromJson(response.getjSonResponse(), Integer.class);
    }

    public boolean sendLogOutRequest(TSUser user) throws LogOutException{
        NetRequest request = new NetRequest(RequestType.LogOut, gson.toJson(user));
        NetResponse response = handleRequest(request);
        if(response.getType() == ResponseType.ERROR)
            throw new LogOutException(response.getMessage());
        return true;
    }

    public ObservableList<Intrebare> getAllQuestionsForTest(TestCultura testCultura){
        NetRequest request = new NetRequest(RequestType.GetAllQuestionsForTest, gson.toJson(testCultura));
        NetResponse response = handleRequest(request);


        Intrebare[] intrebareArray = gson.fromJson(response.getjSonResponse(),Intrebare[].class);
        List<Intrebare> intrebareList = Arrays.asList(intrebareArray);
        ObservableList<Intrebare> observableList= FXCollections.observableArrayList(intrebareList);

        return observableList;
    }

    public TestCultura handleNewGame(TSUser user){
        NetRequest request = new NetRequest(RequestType.REQUEST_NEW_GAME, gson.toJson(user));
        NetResponse response = handleRequest(request);

        if(response.getMessage().contains("Start")){
            return gson.fromJson(response.getjSonResponse(), TestCultura.class);
        }else{
            return null;
        }
    }

    public ObservableList<Game> sendGameOver(Game game) {
        NetRequest request  = new NetRequest(RequestType.REQUEST_GAME_OVER, gson.toJson(game));
        NetResponse response = handleRequest(request);

        if(response.getMessage().contains("OVER")){
            Game[] gameArray = gson.fromJson(response.getjSonResponse(),Game[].class);
            List<Game> gameList = Arrays.asList(gameArray);
            ObservableList<Game> observableList= FXCollections.observableArrayList(gameList);
            return observableList;
        }else{
            return null;
        }
    }

    public abstract NetResponse handleRequest(NetRequest request);

    public abstract void run();

    public void notificationRecieved(NetResponse response){
        System.out.println("I call update");

        if (response.getType() == ResponseType.NOTIFY_GAME_START) {
            Platform.runLater(() -> {
                mainAppView.initNewGame(gson.fromJson(response.getjSonResponse(), TestCultura.class));
            });
        }else if(response.getType() == ResponseType.NOTIFY_GAME_OVER){
            Platform.runLater(()->{

                Game[] gameArray = gson.fromJson(response.getjSonResponse(),Game[].class);
                List<Game> gameList = Arrays.asList(gameArray);
                ObservableList<Game> observableList= FXCollections.observableArrayList(gameList);

                resultsController.setObservableList(observableList);
            });
        }
    }
}
