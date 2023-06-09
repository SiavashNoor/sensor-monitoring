package com.jadifans.opert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ApplicationSettings implements Initializable {


    Stage stage;
    Desktop desktop = Desktop.getDesktop();

    @FXML
    public ChoiceBox<String> periodChoiceBox;


    private final String[] period = {"Instantly","Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
    public Button settingsCancelButton;
    public Button settingsSaveButton;
    public Button addNewStationButton;
    public Button settingsImportButton;
    public TextField ipAddressField;
    public TextField portNumberField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpChoiceBox();
        periodChoiceBox.setOnAction(this::getPeriodValueFromChoiceBox);
        ipAddressField.setOnMouseClicked(this::getIPAddressValue);
        portNumberField.setOnMouseClicked(this::getPortNumberValue);
        settingsImportButton.setOnMouseClicked(this::importSettings);
        addNewStationButton.setOnMouseClicked(this::openAddStationWindow);
    }

    public void setUpChoiceBox() {
        periodChoiceBox.getItems().addAll(period);
        periodChoiceBox.setValue(period[0]);
    }


    public String getPeriodValueFromChoiceBox(ActionEvent event) {
        String choiceBoxValue = periodChoiceBox.getValue();

        if (choiceBoxValue == null) {
            setUpChoiceBox();
        } else {
            switch (choiceBoxValue.toLowerCase()) {

                case "hourly":
                    System.out.println("you have chosen hourly");
                    //create hourly data series  and update the chart .

                    break;
                case "daily":
                    System.out.println("you have chosen daily");
                    break;
                case "weekly":
                    System.out.println("you have chosen weekly");
                    break;
                case "monthly":
                    System.out.println("you have chosen monthly");
                    break;
                case "yearly":
                    System.out.println("you have chosen yearly");
                    break;
                default:
                    System.out.println("by default hourly period is chosen for you:)");

            }
        }
        return choiceBoxValue;
    }

    public String getPeriodValueFromChoiceBox() {
        return periodChoiceBox.getValue();
    }

    public void getIPAddressValue(MouseEvent event) {
        System.out.println(ipAddressField.getText());
    }


    public void getPortNumberValue(MouseEvent event) {
        System.out.println(portNumberField.getText());
    }


    public void saveSettings(MouseEvent mouseEvent) {
        stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        getIPAddressValue(mouseEvent);
        getPortNumberValue(mouseEvent);

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);

        stage.close();
    }

    public void cancelSettings(MouseEvent mouseEvent) {
        stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void openAddStationWindow(MouseEvent mouseEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("stationAdder.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Add New Station");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }


    public void importSettings(MouseEvent mouseEvent) {

        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);
        /*if (file != null) {
            openFile(file);
        }*/
    }

    public void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ignored) {
        }

    }


}
