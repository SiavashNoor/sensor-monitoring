package com.jadifans.opert;

import java.util.LinkedList;
/**
 * this class instance holds  data which we every time attempt to get it  form the server also let the other parts of application access to
 * the  data easily .
 */
public class DataSample {

    public static LinkedList<DataSample> AllDataSamples =new LinkedList<>();
    int[] temperature;
     int[] humidity;
    long timeStamp;
    DataSample(int[] temperature,int[] humidity,long timeStamp){
        this.temperature= temperature;
        this.humidity= humidity;
        this.timeStamp=timeStamp;
    }








}
