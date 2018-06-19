package com;

import com.controllers.MainAppView;
import com.exceptions.LogOutException;
import com.exceptions.SignInException;
import com.google.gson.Gson;
import com.model.MasinaPunctControl;
import com.model.TSUser;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.RequestType;
import com.net.ResponseType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public abstract class AbstractClientService implements Runnable{

    protected MainAppView mainAppView;
    protected Gson gson = new Gson();

    public AbstractClientService(){}

    public void setMainClientView(MainAppView mainClientView) {
        this.mainAppView = mainClientView;
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

    public ObservableList<MasinaTableEntry> getAllMasini(TSUser user){
        NetRequest request = new NetRequest(RequestType.GetMasini, gson.toJson(user));
        NetResponse response = handleRequest(request);


        MasinaPunctControl[] masinaPunctControls2 = gson.fromJson(response.getjSonResponse(), MasinaPunctControl[].class);
        List<MasinaPunctControl> masinaPunctControls = Arrays.asList(masinaPunctControls2);
        List<MasinaTableEntry> intrebareList = masinaPunctControls.stream().map((c) -> new MasinaTableEntry(c.getMasina().getId(), c.getMasina().getNume(), c.getPunctControl().getNumarControl(), c.getTimpTrecere())).collect(Collectors.toList());
        ObservableList<MasinaTableEntry> observableList= FXCollections.observableArrayList(intrebareList);

        return observableList;
    }

//    public TestCultura handleNewGame(TSUser user){
//        NetRequest request = new NetRequest(RequestType.REQUEST_NEW_GAME, gson.toJson(user));
//        NetResponse response = handleRequest(request);
//
//        if(response.getMessage().contains("Start")){
//            return gson.fromJson(response.getjSonResponse(), TestCultura.class);
//        }else{
//            return null;
//        }
//    }
//
//    public ObservableList<Game> sendGameOver(Game game) {
//        NetRequest request  = new NetRequest(RequestType.REQUEST_GAME_OVER, gson.toJson(game));
//        NetResponse response = handleRequest(request);
//
//        if(response.getMessage().contains("OVER")){
//            Game[] gameArray = gson.fromJson(response.getjSonResponse(),Game[].class);
//            List<Game> gameList = Arrays.asList(gameArray);
//            ObservableList<Game> observableList= FXCollections.observableArrayList(gameList);
//            return observableList;
//        }else{
//            return null;
//        }
//    }

    public abstract NetResponse handleRequest(NetRequest request);

    public abstract void run();

    public void notificationRecieved(NetResponse response){
        System.out.println("I call update");

//        if (response.getType() == ResponseType.NOTIFY_GAME_START) {
//            Platform.runLater(() -> {
//                mainAppView.initNewGame(gson.fromJson(response.getjSonResponse(), TestCultura.class));
//            });
//        }else if(response.getType() == ResponseType.NOTIFY_GAME_OVER){
//            Platform.runLater(()->{
//
//                Game[] gameArray = gson.fromJson(response.getjSonResponse(),Game[].class);
//                List<Game> gameList = Arrays.asList(gameArray);
//                ObservableList<Game> observableList= FXCollections.observableArrayList(gameList);
//
//                resultsController.setObservableList(observableList);
//            });
//        }
    }
}
