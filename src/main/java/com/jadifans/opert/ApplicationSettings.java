package com.jadifans.opert;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;

import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class ApplicationSettings implements Initializable {

    public Button editTableView;
    boolean isEditMode =false;
    State state = State.getInstance();
    Stage stage;
    Desktop desktop = Desktop.getDesktop();
    MainScene mainScene;
    private final String[] period = {"Instantly", "Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
    private Property<ObservableList<Station>> stationListProperty = new SimpleObjectProperty<>(state.stations);
    @FXML
    public TextField tempThreshold;

    @FXML
    public CheckBox connectionAlarm;

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
        setUpTempThreshold();
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


    public void saveSettings(MouseEvent mouseEvent) {
        stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        state.choiceBoxOption = getPeriodValueFromChoiceBox();
        state.IPAddress = ipAddressField.getText();
        state.PortNumber = portNumberField.getText();
        state.tempThreshold = Integer.parseInt(tempThreshold.getText());
        mainScene.addTilesToScene();
        mainScene.setStationNames();
        System.out.println("stationlist size: "+state.stations.size());
        // a mechanism to prevent  empty text fields :

        if (!ipAddressField.getText().isEmpty() && !portNumberField.getText().isEmpty() && state.stations != null) {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            //after the save button is pushed and if everything were ok this line will run the backend part in mainScene.
            mainScene.runBackEndTasks();
            //updating charts instantly if any change is applied to the settings .for example any change in period of charts.
            mainScene.updateCharts();
            stage.close();
        } else {
            if (ipAddressField.getText().isEmpty()) {
                ipAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (portNumberField.getText().isEmpty()) {
                portNumberField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (state.isNull(state.stations.toArray())) {
                System.out.println(" there is no stations to be shown please add new stations :");
            }
        }
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

    public void setParentController(MainScene mainScene) {
        this.mainScene = mainScene;
    }

    public void addStationToObservableList(MouseEvent mouseEvent) {
        //load the fxml file and associate a controller ot it and save it into the observable array list of Stations .
        String stationName =addName.getText();
        //just make sure some checkboxes are selected :
        if(!stationName.isBlank() && (addHum.isSelected() || addTemp.isSelected())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StationTile.fxml"));
            try {
                Parent root  = loader.load();
                StationTile stationTileInstance = loader.getController();
                if(isEditMode){
                    state.stations.set(getIndexOfSelectedRow(),new Station(stationName,addTemp.isSelected(),addHum.isSelected(),addAlert.isSelected(),root,stationTileInstance
                    ));
                }else{
                    state.stations.add (new Station(stationName,addTemp.isSelected(),addHum.isSelected(),addAlert.isSelected(),root,stationTileInstance
                    ));
                }
                isEditMode = false;
                editTableView.setDisable(false);
                addStation.setText("Add");
            } catch (IOException e) {
                System.out.println(" failed to create an instance of StationTile!");
                throw new RuntimeException(e);
            }
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


    public void deleteAllRowsInTableview(MouseEvent mouseEvent) {
        state.stations.clear();
    }

    public void deleteSpecificRow(MouseEvent mouseEvent){
        if(!state.stations.isEmpty()) {
            int i = table.getSelectionModel().getFocusedIndex();
            state.stations.remove(i);
            System.out.println("row"+i);
        }
    }

    public void editTableViewContent(MouseEvent mouseEvent) {
        isEditMode =true;
        int i  =table.getSelectionModel().getFocusedIndex();
        addName.setText(state.stations.get(i).name);
        addTemp.setSelected(state.stations.get(i).includeTemp);
        addHum.setSelected(state.stations.get(i).includeHumidity);
        addAlert.setSelected(state.stations.get(i).includeAlert);

        editTableView.setDisable(true);
        addStation.setText("Apply");
    }

    private int getIndexOfSelectedRow(){
        return table.getSelectionModel().getFocusedIndex();
    }
}
