package com.controllers;

import com.AbstractClientService;
import com.StartClient;
import com.exceptions.LogOutException;
import com.model.Game;
import com.model.Intrebare;
import com.model.TSUser;
import com.model.TestCultura;
import com.utils.ModelTableViewBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class MainAppView {


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


    @FXML private void logOutButtonPressed(){
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


    TestCultura currentTest;
    Intrebare selectedIntrebare;

    public TestCultura getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(TestCultura currentTest) {
        this.currentTest = currentTest;
    }

    public Intrebare getSelectedIntrebare() {
        return selectedIntrebare;
    }

    public void setSelectedIntrebare(Intrebare selectedIntrebare) {
        this.selectedIntrebare = selectedIntrebare;
    }


    // ------------------------------------------------------
    // UI SETUP


    private Integer points = 0;

    @FXML
    VBox container;


    TableView<Intrebare> tableView;

    ObservableList<Intrebare> observableList;

    @FXML
    Label userLoggedLabel;


    @FXML Label idlabel;
    @FXML Label contentLabel;
    @FXML Label answerLabel;

    @FXML TextField idField;
    @FXML TextField contentField;
    @FXML TextField answerField;

    @FXML Button adaugaIntrebareButton;

    @FXML Button startNewGame;


    @FXML private void initialize(){}


    public void setupView(){
        this.tableView = ModelTableViewBuilder.buildUpon(Intrebare.class);

        System.out.println("Fac setup for " + currentTest);
        if(currentTest != null){
            this.observableList = clientService.getAllQuestionsForTest(currentTest);
            tableView.setItems(observableList);
        }


        container.getChildren().add(tableView);


        tableView.setRowFactory(tv -> {
            TableRow<Intrebare> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    Intrebare clickedRow = row.getItem();
                    selectedIntrebare = clickedRow;
                    idField.setText(String.valueOf(clickedRow.getId()));
                    contentField.setText(clickedRow.getContent());
                }
            });
            return row ;
        });
    }

    @FXML void adaugaIntrebarePressed(){
        if(selectedIntrebare != null && answerField.getText().length() > 0){
            if(selectedIntrebare.getCorrectAnswer().equals(answerField.getText())){
                points++;
            }
            System.out.println(points);
            observableList.remove(selectedIntrebare);
            tableView.refresh();
            if(observableList.size() == 0){
                System.out.println("game over");
                Game game = new Game(loggedInUser,currentTest,points);
                segueToResults(game);
            }
        }
    }

    public void updateParticipants(){
        if(currentTest != null){
            this.observableList = clientService.getAllQuestionsForTest(currentTest);
            this.tableView.setItems(observableList);
            tableView.refresh();
        }
    }

    public void displayStartButton(){
        this.contentField.setVisible(false);
        this.idField.setVisible(false);
        this.answerField.setVisible(false);
        this.container.setVisible(false);
        this.adaugaIntrebareButton.setVisible(false);
        this.idlabel.setVisible(false);
        this.contentLabel.setVisible(false);
        this.answerLabel.setVisible(false);
    }

    public void displayAllPage(){
        this.contentField.setVisible(true);
        this.idField.setVisible(true);
        this.answerField.setVisible(true);
        this.container.setVisible(true);
        this.idlabel.setVisible(true);
        this.contentLabel.setVisible(true);
        this.answerLabel.setVisible(true);
        this.adaugaIntrebareButton.setVisible(true);

        this.startNewGame.setVisible(false);
    }

    @FXML public void startNewGamePressed(){
        TestCultura testCultura = clientService.handleNewGame(loggedInUser);
        if(testCultura == null){
            warning("I have to wait for others");
            startNewGame.setDisable(true);
        }else{
            initNewGame(testCultura);
        }
    }

    public void initNewGame(TestCultura testCultura){
        System.out.println("Current test: " + testCultura);
        setCurrentTest(testCultura);
        displayAllPage();
        setupView();
    }

    public void segueToResults(Game game){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resultsView.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ResultsController controller = fxmlLoader.<ResultsController>getController();
        controller.setClientService(clientService);


        ObservableList<Game> gamesList = clientService.sendGameOver(game);
        if(gamesList != null){
            controller.setObservableList(gamesList);
        }
        controller.setLoggedInUser(loggedInUser);
        clientService.setResultsView(controller);


        Scene scene = new Scene(root,800,500);

        StartClient.primaryStage.setScene(scene);
        StartClient.primaryStage.show();
    }
}
