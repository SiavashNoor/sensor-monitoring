package com.jadifans.opert;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApplicationSettings implements Initializable {

    private boolean portNumberFieldIsOK = false;
    private boolean IPAddressIsOK = false;
    int selectedRow;
    public Button editTableView;
    boolean isEditMode = false;
    State state = State.getInstance();
    Stage stage;
    Desktop desktop = Desktop.getDesktop();
    MainScene mainScene;
    private final String[] period = {"Instantly", "Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
    private Property<ObservableList<Station>> stationListProperty = new SimpleObjectProperty<>(state.stations);
    private Property<ObservableList<TableContentRepresent>> tableContent = new SimpleObjectProperty<>(state.tableContent);
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
    private TableView<TableContentRepresent> table;
    @FXML
    private TableColumn<TableContentRepresent, String> nameColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> tempUpperValueColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> tempLowerValueColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> tempHasAlertColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> humUpperValueColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> humLowerValueColumn;
    @FXML
    public TableColumn<TableContentRepresent, String> humHasAlertColumn;

    @FXML
    public Button addStation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpChoiceBox();
        setUpIPAddressField();
        setUpPortNumberField();
        settingsImportButton.setOnMouseClicked(this::importSettings);
        setUpTableView();
        setConnectionAlarmStatus();

        //when we open again the ApplicationSettings stage , the listeners won't work unless the fields are  changed
        portNumberValidationListener();
        IPAddressValidationListener();
        //to check portNumber and IPAddress at first place ,


    }

    private void IPAddressValidationListener() {

        ipAddressField.textProperty().addListener(((observable, oldValue, newValue) -> {
            IPAddressREGEXChecker(newValue);
        }));
    }

    private boolean IPAddressREGEXChecker(String ip) {

        // Regex for digit from 0 to 255.
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (ip == null) {
            IPAddressIsOK = false;
        } else {
            // Pattern class contains matcher() method
            // to find matching between given IP address
            // and regular expression.
            Matcher m = p.matcher(ip);
            // Return if the IP address
            // matched the ReGex
            if (m.matches()) {
                ipAddressField.setStyle(null);
                IPAddressIsOK = true;

            } else {
                IPAddressIsOK = false;
                ipAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
        }

        return IPAddressIsOK;
    }


    private void portNumberValidationListener() {
        portNumberField.textProperty().addListener(((observable, oldValue, newValue) -> {
            validatePortNumber(newValue);
        }));
    }

    private void validatePortNumber(String s) {

        try {
            int i = Integer.parseInt(s);
            if (i > 0 && i <= 65535) {
                portNumberField.setStyle(null);
                portNumberFieldIsOK = true;
            } else {
                portNumberFieldIsOK = false;
                portNumberField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

            }
        } catch (NumberFormatException e) {
            portNumberField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            portNumberFieldIsOK = false;
        }
    }


    private void setConnectionAlarmStatus() {
        connectionAlarm.setSelected(state.hasConnectionAlert);
    }

    private void setUpTableView() {
        //the station class need to have getter and setter classes because the PropertyValueFactory won't work without that. .

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        tempUpperValueColumn.setCellValueFactory(new PropertyValueFactory<>("tempUpperValue"));
        tempLowerValueColumn.setCellValueFactory(new PropertyValueFactory<>("tempLowerValue"));
        tempHasAlertColumn.setCellValueFactory(new PropertyValueFactory<>("includeTempAlert"));
        humUpperValueColumn.setCellValueFactory(new PropertyValueFactory<>("humUpperValue"));
        humLowerValueColumn.setCellValueFactory(new PropertyValueFactory<>("humLowerValue"));
        humHasAlertColumn.setCellValueFactory(new PropertyValueFactory<>("includeHumAlert"));
        table.setEditable(false);
        table.itemsProperty().bind(tableContent);

    }


    private void setUpPortNumberField() {
        if (state.PortNumber != null) {

            portNumberField.setText(state.PortNumber);
            validatePortNumber(state.PortNumber);
        }
    }

    private void setUpIPAddressField() {
        if (state.IPAddress != null) {
            IPAddressIsOK = IPAddressREGEXChecker(state.IPAddress);
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
        if (IPAddressIsOK && portNumberFieldIsOK && state.stations != null) {
            stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
            state.choiceBoxOption = getPeriodValueFromChoiceBox();
            state.IPAddress = ipAddressField.getText();
            state.PortNumber = portNumberField.getText();
            state.hasConnectionAlert = connectionAlarm.isSelected();
            mainScene.addTilesToScene();
            mainScene.setStationNames();

            // a mechanism to prevent  empty text fields :


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

    private void saveStateToFile(State state, Stage stage) {

        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Opert files", "*.opt");
        fileChooser.getExtensionFilters().add(fileExtension);
        File file = fileChooser.showSaveDialog(stage);
        System.out.println(file.getAbsolutePath() + "this is the file");
        //TODO check this code:
        if (file.exists()) {
            System.out.println("state is being written to the selected file: ...");
            //the object that we want ot write should be Serializable. and the ExportHandler class handles that.
            ExportHandler exportHandler = new ExportHandler(state);

            try (final FileOutputStream fout = new FileOutputStream(file.getAbsolutePath());
                 final ObjectOutputStream out = new ObjectOutputStream(fout)) {
                out.writeObject(state);
                out.flush();
                out.close();
                fout.close();
                System.out.println("successful file writing....");
            } catch (IOException e) {
                //TODO handle this exception
                System.out.println("couldn't write in to the file ...");
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

    //TODO check this code here:
    public void openStationAdder(MouseEvent mouseEvent) {
        Parent sr;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stationAdder.fxml"));
            sr = loader.load();
            StationAdder stationAdder = loader.getController();
            stationAdder.setParentController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Station SetUp:");
        Scene scene = new Scene(sr);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.getIcons().add(new Image(Objects.requireNonNull(ApplicationSettings.class.getResourceAsStream("img/plusIcon.png"))));
        newStage.show();
        mouseEvent.consume();

    }

    private void setStationAdderData(StationAdder s, int i) {

        Station station = state.stations.get(i);
        s.nameField.setText(station.name);


        s.includeTemp.setSelected(station.temperature.thisPropertyIncluded);
        if (station.temperature.thisPropertyIncluded) {
            s.tempHasLowerThreshold.setDisable(false);
            s.tempHasUpperThreshold.setDisable(false);
            s.tempHasAlert.setDisable(false);
        }
        s.tempHasUpperThreshold.setSelected(station.temperature.includeUpper);
        if (station.temperature.includeUpper) {
            s.tempUpperValue.setDisable(false);
            s.tempUpperValue.setText(String.valueOf(station.temperature.upperThreshold));
        }
        s.tempHasLowerThreshold.setSelected(station.temperature.includeLower);
        if (station.temperature.includeLower) {
            s.tempLowerValue.setDisable(false);
            s.tempLowerValue.setText(String.valueOf(station.temperature.lowerThreshold));
        }
        s.tempHasAlert.setSelected(station.temperature.hasAlert);

        s.includeHum.setSelected(station.humidity.thisPropertyIncluded);
        if (station.humidity.thisPropertyIncluded) {
            s.humHasLowerThreshold.setDisable(false);
            s.humHasUpperThreshold.setDisable(false);
            s.humHasAlert.setDisable(false);
        }
        s.humHasUpperThreshold.setSelected(station.humidity.includeUpper);
        if (station.humidity.includeUpper) {
            s.humUpperValue.setDisable(false);
            s.humUpperValue.setText(String.valueOf(station.humidity.upperThreshold));
        }


        s.humHasLowerThreshold.setSelected(station.humidity.includeLower);
        if (station.humidity.includeLower) {
            s.humLowerValue.setDisable(false);
            s.humLowerValue.setText(String.valueOf(station.humidity.lowerThreshold));
        }

        s.humHasAlert.setSelected(station.humidity.hasAlert);

    }


    public void deleteAllRowsInTableview(MouseEvent mouseEvent) {
        state.stations.clear();
        state.tableContent.clear();
        mouseEvent.consume();
    }

    public void deleteSpecificRow(MouseEvent mouseEvent) {
        int i = table.getSelectionModel().getFocusedIndex();
        if (!state.stations.isEmpty()) {
            state.stations.remove(i);
        }

        if (!state.tableContent.isEmpty()) {
            state.tableContent.remove(i);
        }
        System.out.println("row" + i);
        mouseEvent.consume();
    }

    //TODO check this code :
    public void editTableViewContent(MouseEvent mouseEvent) {

        selectedRow = table.getSelectionModel().getFocusedIndex();
        if (selectedRow != -1) {
            editTableView.setStyle(null);
            isEditMode = true;
            Parent sr;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("stationAdder.fxml"));
                sr = loader.load();
                StationAdder stationAdder = loader.getController();
                stationAdder.setParentController(this);
                stationAdder.saveStation.setText("Apply");

                setStationAdderData(stationAdder, selectedRow);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage newStage = new Stage();
            newStage.setTitle("Edit Station");
            Scene scene = new Scene(sr);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.getIcons().add(new Image(Objects.requireNonNull(ApplicationSettings.class.getResourceAsStream("img/plusIcon.png"))));
            newStage.show();
            mouseEvent.consume();

        } else {

            editTableView.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

        }
    }

    public int getIndexOfSelectedRow() {
        return this.selectedRow;
    }

    public void exportConfigurationsToFile() {
        saveStateToFile(state, stage);
    }
}
