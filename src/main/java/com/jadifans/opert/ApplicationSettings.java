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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApplicationSettings extends StateObserver implements Initializable {

    private boolean portNumberFieldIsOK = false;
    private boolean IPAddressIsOK = false;
    int selectedRow;
    public Button editTableView;
    boolean isEditMode = false;
    State state = State.getInstance(this);
    Stage stage;

    MainScene mainScene;
    private final String[] period = {"Instantly", "Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
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
        settingsImportButton.setOnMouseClicked(this::importSettings);
        setUpComponents();
        setUpTableView();
    }

    private void setUpComponents() {
        setConnectionAlarmStatus();
        setUpPortNumberField();
        setUpChoiceBox();
        setUpIPAddressField();
        //when we open again the ApplicationSettings stage , the listeners won't work unless the fields are  changed
        portNumberValidationListener();
        IPAddressValidationListener();
        //to check portNumber and IPAddress at first place ,

    }

    @Override
    public void  updateCurrentInstance(State s){
        state =s;
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
        connectionAlarm.setSelected(state.isConnectionAlert());
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
        if (state.getPortNumber() != null) {

            portNumberField.setText(state.getPortNumber());
            validatePortNumber(state.getPortNumber());
        }
    }

    private void setUpIPAddressField() {
        if (state.getIPAddress() != null) {
            IPAddressIsOK = IPAddressREGEXChecker(state.getIPAddress());
            ipAddressField.setText(state.getIPAddress());
        }
    }


    public void setUpChoiceBox() {
        periodChoiceBox.getItems().addAll(period);
        if (state.getChoiceBoxOption() == null) {
            periodChoiceBox.setValue(period[0]);
        } else {
            periodChoiceBox.setValue(state.getChoiceBoxOption());
        }
    }

    public String getPeriodValueFromChoiceBox() {
        return periodChoiceBox.getValue();
    }


    public void saveSettings(MouseEvent mouseEvent) {
        if (IPAddressIsOK && portNumberFieldIsOK && state.stations != null) {
            stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
            state.setChoiceBoxOption(getPeriodValueFromChoiceBox());
            state.setIPAddress(ipAddressField.getText());
            state.setPortNumber(portNumberField.getText());
            state.setConnectionAlert(connectionAlarm.isSelected());
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

        if (file.exists()) {
            System.out.println("state is being written to the selected file: ...");
            //the object that we want ot write should be Serializable. and the ExportHandler class handles that.
            ExportHandler exportHandler = new ExportHandler(state,file);
            exportHandler.writeObjectToFile();


        }

    }


    public void cancelSettings(MouseEvent mouseEvent) {
        stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void importSettings(MouseEvent mouseEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        ImportHandler importHandler = new ImportHandler(file);
        State.UseThisInstance((State)importHandler.ConvertXmlToObject(),this);
        updateThisStage();
        System.out.println("this is the state instance in application settings    "+state);
        System.out.println(state.getChoiceBoxOption());
        System.out.println(state.getIPAddress());


    }

    private void updateThisStage() {

        //to fill again components like textFields or checkboxes;
        setUpComponents();


        //yessss you made it : need this to lines to update again the table content :
        //first re-instantiate the tableContent then bind it to the table
        tableContent = new SimpleObjectProperty<>(state.tableContent);
        table.itemsProperty().bind(tableContent);
    }


    public void setParentController(MainScene mainScene) {
        this.mainScene = mainScene;
    }


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
        s.nameField.setText(station.getName());


        s.includeTemp.setSelected(station.getTemperature().isThisPropertyIncluded());
        if (station.getTemperature().isThisPropertyIncluded()) {
            s.tempHasLowerThreshold.setDisable(false);
            s.tempHasUpperThreshold.setDisable(false);
            s.tempHasAlert.setDisable(false);
        }
        s.tempHasUpperThreshold.setSelected(station.getTemperature().isIncludeUpper());
        if (station.getTemperature().isIncludeUpper()) {
            s.tempUpperValue.setDisable(false);
            s.tempUpperValue.setText(String.valueOf(station.getTemperature().getUpperThreshold()));
        }
        s.tempHasLowerThreshold.setSelected(station.getTemperature().isIncludeLower());
        if (station.getTemperature().isIncludeLower()) {
            s.tempLowerValue.setDisable(false);
            s.tempLowerValue.setText(String.valueOf(station.getTemperature().getLowerThreshold()));
        }
        s.tempHasAlert.setSelected(station.getTemperature().isHasAlert());

        s.includeHum.setSelected(station.getHumidity().isThisPropertyIncluded());
        if (station.getHumidity().isThisPropertyIncluded()) {
            s.humHasLowerThreshold.setDisable(false);
            s.humHasUpperThreshold.setDisable(false);
            s.humHasAlert.setDisable(false);
        }
        s.humHasUpperThreshold.setSelected(station.getHumidity().isIncludeUpper());
        if (station.getHumidity().isIncludeUpper()) {
            s.humUpperValue.setDisable(false);
            s.humUpperValue.setText(String.valueOf(station.getHumidity().getUpperThreshold()));
        }


        s.humHasLowerThreshold.setSelected(station.getHumidity().isIncludeLower());
        if (station.getHumidity().isIncludeLower()) {
            s.humLowerValue.setDisable(false);
            s.humLowerValue.setText(String.valueOf(station.getHumidity().getLowerThreshold()));
        }
        s.humHasAlert.setSelected(station.getHumidity().isHasAlert());

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
