package com.jadifans.opert;

import java.util.LinkedList;

public class Station {

     int number;
     int blockNumber;
     boolean includeTemp ;
     boolean includeHumidity;
     String name;


    private  LinkedList<Integer> temperatureList = new LinkedList<>() ;
    private  LinkedList<Double> humidityList = new LinkedList<>();

    Station(String name,int number,int blockNumber,boolean includeTemp,boolean includeHumidity){
        this.name = name;
        this.number = number;
        this.blockNumber = blockNumber;
        this.includeTemp = includeTemp;
        this.includeHumidity = includeHumidity;

    }


    public void updateList(int temp, double humidity){
        if (temperatureList.size() >= 100){
            temperatureList.removeFirst();
            temperatureList.addLast(temp);
        }else {
            temperatureList.addLast(temp);

        }
        if(humidityList.size()>= 100){
            humidityList.removeFirst();
            humidityList.addLast(humidity);

        }else{
            humidityList.addLast(humidity);
        }

    }



    public LinkedList<Integer> getTemperatureList(){

        return temperatureList;
    }
    public LinkedList<Double> getHumidityList(){
        return humidityList;
    }

}
