package com.jadifans.opert;


import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;

import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.*;

public class CoreLogic {

    XYChart.Series<String,Integer> tempSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries1_1 = new XYChart.Series<>(FXCollections.observableArrayList());

    XYChart.Series<String,Integer> tempSeries1_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries1_2 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_1 = new XYChart.Series<>(FXCollections.observableArrayList());
    XYChart.Series<String,Integer> humidSeries2_1 = new XYChart.Series<>();

    XYChart.Series<String,Integer> tempSeries2_2 = new XYChart.Series<>();
    XYChart.Series<String,Integer> humidSeries2_2 = new XYChart.Series<>();
    SensorServer sensorServer =new SensorServer();
    ApplicationSettings applicationSettings;
    LinkedList<DataSample> trimmedDataSamples = new LinkedList<>();
    FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("applicationSettings.fxml"));
    FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
    MainScene mainScene;
    private static String choiceBoxOption;



    public CoreLogic(){

    }










    public  void runApplicationBackendLogic() {
        try {
            mainSceneLoader.load();
        } catch (IOException e) {
            System.out.println("unable to load mainScene controller here .");
            throw new RuntimeException(e);
        }
        mainScene=mainSceneLoader.getController();
        ///////////////this part of code comes from getChoiceBoxOption method :
        //this part handles loading settings in this class:
        try {
            //before writing this line of code I was getting null pointer exception for "applicationSettings" instance.
            settingsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        applicationSettings = settingsLoader.getController();
        ////////////////////
        choiceBoxOption = getChoiceBoxOption();
    }

    private void forceChartsToUpdate(LinkedList<DataSample> trimmedDataSamples,MainScene mainScene,XYChart.Series<String, Integer> series) {
       mainScene.makeDataSeries(trimmedDataSamples,series);
        System.out.println("charts are getting updated:");
        // mainScene.updateAllCharts(trimmedDataSamples);
    }

    private String getChoiceBoxOption() {
        return applicationSettings.getPeriodValueFromChoiceBox();
    }

    public void makeTrimmedDataSamples(String choiceBoxOption) {


        if (getChoiceBoxOption() == null) {
             applicationSettings.setUpChoiceBox();

        } else {
            switch (choiceBoxOption.toLowerCase()) {
                case "instantly":
                    System.out.println("you have chosen instantly bro");
                    trimDataSamples(1);
                    break;
                case "hourly":
                    System.out.println("you have chosen hourly bro");
                    trimDataSamples(4);
                    break;
                case "daily":
                    System.out.println("you have chosen daily bro");
                    trimDataSamples(96);
                    break;
                case "weekly":
                    System.out.println("you have chosen weekly bro");
                    trimDataSamples(672);
                    break;
                case "monthly":
                    System.out.println("you have chosen monthly bro");
                    trimDataSamples(2920);
                    break;
                case "yearly":
                    System.out.println("you have chosen yearly bro");
                    trimDataSamples(35040);
                    break;
                default:
                    System.out.println("by default instant period is chosen for you:)");
                    trimDataSamples(1);
            }
        }
    }

    private void trimDataSamples(int stepFactor) {
       trimmedDataSamples.clear();
        int trimmedListSize = 15;

        for (int j = 0; j < trimmedListSize; j++) {
            if (j * stepFactor < DataSample.AllDataSamples.size()) {
                trimmedDataSamples.add(DataSample.AllDataSamples.get( (j * stepFactor)));
            } else break;
        }
        Collections.reverse(trimmedDataSamples);
    }


    public boolean doPeriodicTasks() {
        //you shouldn't call getChoiceBoxOption twice because it calls  root loader and cause  exception if its called twice .be careful.
        boolean connected =sensorServer.connectToServer();
        if(connected) {
            makeTrimmedDataSamples(choiceBoxOption);

        }
        return connected;
    }




 public static void setChoiceBoxOption(String ch){
        choiceBoxOption = ch;
 }


}
