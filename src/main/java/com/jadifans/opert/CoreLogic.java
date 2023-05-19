package com.jadifans.opert;

import java.util.Timer;
import java.util.TimerTask;

public class CoreLogic   {


    public void runApplicationBackendLogic() {
        System.out.println("hello");
        TimerTask task = new SensorServer();
        Timer timer = new Timer();
        //this timer is going to set a one-minute period data picking with a 30 seconds delay at start.
        timer.scheduleAtFixedRate(task,30000,60000);
        //getDataFromServer(sensorServer);

    }









    public void getDataFromServer(SensorServer sensorServer){
        sensorServer.connectToServer();
    }
}
