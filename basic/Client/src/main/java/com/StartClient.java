package com;

import com.controllers.LoginView;
import com.model.TSUser;
import com.net.RabMQClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClient extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MPP Exam!");

        StartClient.primaryStage = primaryStage;

        AbstractClientService clientService = new RabMQClient();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        LoginView controller = fxmlLoader.<LoginView>getController();

        controller.setClientService(clientService);

        Scene scene = new Scene(root,800,500);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(clientService).start();
    }
}
