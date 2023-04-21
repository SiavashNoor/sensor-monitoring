package com.jadifans.opert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    private final String[] period = {"Daily", "Weekly", "Monthly", "Yearly"};
    public Button settingsCancelButton;
    public Button settingsSaveButton;

    public Button settingsImportButton;
    public TextField ipAddressField;
    public TextField portNumberField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        periodChoiceBox.getItems().addAll(period);
        periodChoiceBox.setValue(period[0]);
        periodChoiceBox.setOnAction(this::getPeriodValueFromChoiceBox);
        ipAddressField.setOnMouseClicked(this::getIPAddressValue);
        portNumberField.setOnMouseClicked(this::getPortNumberValue);
        settingsImportButton.setOnMouseClicked(this::importSettings);



    }

    public void getPeriodValueFromChoiceBox(ActionEvent event) {
        String choiceBoxValue = periodChoiceBox.getValue();
        System.out.println("choice box value is " + choiceBoxValue);
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



    public void importSettings(MouseEvent mouseEvent) {

        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);
        /*if (file != null) {
            openFile(file);
        }*/
    }
    public void openFile (File file){
        try {
            desktop.open(file);
        } catch (IOException ignored) {
        }


    }
}
