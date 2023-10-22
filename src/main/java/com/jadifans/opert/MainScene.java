package com.jadifans.opert;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * About Charts . be careful about  chart behaviour when showing data
 * if data points have same X value they would be shown in the same column. whether  the X values are  Stirngs or Numbers.
 * and also good to remember that to ignore error in charts set the animation false
 **/
public class MainScene extends StateObserver implements Initializable  {

    boolean isApplicationTerminated = false;
    SensorServer sensorServer = new SensorServer();
    Stage stage;
    private boolean taskIsRunning = false;
    State state = State.getInstance(this);
    boolean serverIsConnected;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String[] xValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    int trimmedListSize = 16;
    LinkedList<DataSample> trimmedDataSamples = new LinkedList<>();
    int timerTaskDelay = 8000;
    int timerTaskPeriod = 60000;

    @FXML
    public TilePane tilePane;
    @FXML
    public FontIcon infoIcon;
    @FXML
    public Text ConnectionStatus;
    @FXML
    public FontIcon Settings;
    @FXML
    public Hyperlink githubLink;
    @FXML
    public Circle closeButton;
    @FXML
    public Circle minimizeButton;
    @FXML
    public AnchorPane windowBar;


    public MainScene() {
    }

    @Override
    public void  updateCurrentInstance(State s){
        state =s;
    }

    public void closeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        isApplicationTerminated = true;
        stage.close();
        Platform.exit();
    }


    public void minimizeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void openGithubLink(MouseEvent mouseEvent) {
        //this class give access to desktop utilities
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(java.net.URI.create("https://github.com/SiavashNoor"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void openSettingsWindow(MouseEvent mouseEvent) {
        Parent sr;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("applicationSettings.fxml"));
            sr = loader.load();
            ApplicationSettings applicationSettings = loader.getController();
            applicationSettings.setParentController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Settings");
        Scene scene = new Scene(sr);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.getIcons().add(new Image(Objects.requireNonNull(MainScene.class.getResourceAsStream("img/settings.png"))));
        newStage.show();
    }

    public void windowBarPressed(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getX();
        yOffset = mouseEvent.getY();
    }

    public void windowBarDragged(MouseEvent mouseEvent) {
        stage = (Stage) ((Pane) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - xOffset);
        stage.setY(mouseEvent.getScreenY() - yOffset);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Settings.setOnMouseClicked(this::openSettingsWindow);
        githubLink.setOnMouseClicked(this::openGithubLink);
        addTilesToScene();

        //
        State.assignForChanges(this);
    }


    public void openInfoWindow(MouseEvent mouseEvent) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("infoWindow.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("Info");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainScene.class.getResourceAsStream("img/IdeaIcon.png"))));
        stage.show();
        mouseEvent.consume();
    }

    public void runBackEndTasks() {

        if (!taskIsRunning) {
            taskIsRunning = true;
            Timer timer = new Timer();
            setChartsUnAnimated();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    serverIsConnected = sensorServer.connectToServer();
                    Platform.runLater(() -> {
                        updateConnectionStatus(serverIsConnected);
                        updateCharts();
                        updateLabels();
                        checkForAlarms();
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, timerTaskDelay, timerTaskPeriod);
        }
    }


    private void checkForAlarms() {
        if (isThereAnyDataAboveThreshold() || (!serverIsConnected && state.isConnectionAlert())) {
                AlertPlayer player = new AlertPlayer("/com/jadifans/opert/soundEffects/alarm.mp3");
                player.playAlert();
        }
    }


    public boolean isThereAnyDataAboveThreshold() {
        boolean exceededCondition1 = false;
        boolean exceededCondition2 = false;

        if (!DataSample.AllDataSamples.isEmpty() && !state.stations.isEmpty()) {
            for (int i = 0; i < IterationSize(); i++) {
                if(state.stations.get(i).getTemperature().isHasAlert()){
                    if ( !state.stations.get(i).getTemperature().checkThresholdIsOK(DataSample.AllDataSamples.getLast().temperature[i])){
                        exceededCondition1 = true;
                    }
                }
                if(state.stations.get(i).getHumidity().isHasAlert()){
                    if (!state.stations.get(i).getHumidity().checkThresholdIsOK(DataSample.AllDataSamples.getLast().humidity[i])) {
                        exceededCondition2 = true;
                    }
                }
                if(exceededCondition1||exceededCondition2){
                    state.stations.get(i).getRoot().setStyle("-fx-background-color: #cc8685;");
                }
            }
        }
        return exceededCondition1 || exceededCondition2;
    }


    private void updateConnectionStatus(boolean serverIsConnected) {
        ///////////////////set connection status here using boolean value isConnected
        if (serverIsConnected) {
            ConnectionStatus.setText("Connected");
            ConnectionStatus.setFill(Color.GREEN);
        } else {
            ConnectionStatus.setText("Disconnected");
            ConnectionStatus.setFill(Color.RED);
        }
    }


    private void updateLabels() {
        // labels are updated based on last dataSample.
        if (!DataSample.AllDataSamples.isEmpty()) {
            for (int i = 0; i < IterationSize(); i++) {
                state.stations.get(i).getStationTile().temperatureLabel.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[i]));
                state.stations.get(i).getStationTile().humidityLabel.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[i]));
            }
        }
    }


    //tiles are added to tilePane to crowd it.
    public void addTilesToScene() {
        tilePane.getChildren().clear();
        for (int i = 0; i < state.stations.size(); i++) {
            tilePane.getChildren().add(state.stations.get(i).getRoot());
        }
    }

    public void setStationNames() {
        for (int i = 0; i < state.stations.size(); i++) {
            state.stations.get(i).getStationTile().chartName.setText(state.stations.get(i).getName());
        }
    }

    public void updateCharts() {
        makeTrimmedDataSamples(state.getChoiceBoxOption());
        makeSeries();
        injectSeriesToCharts();
    }


    private void injectSeriesToCharts() {
        for (int i = 0; i < state.stations.size(); i++) {
            state.stations.get(i).getStationTile().areaChart.getData().clear();

            if (state.stations.get(i).getTemperature().isThisPropertyIncluded()) {
                state.stations.get(i).getStationTile().areaChart.getData().add(state.stations.get(i).getStationTile().tempSeries);
            } else {
                state.stations.get(i).getStationTile().areaChart.getData().add(new XYChart.Series<>());
            }
            state.stations.get(i).getStationTile().areaChart.getData().add(new XYChart.Series<>());
            state.stations.get(i).getStationTile().areaChart.getData().add(new XYChart.Series<>());

            if (state.stations.get(i).getHumidity().isThisPropertyIncluded()) {
                state.stations.get(i).getStationTile().areaChart.getData().add(state.stations.get(i).getStationTile().humidSeries);
            }
        }
    }


    private void makeSeries() {

        for (int i = 0; i < state.stations.size(); i++) {
            state.stations.get(i).getStationTile().tempSeries.getData().clear();
            state.stations.get(i).getStationTile().humidSeries.getData().clear();
        }
        for (int i = 0; i < state.stations.size(); i++) {
            if (i < IterationSize()) {
                for (int j = 0; j < trimmedDataSamples.size(); j++) {
                    state.stations.get(i).getStationTile().tempSeries.getData().add(new XYChart.Data<>(xValues[j], trimmedDataSamples.get(j).temperature[i]));
                    state.stations.get(i).getStationTile().humidSeries.getData().add(new XYChart.Data<>(xValues[j], trimmedDataSamples.get(j).humidity[i]));
                }
            } else {
                state.stations.get(i).getStationTile().tempSeries.getData().add(new XYChart.Data<>());
                state.stations.get(i).getStationTile().humidSeries.getData().add(new XYChart.Data<>());
            }
        }
    }


    public void makeTrimmedDataSamples(String choiceBoxOption) {
        //sp -> step or step factor
        int sp;

        switch (choiceBoxOption.toLowerCase()) {
            case "instantly" -> {
                System.out.println("you have chosen instantly bro");
                sp = 1;
            }
            case "hourly" -> {
                System.out.println("you have chosen hourly bro");
                sp = 4;
            }
            case "daily" -> {
                System.out.println("you have chosen daily bro");
                sp = 96;
            }
            case "weekly" -> {
                System.out.println("you have chosen weekly bro");
                sp = 672;
            }
            case "monthly" -> {
                System.out.println("you have chosen monthly bro");
                sp = 2960;
            }
            case "yearly" -> {
                System.out.println("you have chosen yearly bro");
                sp = 35040;
            }
            default -> {
                System.out.println("by default instant period is chosen for you:)");
                sp = 1;
            }
        }
        trimDataSamples(sp);
    }


    private void trimDataSamples(int stepFactor) {
        trimmedDataSamples.clear();
        int size = DataSample.AllDataSamples.size();
        int lastIndex = size - 1;
        int remainder = lastIndex % stepFactor;
        for (int i = 0; i < trimmedListSize; i++) {
            if (lastIndex - (i * stepFactor + remainder) >= 0) {
                trimmedDataSamples.addFirst(DataSample.AllDataSamples.get(lastIndex - (i * stepFactor + remainder)));
                System.out.println("selected indexes:" + (lastIndex - (i * stepFactor + remainder)));
            } else break;
        }
    }


    private int IterationSize() {

        //this is number is hard-coded .it shows  the size of Data.
        return Math.min(4, state.stations.size());
    }


    public void setChartsUnAnimated() {
        for (int i = 0; i < state.stations.size(); i++) {
            state.stations.get(i).getStationTile().areaChart.setAnimated(false);
        }
    }
}

