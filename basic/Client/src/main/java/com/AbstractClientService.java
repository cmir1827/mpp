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
        List<MasinaTableEntry> intrebareList = masinaPunctControls.stream().map((c) -> new MasinaTableEntry(c.getMasina().getId(), c.getMasina().getNume(), c.getPunctControl().getNumarControl(), c.getTimpTrecere(), c.getMasina(), c.getPunctControl())).collect(Collectors.toList());
        ObservableList<MasinaTableEntry> observableList= FXCollections.observableArrayList(intrebareList);

        return observableList;
    }

    public boolean handlePassCheckpoint(MasinaPunctControl punctControl) {
        NetRequest request = new NetRequest(RequestType.PassCheckpoint, gson.toJson(punctControl));
        NetResponse response = handleRequest(request);

        if (response.getType() == ResponseType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public abstract NetResponse handleRequest(NetRequest request);

    public abstract void run();

    public void notificationRecieved(NetResponse response){
        System.out.println("I call update");

        if (response.getType() == ResponseType.Notify_new_car) {
            Platform.runLater(() -> {
                mainAppView.addNewCar(gson.fromJson(response.getjSonResponse(), MasinaPunctControl.class));
            });
        }
    }
}
