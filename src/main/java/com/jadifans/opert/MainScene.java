package com.jadifans.opert;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
About Charts . be careful about  chart behaviour when showing data
 if data points have same X value they would be shown in the same column. whether  the X values are  Stirngs or Numbers.
 and also good to remember that to ignore error in charts set the animation false

 **/
public class MainScene implements Initializable {


    XYChart.Series<String,Integer> series =new XYChart.Series<>();
    CoreLogic coreLogic = new CoreLogic();
    public CategoryAxis xAxis1;
    Stage stage;
    private boolean taskIsRunning = false;
    Random random = new Random();
    boolean serverIsConnected ;
    private double xOffset = 0;
    private double yOffset = 0;
    private LinkedList<Station> stations;
    private final String[] xValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15"};
    private final Integer[] yValues = {8, 0, 9, 3, 12, 15, 14, 18, 9, 10};
    private int timerTaskDelay;
    private int timerTaskPeriod;

    @FXML
    public FontIcon infoIcon;
    @FXML
    public Text hum4;
    @FXML
    public Text temp4;
    @FXML
    public Text chart4Name;
    @FXML
    public Text hum3;
    @FXML
    public Text temp3;
    @FXML
    public Text chart3Name;
    @FXML
    public Text hum2;
    @FXML
    public Text temp2;
    @FXML
    public Text chart2Name;
    @FXML
    public Text hum1;
    @FXML
    public Text temp1;
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


    static XYChart.Series<String,Integer> tempSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    static XYChart.Series<String,Integer> humidSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String,Integer> tempSeries1_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries1_2 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries2_1 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries2_2 = new XYChart.Series<>();


    javafx.event.EventHandler<ActionEvent> chartUpdater = new javafx.event.EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            areaChart1_2.getData().remove(tempSeries1_2);
            Random random = new Random();
            //without setting animation false I was getting the  " Duplicate series added " error and after just one update
            //the exception error was being thrown.
            areaChart1_2.setAnimated(false);
            if (tempSeries1_2.getData().size()<15) {
                tempSeries1_2.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
            }else {
                tempSeries1_2.getData().remove(0);
                tempSeries1_2.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
            }
            areaChart1_2.getData().add(tempSeries1_2);
        }
    };



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












    private void updateCharts() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        areaChart1_1.getData().remove(series);
        areaChart1_1.getData().remove(series2);
        series.setName("temp");
        for (int i = 0; i < yValues.length; i++) {
            series.getData().add(new XYChart.Data<>(xValues[i], yValues[i]));
        }
        series2.setName("hum");
        series2.getData().add(new XYChart.Data<>("10", 20));
        series2.getData().add(new XYChart.Data<>("13", 12));
        series2.getData().add(new XYChart.Data<>("14", 8));
        //areaChart1_1.getData().add(series);
        //adding empty series to change color of the series .jfx has default colors for series.by adding empty series just
        //skipping those colors . to use it  just uncomment the below line :
        //areaChart1_1.getData().add(new XYChart.Series<>());
       // areaChart1_1.getData().add(series2);
    }

    public void makeDataSeries(LinkedList<DataSample> trimmedDataSamples,XYChart.Series<String, Integer> series) {
        tempSeries1_1.setName("temp");
        humidSeries1_1.setName("humid");
        tempSeries1_1.getData().clear();
        humidSeries1_1.getData().clear();
        areaChart1_1.setAnimated(false);
        //areaChart1_1.getData().clear();
        areaChart1_1.getData().remove(series);
       //
        for (DataSample trimmedDataSample : trimmedDataSamples) {
            System.out.println("this is running in main Scene class " + Arrays.toString(trimmedDataSample.humidity));
        }

        for (int i=0;i<trimmedDataSamples.size();i++){

            tempSeries1_1.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).temperature[0]));
            humidSeries1_1.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).humidity[0]));

            tempSeries1_2.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).temperature[1]));
            humidSeries1_2.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).humidity[1]));

            tempSeries2_1.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).temperature[2]));
            humidSeries2_1.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).humidity[2]));

            tempSeries2_2.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).temperature[3]));
            humidSeries2_2.getData().add(new XYChart.Data<>(xValues[i],trimmedDataSamples.get(i).humidity[3]));
        }
        System.out.println("this is the series"+series.getData());
        this.series= series;
           areaChart1_1.getData().add(series);
    }

    public void openInfoWindow(MouseEvent mouseEvent) {
        Parent root ;
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

        if (!taskIsRunning) {
            taskIsRunning = true;

            Timer timer = new Timer();
            areaChart1_1.setAnimated(false);
            areaChart1_2.setAnimated(false);
            areaChart2_1.setAnimated(false);
            areaChart2_2.setAnimated(false);
            coreLogic.runApplicationBackendLogic();

            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {

                    serverIsConnected = coreLogic.doPeriodicTasks();
                    //without setting animation false I was getting the  " Duplicate series added " error and after just one update
                    //the exception error was being thrown .

                    Platform.runLater(() -> {

                        ///////////////////set connection status here using boolean value isConnected
                        if (serverIsConnected) {
                            ConnectionStatus.setText("Connected");
                            ConnectionStatus.setFill(Color.GREEN);
                        } else {
                            ConnectionStatus.setText("Disconnected");
                            ConnectionStatus.setFill(Color.RED);
                        }
                        ///////////////////

                        areaChart1_1.getData().remove(tempSeries1_1);
                        if (tempSeries1_1.getData().size() < 15) {
                            tempSeries1_1.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
                        } else {
                            tempSeries1_1.getData().remove(0);
                            tempSeries1_1.getData().add(new XYChart.Data<>(String.valueOf(random.nextInt(1000)), random.nextInt(100)));
                        }
                        areaChart1_1.getData().add(tempSeries1_1);
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, 30000, 60000);
//////////////////////////
            Timeline updateChart = new Timeline(new KeyFrame(Duration.seconds(60), chartUpdater));
            updateChart.setCycleCount(Animation.INDEFINITE);
            updateChart.play();
        }
    }
}

