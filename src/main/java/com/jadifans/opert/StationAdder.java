package com.jadifans.opert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StationAdder implements Initializable {


    StringBuilder errorStringText = new StringBuilder();
    boolean canCloseWindow = true;

    @FXML
    public TextField tempLowerValue;
    @FXML
    public TextField tempUpperValue;
    @FXML
    public Text errorText;
    @FXML
    public CheckBox humHasAlert;
    @FXML
    public CheckBox tempHasUpperThreshold;
    @FXML
    public CheckBox tempHasLowerThreshold;
    @FXML
    public TextField humLowerValue;
    @FXML
    public TextField humUpperValue;
    @FXML
    public CheckBox humHasUpperThreshold;
    @FXML
    public CheckBox humHasLowerThreshold;
    @FXML
    public CheckBox tempHasAlert;
    State state = State.getInstance();
    Stage stage;
    ApplicationSettings applicationSettings;

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
        stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveStation.setOnMouseClicked(this::addStation);
        // if (applicationSettings.isEditMode) {
        //    setupSelectedStation();
        // }
        controlCheckBoxesAndTextFields();
        addValidationListenersToTextField();


    }

    private void addValidationListenersToTextField() {

        tempUpperValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tempHasUpperThreshold.isSelected()) {
                tempUpperValue.setStyle(null);
                try {
                    Integer.parseInt(newValue);
                   //
                    //
                    canCloseWindow = true;

                } catch (NumberFormatException e) {

                    tempUpperValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    canCloseWindow = false;
                }
            } else {
                tempUpperValue.setStyle(null);
            }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        tempLowerValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tempHasLowerThreshold.isSelected()) {
                tempLowerValue.setStyle(null);
                try {
                    Integer.parseInt(newValue);
                    //
                    //
                    canCloseWindow = true;
                } catch (NumberFormatException e) {
                    tempLowerValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    canCloseWindow = false;
                }
            } else {
                tempLowerValue.setStyle(null);
            }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        humUpperValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (humHasUpperThreshold.isSelected()) {
                humUpperValue.setStyle(null);
                try {
                    Integer.parseInt(newValue);
                    //
                    //
                    canCloseWindow = true;
                } catch (NumberFormatException e) {
                    humUpperValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    canCloseWindow = false;
                }
            } else {
                tempUpperValue.setStyle(null);
            }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        humLowerValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (humHasLowerThreshold.isSelected()) {
                humLowerValue.setStyle(null);
                try {
                    Integer.parseInt(newValue);
                    //
                    //
                    canCloseWindow = true;
                } catch (NumberFormatException e) {
                    humLowerValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    canCloseWindow = false;
                }
            } else {
                tempLowerValue.setStyle(null);
            }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

    }

    private void controlCheckBoxesAndTextFields() {
        includeTemp.setOnAction(event -> {
            if (includeTemp.isSelected()) {
                tempHasUpperThreshold.setDisable(false);
                tempHasLowerThreshold.setDisable(false);
                tempHasAlert.setDisable(false);
            } else {
                tempHasUpperThreshold.setSelected(false);
                tempHasLowerThreshold.setSelected(false);
                tempHasUpperThreshold.setDisable(true);
                tempHasLowerThreshold.setDisable(true);
                tempUpperValue.clear();
                tempLowerValue.clear();
                tempHasAlert.setDisable(true);
                tempUpperValue.setDisable(true);
                tempLowerValue.setDisable(true);
                tempHasAlert.setSelected(false);

                tempUpperValue.setStyle(null);
                tempLowerValue.setStyle(null);
            }
        });

        includeHum.setOnAction(event -> {
            if (includeHum.isSelected()) {
                humHasUpperThreshold.setDisable(false);
                humHasLowerThreshold.setDisable(false);
                humHasAlert.setDisable(false);
            } else {
                humHasUpperThreshold.setSelected(false);
                humHasLowerThreshold.setSelected(false);
                humHasUpperThreshold.setDisable(true);
                humHasLowerThreshold.setDisable(true);
                humUpperValue.clear();
                humLowerValue.clear();
                humHasAlert.setDisable(true);
                humUpperValue.setDisable(true);
                humLowerValue.setDisable(true);
                humHasAlert.setSelected(false);

                humUpperValue.setStyle(null);
                humLowerValue.setStyle(null);
            }
        });

        tempHasUpperThreshold.setOnAction(event -> {
            if (tempHasUpperThreshold.isSelected()) {
                if(tempUpperValue.getText().isBlank()){
                    canCloseWindow = false;
                    tempUpperValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                }
                tempUpperValue.setDisable(false);
                tempHasAlert.setDisable(false);
            } else {
                tempUpperValue.setDisable(true);
                tempUpperValue.setStyle(null);
                tempUpperValue.clear();
                if (!tempHasLowerThreshold.isSelected()) {
                    tempHasAlert.setSelected(false);
                    tempHasAlert.setDisable(true);
                }
                tempUpperValue.clear();
            }
        });
        tempHasLowerThreshold.setOnAction(event -> {
            if (tempHasLowerThreshold.isSelected()) {
                if(tempLowerValue.getText().isBlank()){
                    canCloseWindow = false;
                    tempLowerValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                }
                tempLowerValue.setDisable(false);
                tempHasAlert.setDisable(false);
            } else {
                tempLowerValue.setDisable(true);
                tempLowerValue.clear();
                tempLowerValue.setStyle(null);

                if (!tempHasUpperThreshold.isSelected()) {
                    tempHasAlert.setSelected(false);
                    tempHasAlert.setDisable(true);
                }
            }

        });
        humHasUpperThreshold.setOnAction(event -> {
            if (humHasUpperThreshold.isSelected()) {
                if(humUpperValue.getText().isBlank()){
                    canCloseWindow = false;
                    humUpperValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                }
                humUpperValue.setDisable(false);
                humHasAlert.setDisable(false);
            } else {
                humUpperValue.setDisable(true);
                humUpperValue.setStyle(null);
                humUpperValue.clear();
                if (!humHasLowerThreshold.isSelected()) {
                    humHasAlert.setSelected(false);
                    humHasAlert.setDisable(true);
                }
            }
        });
        humHasLowerThreshold.setOnAction(event -> {
            if (humHasLowerThreshold.isSelected()) {
                if(humLowerValue.getText().isBlank()){
                    canCloseWindow = false;
                    humLowerValue.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                }
                humLowerValue.setDisable(false);
                humHasAlert.setDisable(false);
            } else {
                humLowerValue.setDisable(true);
                humLowerValue.clear();
                humLowerValue.setStyle(null);
                if (!humHasUpperThreshold.isSelected()) {
                    humHasAlert.setSelected(false);
                    humHasAlert.setDisable(true);
                }
            }
        });
    }

    private void setupSelectedStation() {
        saveStation.setText("Apply");
        int i = applicationSettings.SelectedRowNum;
        Station station = state.stations.get(i);

        nameField.setText(station.name);
        includeTemp.setSelected(station.temperature.thisPropertyIncluded);
        tempHasUpperThreshold.setSelected(station.temperature.includeUpper);
        tempHasLowerThreshold.setSelected(station.temperature.includeLower);
        tempHasAlert.setSelected(station.temperature.hasAlert);
        tempUpperValue.setText(String.valueOf(station.temperature.upperThreshold));
        tempLowerValue.setText(String.valueOf(station.temperature.lowerThreshold));

        includeHum.setSelected(station.humidity.thisPropertyIncluded);
        humHasUpperThreshold.setSelected(station.humidity.includeUpper);
        humHasLowerThreshold.setSelected(station.humidity.includeLower);
        humHasAlert.setSelected(station.humidity.hasAlert);
        humUpperValue.setText(String.valueOf(station.humidity.upperThreshold));
        humLowerValue.setText(String.valueOf(station.humidity.lowerThreshold));

    }

    public void addStation(MouseEvent mouseEvent) {
        System.out.println("the canclosewindow: "+canCloseWindow);
        String stationName = nameField.getText();
        if (canCloseWindow) {
            //load the fxml file and associate a controller to it and save it into the observable array list of Stations .
            //just make sure some checkboxes are selected :
            if (!stationName.isBlank()
                    && (includeHum.isSelected() || includeTemp.isSelected())
                    && isThresholdEligible()
            ) {
                Temperature t = new Temperature(includeTemp.isSelected(),
                        tempHasUpperThreshold.isSelected(),
                        tempHasLowerThreshold.isSelected(),
                        handleStringToInteger(tempUpperValue.getText()),
                        handleStringToInteger(tempLowerValue.getText()),
                        tempHasAlert.isSelected());

                Humidity h = new Humidity(includeHum.isSelected(),
                        humHasUpperThreshold.isSelected(),
                        humHasLowerThreshold.isSelected(),
                        handleStringToInteger(humUpperValue.getText()),
                        handleStringToInteger(humLowerValue.getText()),
                        humHasAlert.isSelected());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("StationTile.fxml"));
                try {
                    //TODO check this code :
                    Parent root = loader.load();
                    StationTile stationTileInstance = loader.getController();
                    if (!applicationSettings.isEditMode) {
                        state.stations.add(new Station(stationName,
                                t,
                                h,
                                root,
                                stationTileInstance
                        ));
                    } else {

                        state.stations.add(applicationSettings.SelectedRowNum, new Station(stationName,
                                t,
                                h,
                                root,
                                stationTileInstance
                        ));
                    }
                    // after any manipulation in charts for example adding new chart , the animation for that should be set unanimated,otherwise it throws null pointer exception .
                    stationTileInstance.setAreaChartUnAnimated();
                } catch (IOException e) {
                    System.out.println(" failed to create an instance of StationTile!");
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
                stage.close();
            }
        } else {
            //will make the border red , if it is empty
            if (stationName.isBlank()) {
                nameField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (!includeHum.isSelected()) {
                includeHum.setStyle("-fx-check-box-border: #B22222; -fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (!includeTemp.isSelected()) {
                includeTemp.setStyle("-fx-check-box-border: #B22222; -fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }

        }

        applicationSettings.isEditMode = false;
    }

    //TODO check this method : do it for temp and hum .
    private boolean isThresholdEligible() {
        boolean b = true;
        if (tempHasLowerThreshold.isSelected() && tempHasUpperThreshold.isSelected()) {
            if (Integer.parseInt(tempUpperValue.getText()) <= Integer.parseInt(tempLowerValue.getText())) {
                b = false;
            }
        }
        if (humHasUpperThreshold.isSelected() && humHasLowerThreshold.isSelected()) {
            if (Integer.parseInt(humUpperValue.getText()) <= Integer.parseInt(humLowerValue.getText())) {
                b = false;
            }
        }
        return b;
    }

    // this method gets the same instance used for the ApplicationSettings controller this is run in ApplicationSettings controller and there the instance passed through .
    public void setParentController(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    private int handleStringToInteger(String s) {
        int r;
        if (s.isBlank()) {
            errorStringText.append("you have to fill the blank field .\n ");
            System.out.println("handle string to Integer :field is blank");
            r = -10000000;
        } else {
            r = Integer.parseInt(s);
        }
        return r;
    }
}
