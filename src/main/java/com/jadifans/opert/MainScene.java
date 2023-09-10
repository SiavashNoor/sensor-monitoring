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
public class MainScene implements Initializable {

    SensorServer sensorServer = new SensorServer();
    AlertPlayer player = new AlertPlayer();
    Stage stage;
    private boolean taskIsRunning = false;
    State state = State.getInstance();
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


    public void closeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.close();
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
    }

    public void runBackEndTasks() {

        if (!taskIsRunning) {
            taskIsRunning = true;
            Timer timer = new Timer();
            for(int i = 0; i<state.stations.size(); i++){
                state.stations.get(i).stationTile.areaChart.setAnimated(false);
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    serverIsConnected = sensorServer.connectToServer();
                    Platform.runLater(() -> {
                        updateConnectionStatus(serverIsConnected);
                        updateCharts();
                        updateLabels();
                        checkThreshold();
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, timerTaskDelay, timerTaskPeriod);
        }
    }

    private void checkThreshold() {
        if (isThereAnyDataAboveThreshold()) {
            player.playAlert();
        }
    }


    public boolean isThereAnyDataAboveThreshold() {
        boolean isAbove = false;
        if (!DataSample.AllDataSamples.isEmpty()) {
            for (int i = 0; i < DataSample.AllDataSamples.getFirst().temperature.length; i++) {
                if (DataSample.AllDataSamples.getLast().temperature[i] >= state.tempThreshold && state.stations.get(i).includeTemp) {
                    isAbove = true;
                }
            }
        }
        return isAbove;
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
            for(int i =0;i<IterationSize();i++){
                state.stations.get(i).stationTile.temperatureLabel.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[i]));
                state.stations.get(i).stationTile.humidityLabel.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[i]));
            }
        }
    }

    //tiles are added to tilePane to crowd it.
    public void addTilesToScene(){
        tilePane.getChildren().clear();
for(int i = 0; i<state.stations.size(); i++){
    tilePane.getChildren().add(state.stations.get(i).root );
}
    }

    public void setStationNames() {
        for(int i = 0; i<state.stations.size(); i++){
            state.stations.get(i).stationTile.chartName.setText(state.stations.get(i).name);
        }
    }

    public void updateCharts() {
        makeTrimmedDataSamples(state.choiceBoxOption);
        makeSeries();
        injectSeriesToCharts();
    }

    private void injectSeriesToCharts() {
        for(int i = 0; i<state.stations.size(); i++){
            state.stations.get(i).stationTile.areaChart.getData().clear();

            if(state.stations.get(i).includeTemp){
                state.stations.get(i).stationTile.areaChart.getData().add(state.stations.get(i).stationTile.tempSeries);
            }else{
                state.stations.get(i).stationTile.areaChart.getData().add(new XYChart.Series<>());
            }
            state.stations.get(i).stationTile.areaChart.getData().add(new XYChart.Series<>());
            state.stations.get(i).stationTile.areaChart.getData().add(new XYChart.Series<>());

            if(state.stations.get(i).includeHumidity){
                state.stations.get(i).stationTile.areaChart.getData().add(state.stations.get(i).stationTile.humidSeries);
            }
        }
    }


    private void makeSeries() {
        for (int i = 0; i < state.stations.size(); i++) {
            state.stations.get(i).stationTile.tempSeries.getData().clear();
            state.stations.get(i).stationTile.humidSeries.getData().clear();
        }
        for (int i = 0; i < IterationSize(); i++) {
            for (int j = 0; j < trimmedDataSamples.size(); j++) {
                state.stations.get(i).stationTile.tempSeries.getData().add(new XYChart.Data<>(xValues[j], trimmedDataSamples.get(j).temperature[i]));
                state.stations.get(i).stationTile.humidSeries.getData().add(new XYChart.Data<>(xValues[j], trimmedDataSamples.get(j).humidity[i]));
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


}

