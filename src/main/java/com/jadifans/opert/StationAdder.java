package com.jadifans.opert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class StationAdder implements Initializable {
    Stage stage;
    ApplicationSettings applicationSettings ;
    @FXML
    public TextField nameField;
    @FXML
    public CheckBox includeTemp;
    @FXML
    public CheckBox includeHum;
    @FXML
    public Button saveStation;
    @FXML
    public Button cancelButton;

    public void closeWindow(MouseEvent mouseEvent) {
       stage = (Stage)((Button)mouseEvent.getSource()).getScene().getWindow();
       stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveStation.setOnMouseClicked(this::addStation);

    }

    public void addStation(MouseEvent mouseEvent) {

        String stationName =nameField.getText();
        if(!stationName.isBlank() && (includeHum.isSelected() || includeTemp.isSelected())){
                applicationSettings.setStationName(stationName,includeTemp.isSelected(),includeHum.isSelected());
                    stage = (Stage)((Button)mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        }else{

            //would make border red , if it was empty
            if(stationName.isBlank()){

                nameField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } if (!includeHum.isSelected()) {
                 includeHum.setStyle("-fx-border-radius: 1px; -fx-check-box-border: #B22222");

            }if(!includeTemp.isSelected()){
                includeTemp.setStyle("-fx-check-box-border: #B22222; -fx-border-color: #B22222; -fx-focus-color: #B22222;");

            }

        }
    }
// this method gets the same instance used for the ApplicationSettings controller this is run in ApplicationSettings controller and there the instance passed through .

    public void setParentController(ApplicationSettings applicationSettings){
        this.applicationSettings = applicationSettings;
    }
}
