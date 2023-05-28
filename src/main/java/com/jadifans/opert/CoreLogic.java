package com.jadifans.opert;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CoreLogic {
    FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("applicationSettings.fxml"));

    FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
    MainScene mainScene = mainSceneLoader.getController();
    public void runApplicationBackendLogic() {

        getDataFromServer();
        String choiceBoxOption= getChoiceBoxOption();
        makeDataSeries(choiceBoxOption);



       /* String choiceBoxOption = getChoiceBoxOption();
            if (choiceBoxOption ==null){
            settings.setUpChoiceBox();
            }
            else {
                switch (choiceBoxOption.toLowerCase()) {

                    case "hourly":
                        System.out.println("you have chosen hourly");
                        break;
                    case "daily":
                        System.out.println("you have chosen daily");
                        break;
                    case "monthly":
                        System.out.println("you have chosen monthly");
                        break;
                    case "yearly":
                        System.out.println("you have chosen yearly");
                        break;

                    default:
                        System.out.println("by default hourly period is chosen for you:)");

                }
            }
*/

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
                    int size =  DataSample.AllDataSamples.size();
                    System.out.println(size);
                    break;
                case "hourly":
                    System.out.println("you have chosen hourly bro");

                    break;
                case "daily":
                    System.out.println("you have chosen daily bro");
                    break;
                case "monthly":
                    System.out.println("you have chosen monthly bro");
                    break;
                case "yearly":
                    System.out.println("you have chosen yearly bro");
                    break;
                default:
                    System.out.println("by default instant period is chosen for you:)");
            }
        }
    }
}
