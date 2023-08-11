package com.jadifans.opert;



import javafx.application.Platform;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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


    Stage stage;
    private boolean taskIsRunning = false;
    State state = State.getInstance();
    boolean serverIsConnected;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String[] xValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    int trimmedListSize = 15;
    LinkedList<DataSample> trimmedDataSamples = new LinkedList<>();


    @FXML
    public Label temp1;
    @FXML
    public Label hum1;
    @FXML
    public FontIcon infoIcon;
    @FXML
    public Label temp4;
    @FXML
    public Label hum4;
    @FXML
    public Text chart4Name;
    @FXML
    public Label hum3;
    @FXML
    public Label temp3;
    @FXML
    public Text chart3Name;
    @FXML
    public Label hum2;
    @FXML
    public Label temp2;
    @FXML
    public Text chart2Name;
    @FXML
    public Text chart1Name;
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
    public Pane windowBar;
    @FXML
    private AreaChart<String, Integer> areaChart1_1;
    @FXML
    private AreaChart<String, Integer> areaChart1_2;
    @FXML
    private AreaChart<String, Integer> areaChart2_1;
    @FXML
    private AreaChart<String, Integer> areaChart2_2;



    XYChart.Series<String, Integer> tempSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String, Integer> humidSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String, Integer> tempSeries1_2 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String, Integer> humidSeries1_2 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String, Integer> tempSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String, Integer> humidSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String, Integer> tempSeries2_2 = new XYChart.Series<>();
    XYChart.Series<String, Integer> humidSeries2_2 = new XYChart.Series<>();


/*
     this is another method to update a chart.not really practically true.
    javafx.event.EventHandler<ActionEvent> chartUpdater = new javafx.event.EventHandler<>() {

         @Override
         public void handle(ActionEvent event) {
             areaChart1_2.getData().remove(tempSeries1_2);
             Random random = new Random();
             //without setting animation false I was getting the  " Duplicate series added " error and after just one update
             //the exception error was being thrown.
             areaChart1_2.setAnimated(false);
             if (tempSeries1_2.getData().size() < 15) {
                 tempSeries1_2.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
             } else {
                 tempSeries1_2.getData().remove(0);
                 tempSeries1_2.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
             }
             areaChart1_2.getData().add(tempSeries1_2);
         }
     };
    */

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
        newStage.getIcons().add(new Image("com/jadifans/opert/img/settings.png"));
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
        stage.getIcons().add(new Image("com/jadifans/opert/img/IdeaIcon.png"));
        stage.show();
    }

    public void runBackEndTasks() {
        int timerTaskDelay = 30000;
        int timerTaskPeriod = 60000;
        if (!taskIsRunning) {
            taskIsRunning = true;

            Timer timer = new Timer();
            areaChart1_1.setAnimated(false);
            areaChart1_2.setAnimated(false);
            areaChart2_1.setAnimated(false);
            areaChart2_2.setAnimated(false);

            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {

                    serverIsConnected = sensorServer.connectToServer();


                    Platform.runLater(() -> {
                            updateConnectionStatus(serverIsConnected);
                            updateCharts();
                            updateLabels();

                     /*       areaChart1_1.getData().remove(tempSeries1_1);
                            if (tempSeries1_1.getData().size() < 15) {
                                tempSeries1_1.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
                            } else {
                                tempSeries1_1.getData().remove(0);
                                tempSeries1_1.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
                            }
                            areaChart1_1.getData().add(tempSeries1_1);*/
                    });
                }
            };

            timer.scheduleAtFixedRate(timerTask, timerTaskDelay, timerTaskPeriod);

            //////////////////////////////////////
            //without setting animation false I was getting the  " Duplicate series added " error and after just one update
            //the exception error was being thrown .
           /* Timeline updateChart = new Timeline(new KeyFrame(Duration.seconds(60), chartUpdater));
            updateChart.setCycleCount(Animation.INDEFINITE);
            updateChart.play();*/
            ///////////////////////////////////////
        }
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
       temp1.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[0]));
        temp2.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[1]));
        temp3.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[2]));
        temp4.setText(String.valueOf(DataSample.AllDataSamples.getLast().temperature[3]));
        hum1.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[0]));
        hum2.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[1]));
        hum3.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[2]));
        hum4.setText(String.valueOf(DataSample.AllDataSamples.getLast().humidity[3]));
    }

    public void setStationNames(){
        chart1Name.setText(state.stations[0].name);
        chart2Name.setText(state.stations[1].name);
        chart3Name.setText(state.stations[2].name);
        chart4Name.setText(state.stations[3].name);
    }

    public void updateCharts() {
        makeTrimmedDataSamples(state.choiceBoxOption);
        makeSeries();
        injectSeriesToCharts();
    }

    private void injectSeriesToCharts() {
        areaChart1_1.getData().clear();
        areaChart1_2.getData().clear();
        areaChart2_1.getData().clear();
        areaChart2_2.getData().clear();

       // dding empty series to change color of the series .jfx has default colors for series.by adding empty series just
        //skipping those colors . to use it  just uncomment the below line :
        areaChart1_1.getData().add(tempSeries1_1);
        areaChart1_1.getData().add(new XYChart.Series<>());
        areaChart1_1.getData().add(new XYChart.Series<>());
        areaChart1_1.getData().add(humidSeries1_1);
        areaChart1_2.getData().add(tempSeries1_2);
        areaChart1_2.getData().add(new XYChart.Series<>());
        areaChart1_2.getData().add(new XYChart.Series<>());
        areaChart1_2.getData().add(humidSeries1_2);
        areaChart2_1.getData().add(tempSeries2_1);
        areaChart2_1.getData().add(new XYChart.Series<>());
        areaChart2_1.getData().add(new XYChart.Series<>());
        areaChart2_1.getData().add(humidSeries2_1);
        areaChart2_2.getData().add(tempSeries2_2);
        areaChart2_2.getData().add(new XYChart.Series<>());
        areaChart2_2.getData().add(new XYChart.Series<>());
        areaChart2_2.getData().add(humidSeries2_2);
    }

    private void makeSeries() {
        /// I really don't like to do this shit code I mean hard coding, I know its ridiculous .In the next major Update going to make a big change in this part of app
        // and make it more flexible .
        tempSeries1_1.getData().clear();
        tempSeries1_2.getData().clear();
        tempSeries2_1.getData().clear();
        tempSeries2_2.getData().clear();
        humidSeries1_1.getData().clear();
        humidSeries1_2.getData().clear();
        humidSeries2_1.getData().clear();
        humidSeries2_2.getData().clear();

        for (int i = 0; i < trimmedDataSamples.size(); i++) {

            tempSeries1_1.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).temperature[0]));
            humidSeries1_1.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).humidity[0]));
            tempSeries1_2.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).temperature[1]));
            humidSeries1_2.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).humidity[1]));
            tempSeries2_1.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).temperature[2]));
            humidSeries2_1.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).humidity[2]));
            tempSeries2_2.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).temperature[3]));
            humidSeries2_2.getData().add(new XYChart.Data<>(xValues[i], trimmedDataSamples.get(i).humidity[3]));
        }
    }


    public void makeTrimmedDataSamples(String choiceBoxOption) {
        //sp -> step or step factor
        int sp = 0;

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
        int remainder = size % stepFactor;
        int lastIndex = size - 1;

        for (int i = 0; i < trimmedListSize; i++) {
            if (lastIndex - (i * stepFactor + remainder) >= 0) {
                trimmedDataSamples.addFirst(DataSample.AllDataSamples.get(lastIndex - (i * stepFactor + remainder)));
                System.out.println("selected indexes:"+(lastIndex - (i * stepFactor + remainder)));
            } else break;
        }
    }
}

