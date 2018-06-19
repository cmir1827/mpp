package com.controllers;

import com.AbstractClientService;
import com.MasinaTableEntry;
import com.StartClient;
import com.exceptions.LogOutException;
import com.model.MasinaPunctControl;
import com.model.TSUser;
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
import java.time.LocalDate;

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


    MasinaTableEntry selectedMasina;

    public MasinaTableEntry getSelectedMasina() {
        return selectedMasina;
    }

    public void setSelectedMasina(MasinaTableEntry selectedMasina) {
        this.selectedMasina = selectedMasina;
    }

    // ------------------------------------------------------
    // UI SETUP


    @FXML
    VBox container;

    TableView<MasinaTableEntry> tableView;

    ObservableList<MasinaTableEntry> observableList;

    @FXML
    Label userLoggedLabel;


    @FXML Label idlabel;
    @FXML Label contentLabel;
    @FXML Label answerLabel;

    @FXML TextField idField;
    @FXML TextField contentField;
    @FXML TextField answerField;


    @FXML private void initialize(){
    }

    public void setupView(){
        this.tableView = ModelTableViewBuilder.buildUpon(MasinaTableEntry.class);

        this.observableList = clientService.getAllMasini(loggedInUser);
        tableView.setItems(observableList);

        container.getChildren().add(tableView);


        tableView.setRowFactory(tv -> {
            TableRow<MasinaTableEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    MasinaTableEntry clickedRow = row.getItem();
                    selectedMasina = clickedRow;
                    idField.setText(String.valueOf(clickedRow.getId()));
                    contentField.setText(clickedRow.getNume());
                }
            });
            return row ;
        });
    }

    @FXML void inregistreazaIntrebarePressed(){
        if(selectedMasina != null && answerField.getText().length() > 0 && !loggedInUser.getUsername().equals("user1")){
            observableList.remove(selectedMasina);
            tableView.refresh();

            clientService.handlePassCheckpoint(new MasinaPunctControl(selectedMasina.getMasina(), selectedMasina.getPunctControl(), LocalDate.parse(answerField.getText())));
        }
    }

    public void addNewCar(MasinaPunctControl masinaPunctControl) {
        this.observableList.add(new MasinaTableEntry(masinaPunctControl.getId(), masinaPunctControl.getMasina().getNume(), masinaPunctControl.getPunctControl().getNumarControl(), masinaPunctControl.getTimpTrecere(), masinaPunctControl.getMasina(), masinaPunctControl.getPunctControl()));
    }
}
