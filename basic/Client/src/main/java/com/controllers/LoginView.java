package com.controllers;
import com.AbstractClientService;
import com.StartClient;
import com.exceptions.SignInException;
import com.model.TSUser;
import com.model.TestCultura;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by sergiubulzan on 19/06/2017.
 */
public class LoginView {

    private AbstractClientService clientService;

    public void setClientService(AbstractClientService clientService) {
        this.clientService = clientService;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML private void initialize(){
        errorLabel.setText("");
        errorLabel.setAlignment(Pos.CENTER);
    }


    @FXML void signInButtonPressed(){

        errorLabel.setText("");
        if (usernameField.getText().length() < 1 || passwordField.getText().length() < 1){
            errorLabel.setText("Campuri vide!");
            return;
        }

        TSUser user = new TSUser(usernameField.getText(), passwordField.getText());
        try {
            int id = clientService.sendSignInRequest(user);
            user.setUserId(id);
            segueToMainView(user);
        } catch (SignInException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void segueToMainView(TSUser loggedInUser){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainAppWindow.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MainAppView controller = fxmlLoader.<MainAppView>getController();
        controller.setClientService(clientService);


        controller.setLoggedInUser(loggedInUser);
        controller.displayStartButton();
        clientService.setMainClientView(controller);


        Scene scene = new Scene(root,800,500);

        StartClient.primaryStage.setScene(scene);
        StartClient.primaryStage.show();
    }


}
