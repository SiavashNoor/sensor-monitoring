package com.jadifans.opert;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class CoreLogic  {

    LinkedList<DataSample> trimmedDataSamples = new LinkedList<>();
    FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("applicationSettings.fxml"));

    FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
    MainScene mainScene = mainSceneLoader.getController();
    public void runApplicationBackendLogic() {
        getDataFromServer();
        //you shouldn't call getChoiceBoxOption twice because it calls  root loader and cause  exception if its called twice .be careful.
        String choiceBoxOption= getChoiceBoxOption();
        makeDataSeries(choiceBoxOption);
        System.out.println("trimmed data samples:"+trimmedDataSamples.size());
        forceChartsToUpdate(trimmedDataSamples);
    }

    private void forceChartsToUpdate(LinkedList<DataSample> trimmedDataSamples)  {
        try {
            mainSceneLoader.load();
        } catch (IOException e) {
            System.out.println("unable to load mainScene controller here .");
            throw new RuntimeException(e);
        }
        System.out.println("charts are getting updated:");
      // mainScene.updateAllCharts(trimmedDataSamples);


    }

    private String getChoiceBoxOption() {
        try {
            //before writing this line of code I was getting null pointer exception for "applicationSettings" instance.
             settingsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ApplicationSettings applicationSettings =settingsLoader.getController();
        return applicationSettings.getPeriodValueFromChoiceBox();
    }


    public void getDataFromServer() {

        System.out.println("hello");
        TimerTask task = new SensorServer();
        Timer timer = new Timer();
        //this timer is going to set a one-minute period data picking with a 30 seconds delay at start.
        timer.scheduleAtFixedRate(task, 30000, 60000);
        System.out.println("hi");

    }


    public void makeDataSeries(String choiceBoxOption){

        if (choiceBoxOption ==null){
           // applicationSettings.setUpChoiceBox();

        }
        else {
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
        int size =  DataSample.AllDataSamples.size();
        int i = size-1;
        while (i>=0){
           trimmedDataSamples.add( DataSample.AllDataSamples.get(i));
            i-=stepFactor;
        }
    }
}
