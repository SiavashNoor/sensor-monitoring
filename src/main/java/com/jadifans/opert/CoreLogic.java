package com.jadifans.opert;


import javafx.collections.FXCollections;


import javafx.scene.chart.XYChart;


public class CoreLogic {

    XYChart.Series<String,Integer> tempSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String,Integer> tempSeries1_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries1_2 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries2_1 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries2_2 = new XYChart.Series<>();

    public CoreLogic(){

    }
}
