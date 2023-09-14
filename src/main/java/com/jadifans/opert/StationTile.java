package com.jadifans.opert;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class StationTile implements Initializable , Serializable {
    @FXML
    public Text chartName;
    @FXML
    public AreaChart<String,Integer> areaChart;
    @FXML
    public Label temperatureLabel;
    @FXML
    public Label humidityLabel;

    XYChart.Series<String,Integer>tempSeries = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries = new XYChart.Series<>(FXCollections.observableArrayList());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public StationTile(){
    }

}
