package com.controllers;

import com.AbstractClientService;
import com.StartClient;
import com.exceptions.LogOutException;
import com.model.Game;
import com.model.Intrebare;
import com.model.TSUser;
import com.utils.ModelTableViewBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class ResultsController {


    // ------------------------------------------------------
    // CLIENT SERVICE AND USER SETUP

    private AbstractClientService clientService;

    public void setClientService(AbstractClientService clientService) {
        this.clientService = clientService;
    }

    private TSUser loggedInUser;

    public TSUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(TSUser loggedInUser) {
        this.loggedInUser = loggedInUser;
        userLoggedLabel.setText(loggedInUser.getUsername());
    }

    // ------------------------------------------------------
    // LOG OUT BUTTON SETUP


    @FXML
    private void logOutButtonPressed(){
        try {
            clientService.sendLogOutRequest(loggedInUser);
            segueToLogInView();
        } catch (LogOutException e) {
            System.out.println(e.getMessage());
        }
    }

    void segueToLogInView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginView controller = fxmlLoader.<LoginView>getController();
        controller.setClientService(clientService);

        Scene scene = new Scene(root,800,500);

        StartClient.primaryStage.setScene(scene);
        StartClient.primaryStage.show();
    }


    // ------------------------------------------------------
    // WARNING AND ERROR BOXES


    public void warning(String war) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(war);
        alert.showAndWait();
    }

    private void error(String err) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(err);
        alert.showAndWait();
    }


    // ------------------------------------------------------
    // MEMBERS

    @FXML
    Label userLoggedLabel;

    @FXML
    VBox container;

    TableView<Game> tableView;

    ObservableList<Game> observableList;

    public ObservableList<Game> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<Game> observableList) {
        this.observableList = observableList;

        this.tableView = ModelTableViewBuilder.buildUpon(Game.class);

        tableView.setItems(observableList);

        this.container.getChildren().add(tableView);
    }
}
