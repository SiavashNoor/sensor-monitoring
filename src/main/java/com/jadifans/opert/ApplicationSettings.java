package com.jadifans.opert;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.Objects;
import java.util.ResourceBundle;


public class ApplicationSettings implements Initializable {


    State state = State.getInstance();
    int stationNumberByPosition;
    Stage stage;
    Desktop desktop = Desktop.getDesktop();
    MainScene mainScene;
    private final String[] period = {"Instantly", "Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
    private Property<ObservableList<Station>> stationListProperty = new SimpleObjectProperty<>(state.observableStations);
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
    @FXML
    private TableView<Station> table;
    @FXML
    private TableColumn<String, Integer> idColumn;
    @FXML
    private TableColumn<Station, String> nameColumn;
    @FXML
    private TableColumn<Station, Boolean> temperatureColumn;
    @FXML
    private TableColumn<Station, Boolean> humidityColumn;
    @FXML
    private TableColumn<Station, Boolean> alertColumn;
    @FXML
    public TextField addName;
    @FXML
    public Button addStation;
    @FXML
    public CheckBox addTemp;
    @FXML
    public CheckBox addHum;
    @FXML
    public CheckBox addAlert;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpChoiceBox();
        setUpIPAddressField();
        setUpPortNumberField();
        setStation();
        setUpAlarms();
        setUpTempThreshold();
        rectangle.setOnMouseReleased(this::setStation);
        ipAddressField.setOnMouseClicked(this::getIPAddressValue);
        portNumberField.setOnMouseClicked(this::getPortNumberValue);
        settingsImportButton.setOnMouseClicked(this::importSettings);
        setUpTableView();
    }

    private void setUpTableView() {
        //the station class need to have getter and setter classes because the PropertyValueFactory won't work without that. .
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("includeTemp"));
        humidityColumn.setCellValueFactory(new PropertyValueFactory<>("includeHumidity"));
        alertColumn.setCellValueFactory(new PropertyValueFactory<>("includeAlert"));
        table.setEditable(true);
        table.itemsProperty().bind(stationListProperty);

    }

    private void setUpTempThreshold() {
        tempThreshold.setText(String.valueOf(state.tempThreshold));
    }

    private void setUpAlarms() {
        CheckBox[] Alarms = {sensor1Alarm, sensor2Alarm, sensor3Alarm, sensor4Alarm, connectionAlarm};
        for (int i = 0; i < Alarms.length; i++) {
            if (state.alarms[i] != null) {
                Alarms[i].setSelected(state.alarms[i].isSelected());
            }
        }
    }

    private void setUpPortNumberField() {
        if (state.PortNumber != null) {
            portNumberField.setText(state.PortNumber);
        }
    }

    private void setUpIPAddressField() {
        if (state.IPAddress != null) {
            ipAddressField.setText(state.IPAddress);
        }
    }

    private void setStation() {
        Text[] Stations = {stationOne, stationTwo, stationThree, stationFour};

        for (int i = 0; i < Stations.length; i++) {
            if (state.stations[i] != null) {
                Stations[i].setText(state.stations[i].name);
            }
        }
    }

    public void setUpChoiceBox() {
        periodChoiceBox.getItems().addAll(period);
        if (state.choiceBoxOption == null) {
            periodChoiceBox.setValue(period[0]);
        } else {
            periodChoiceBox.setValue(state.choiceBoxOption);
        }
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

        state.choiceBoxOption = getPeriodValueFromChoiceBox();
        state.IPAddress = ipAddressField.getText();
        state.PortNumber = portNumberField.getText();
        state.tempThreshold = Integer.parseInt(tempThreshold.getText());


        // a mechanism to prevent  empty text fields :

        if (!ipAddressField.getText().equals("") && !portNumberField.getText().equals("") && state.stations != null) {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);

            //after the save button is pushed and if everything were ok this line will run the backend part in mainScene.
            mainScene.runBackEndTasks();
            //updating charts instantly if any change is applied to the settings .for example any change in period of charts.
            mainScene.updateCharts();
            mainScene.setStationNames();
            stage.close();
        } else {
            if (ipAddressField.getText().equals("")) {
                ipAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (portNumberField.getText().equals("")) {
                portNumberField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (state.isNull(state.stations)) {
                rectangle.setStyle(" -fx-stroke:  #B22222; -fx-stroke-width: 3;");
            }
        }
    }


    private void saveAlarmToState() {
        state.alarms = new CheckBox[]{sensor1Alarm, sensor2Alarm, sensor3Alarm, sensor4Alarm, connectionAlarm};
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
            root = loader.load();
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
        newStage.getIcons().add(new Image(Objects.requireNonNull(MainScene.class.getResourceAsStream("img/plusIcon.png"))));
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

    public void setStationName(String stationName, boolean includeTemp, boolean includeHum) {
/*        System.out.println(stationName);
        System.out.println("in set stationName"+stationNumberByPosition);
        System.out.println("as instance"+this);*/
        switch (stationNumberByPosition) {
            case 0 -> {
                stationOne.setText(stationName);
                state.stations[0] = new Station(stationName, includeTemp, includeHum);
            }
            case 1 -> {
                stationTwo.setText(stationName);
                state.stations[1] = new Station(stationName, includeTemp, includeHum);
            }
            case 2 -> {
                stationThree.setText(stationName);
                state.stations[2] = new Station(stationName, includeTemp, includeHum);
            }
            case 3 -> {
                stationFour.setText(stationName);
                state.stations[3] = new Station(stationName, includeTemp, includeHum);
            }
        }
    }

    public void setParentController(MainScene mainScene) {
        this.mainScene = mainScene;
    }



    public void addStationToObservableList(MouseEvent mouseEvent) {
        String stationName =addName.getText();
        if(!stationName.isBlank() && (addHum.isSelected() || addTemp.isSelected())){
            state.observableStations.add(new Station(addName.getText(),addTemp.isSelected(),addHum.isSelected(),addAlert.isSelected()));
            //to clear data entries when the data submit was successful.
            addName.clear();
            addTemp.setSelected(false);
            addHum.setSelected(false);
            addAlert.setSelected(false);

            //to remove red border around nodes If it was previously added to them.and reset it to the default one .
            addName.setStyle(null);
            addTemp.setStyle(null);
            addHum.setStyle(null);
        }else{
            //will make the border red , if it is empty
            if(stationName.isBlank()){
                addName.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } if (!addHum.isSelected()) {
                addHum.setStyle("-fx-check-box-border: #B22222; -fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }if(!addTemp.isSelected()){
                addTemp.setStyle("-fx-check-box-border: #B22222; -fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
        }
    }
}
