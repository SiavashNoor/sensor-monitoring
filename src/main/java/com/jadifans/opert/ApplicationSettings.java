package com.jadifans.opert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ApplicationSettings implements Initializable {

    int stationNumberByPosition;
    Stage stage;
    Desktop desktop = Desktop.getDesktop();
    StationAdder stationAdder;
    private final String[] period = {"Instantly", "Hourly", "Daily", "Weekly", "Monthly", "Yearly"};

    @FXML
    public TextField tempThreshold;
    @FXML
    public CheckBox sensor1Alarm;
    @FXML
    public CheckBox sensor2Alarm;
    @FXML
    public CheckBox sensor3Alarm;
    @FXML
    public CheckBox sensor4Alarm;
    @FXML
    public CheckBox connectionAlarm;
    @FXML
    public Text stationFour;
    @FXML
    public Text stationThree;
    @FXML
    public Text stationTwo;
    @FXML
    public Text stationOne;
    @FXML
    public javafx.scene.shape.Rectangle rectangle;
    @FXML
    public ChoiceBox<String> periodChoiceBox;
    @FXML
    public Button settingsCancelButton;
    @FXML
    public Button settingsSaveButton;
    @FXML
    public Button settingsImportButton;
    @FXML
    public TextField ipAddressField;
    @FXML
    public TextField portNumberField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpChoiceBox();
        setUpIPAddressField();
        setUpPortNumberField();
        setStation();
        setUpAlarms();
        setUpTempThreshold();
       rectangle.setOnMouseReleased(this::setStation);
        //settingsSaveButton.setOnMouseClicked(this::saveSettings);
        periodChoiceBox.setOnAction(this::getPeriodValueFromChoiceBox);
        ipAddressField.setOnMouseClicked(this::getIPAddressValue);
        portNumberField.setOnMouseClicked(this::getPortNumberValue);
        settingsImportButton.setOnMouseClicked(this::importSettings);
    }

    private void setUpTempThreshold() {
        tempThreshold.setText(String.valueOf(State.tempThreshold));
    }

    private void setUpAlarms(){
        CheckBox[] Alarms ={sensor1Alarm, sensor2Alarm, sensor3Alarm, sensor4Alarm, connectionAlarm};
        for(int i=0;i<Alarms.length;i++){
            if(State.alarms[i]!=null){
                Alarms[i].setSelected(State.alarms[i].isSelected());
            }
        }
    }

    private void setUpPortNumberField() {
        if (State.PortNumber != null) {
            portNumberField.setText(State.PortNumber);
        }
    }

    private void setUpIPAddressField() {
        if (State.IPAddress != null) {
            ipAddressField.setText(State.IPAddress);
        }
    }
    private void setStation(){
        Text[] Stations ={stationOne,stationTwo,stationThree,stationFour};

        for(int i=0;i<Stations.length;i++){
            if(State.stations[i]!=null){
                Stations[i].setText(State.stations[i].name);
            }
        }
    }

    public void setUpChoiceBox() {
        periodChoiceBox.getItems().addAll(period);
        if (State.choiceBoxOption == null) {
            periodChoiceBox.setValue(period[0]);
        } else {
            periodChoiceBox.setValue(State.choiceBoxOption);
        }
    }


    public String getPeriodValueFromChoiceBox(ActionEvent event) {
        return periodChoiceBox.getValue();
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
        saveAlarmToState();

        State.choiceBoxOption = getPeriodValueFromChoiceBox();
        State.IPAddress = ipAddressField.getText();
        State.PortNumber = portNumberField.getText();
        State.tempThreshold =Integer.parseInt(tempThreshold.getText());

        CoreLogic.setChoiceBoxOption(getPeriodValueFromChoiceBox());
        // a mechanism to prevent  empty text fields :
        System.out.println("save settings :-" + portNumberField.getText() + "-");
        if (!ipAddressField.getText().equals("") && !portNumberField.getText().equals("")) {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            stage.close();
        } else {
            if (ipAddressField.getText().equals("")) {
                ipAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (portNumberField.getText().equals("")) {
                portNumberField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
        }
    }

    private void saveAlarmToState() {
        State.alarms = new CheckBox[]{sensor1Alarm, sensor2Alarm, sensor3Alarm, sensor4Alarm, connectionAlarm};
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

    public void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ignored) {
        }
    }


    public void setStation(MouseEvent mouseEvent) {
        //getting the click position of mouse . need to know which part of rectangle is selected .
        stationNumberByPosition = returnStationNumberBasedOnClickPosition(mouseEvent);


       Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stationAdder.fxml"));
            root =loader.load();
             StationAdder stationAdder = loader.getController();

            //this is how I pass the same instance to the other scene and let it send back data to this  scene which is considered as a parent
            stationAdder.setParentController(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage newStage = new Stage();
        newStage.setTitle("Add New Station");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.getIcons().add(new Image("com/jadifans/opert/img/plusIcon.png"));
        newStage.show();
    }

    private int returnStationNumberBasedOnClickPosition(MouseEvent mouseEvent) {

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        int rx = (int) Math.floor(x / (width / 2));
        int ry = (int) Math.floor(y / (height / 2));
        //ry*2(power of 1)+rx*2(power of 0) returns 0,1,2,3 for the quarters .
        System.out.println(ry * 2 + rx);
        return ry * 2 + rx;
    }

    public  void setStationName(String stationName,boolean includeTemp,boolean includeHum) {
        System.out.println(stationName);
        System.out.println("in set stationName"+stationNumberByPosition);
        System.out.println("as instance"+this);
        switch (stationNumberByPosition) {
            case 0:
                stationOne.setText(stationName);
                State.stations[0] =new Station(stationName,includeTemp,includeHum);
                break;
            case 1:
                stationTwo.setText(stationName);
                State.stations[1] =new Station(stationName,includeTemp,includeHum);
                break;
            case 2:
                stationThree.setText(stationName);
                State.stations[2] =new Station(stationName,includeTemp,includeHum);
                break;
            case 3:
                stationFour.setText(stationName);
                State.stations[3] =new Station(stationName,includeTemp,includeTemp);
                break;
        }

    }

}
