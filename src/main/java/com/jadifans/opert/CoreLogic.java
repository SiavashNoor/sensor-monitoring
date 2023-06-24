package com.jadifans.opert;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class CoreLogic extends TimerTask{
    //private AreaChart<String, Integer> chart;
     XYChart.Series<String, Integer> series = new XYChart.Series<>();


    public CoreLogic(AreaChart<String,Integer> chart) {
        System.out.println("core logic instance.");
      chart.getData().add(series);
    }

    public CoreLogic(){

    }
    LinkedList<DataSample> trimmedDataSamples = new LinkedList<>();
    ApplicationSettings applicationSettings;
    SensorServer sensorServer =new SensorServer();
    FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("applicationSettings.fxml"));

    TimerTask task = this;
    Timer timer = new Timer();

    FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
    MainScene mainScene ;
    String choiceBoxOption;
    boolean coreIsRunning = false;

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
        getDataFromServer();

    }

    private void forceChartsToUpdate(LinkedList<DataSample> trimmedDataSamples,MainScene mainScene,XYChart.Series<String, Integer> series) {
       mainScene.makeDataSeries(trimmedDataSamples,series);
        System.out.println("charts are getting updated:");
        // mainScene.updateAllCharts(trimmedDataSamples);
    }

    private String getChoiceBoxOption() {
        return applicationSettings.getPeriodValueFromChoiceBox();
    }


    public void getDataFromServer() {
        System.out.println("hello");
        //this timer is going to set a one-minute period data picking with a 30 seconds delay at start.
        timer.scheduleAtFixedRate(task, 30000, 60000);
        System.out.println("hi");

    }


    public void makeTrimmedDataSamples(String choiceBoxOption) {

        if (getChoiceBoxOption() == null) {
            // applicationSettings.setUpChoiceBox();

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


    public void doPeriodicTasks() {
        //you shouldn't call getChoiceBoxOption twice because it calls  root loader and cause  exception if its called twice .be careful.
        this.series.getData().add(new XYChart.Data<>("sa",10));
        makeTrimmedDataSamples(choiceBoxOption);
        forceChartsToUpdate(trimmedDataSamples,mainScene,series);
        System.out.println("trimmed data samples:"+trimmedDataSamples.size());
    }

    @Override
    public void run() {

        sensorServer.connectToServer();
        Platform.runLater(this::doPeriodicTasks);
    }
}
