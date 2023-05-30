package com.jadifans.opert;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StationAdder implements Initializable {

    Stage stage;

    public Button saveStation;
    public Button cancelButton;

    public void closeWindow(MouseEvent mouseEvent) {
       stage = (Stage)((Button)mouseEvent.getSource()).getScene().getWindow();
       stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveStation.setOnMouseClicked(this::saveStation);
    }

    public void saveStation(MouseEvent mouseEvent) {
        stage = (Stage)((Button)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
