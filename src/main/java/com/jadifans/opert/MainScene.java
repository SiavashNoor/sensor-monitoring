package com.jadifans.opert;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
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
import java.util.*;


public class MainScene implements Initializable {


    enum StationList{
        STATION1,
        STATION2,
        STATION3,
        STATION4

    }

    public CategoryAxis xAxis1;

    Stage stage;

    private  double xOffset = 0;
    private  double yOffset = 0;

    private LinkedList<Station> stations ;


    private String[] xvalues = {"0","1","2","3","4","5","6","7","8","9","10"};
    private Integer[] yvalues = {8,0,9,3,12,15,14,18,9,10};

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
    private AreaChart<String,Integer> areaChart1;





    public void closeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.close();

    }


    public void minimizeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    public void openGithubLink(MouseEvent mouseEvent)  {
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

    public void openStationWindow(MouseEvent mouseEvent)  {
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
        stage.setX(mouseEvent.getScreenX()-xOffset);
        stage.setY(mouseEvent.getScreenY()-yOffset);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateCharts();
        Settings.setOnMouseClicked(this::openSettingsWindow);
        addStation.setOnMouseClicked(this::openStationWindow);
        githubLink.setOnMouseClicked(this::openGithubLink);

    }

    private void updateCharts() {



        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("temp");
        for (int i = 0;i<yvalues.length;i++){
            series.getData().add(new XYChart.Data<>(xvalues[i],yvalues[i]));
        }

        XYChart.Series<String,Integer> series2 = new XYChart.Series<>();
        series2.setName("hum");
        series2.getData().add(new XYChart.Data<>("10",20));
        series2.getData().add(new XYChart.Data<>("13",12));
        series2.getData().add(new XYChart.Data<>("14",8));
        areaChart1.getData().add(series);
        areaChart1.getData().add(series2);

    }

}

