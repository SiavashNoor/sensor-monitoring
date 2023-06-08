package com.jadifans.opert;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainScene implements Initializable {


    public CategoryAxis xAxis1;
    Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private LinkedList<Station> stations;
    private final String[] xValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15"};
    private final Integer[] yValues = {8, 0, 9, 3, 12, 15, 14, 18, 9, 10};
    @FXML
    public FontIcon Settings;
    @FXML
    public FontIcon addStation;
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


    XYChart.Series<String,Integer> tempSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String,Integer> tempSeries1_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries1_2 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries2_1 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries2_2 = new XYChart.Series<>();



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

    public MainScene() {

    }

    public void openStationWindow(MouseEvent mouseEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("stationAdder.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Add New Station");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }

    public void openSettingsWindow(MouseEvent mouseEvent) {
        Parent settingsRoot = null;
        try {
            settingsRoot = FXMLLoader.load(HelloApplication.class.getResource("applicationSettings.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Settings");
        Scene scene = new Scene(settingsRoot);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
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
       // updateCharts();
        Settings.setOnMouseClicked(this::openSettingsWindow);
        addStation.setOnMouseClicked(this::openStationWindow);
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
        areaChart1_1.getData().add(series);
        //adding empty series to change color of the series .jfx has default colors for series.by adding empty series just
        //skipping those colors . to use it  just uncomment the below line :
        //areaChart1_1.getData().add(new XYChart.Series<>());
        areaChart1_1.getData().add(series2);
    }

    public void makeDataSeries(LinkedList<DataSample> trimmedDataSamples) {
        tempSeries1_1.setName("temp");
        humidSeries1_1.setName("humid");
        tempSeries1_1.getData().clear();
        humidSeries1_1.getData().clear();
        areaChart1_1.getData().clear();
        areaChart1_1.getData().clear();
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
        System.out.println("this is the series"+tempSeries1_1.getData());

            areaChart1_1.getData().add(tempSeries1_1);
            areaChart1_1.getData().add(humidSeries1_1);

        areaChart1_1.setAnimated(false);

    }
}

