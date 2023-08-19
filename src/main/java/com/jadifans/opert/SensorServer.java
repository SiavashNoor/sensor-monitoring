package com.jadifans.opert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.TimerTask;

public class SensorServer extends TimerTask {
    CoreLogic coreLogic;

    Document doc = null;
    int counter=1;

    SensorServer(CoreLogic coreLogic){
        this.coreLogic= coreLogic;
    }
    public void connectToServer() {



        String serverURL = "http://49.12.208.81:1374";

        try {
            doc = Jsoup.connect(serverURL).get();

            Element table = doc.select("table").get(0);
            Element tBody = table.select("tbody").get(0);
            Elements row = tBody.select("tr");
            Elements temps = row.get(0).select("td");
            Elements humids = row.get(1).select("td");
            int[] temperatures =new int[temps.size()];
            int[] humidities = new int[humids.size()];
            if (temps.size()==humids.size()) {
                for (int i = 0; i < humidities.length; i++) {
                    temperatures[i] = Integer.parseInt(temps.get(i).text());
                    humidities[i] = Integer.parseInt(humids.get(i).text());
                }
            }else throw new RuntimeException();

            long unixTimeStampAtThisMoment = Instant.now().getEpochSecond();

            //every year has 525960 minutes .the maximum size of arrayList that we need .
            //storing last data in last place of linked list
            if (DataSample.AllDataSamples.size()<525960) {
                DataSample.AllDataSamples.addFirst(new DataSample(temperatures, humidities, unixTimeStampAtThisMoment));
                System.out.println(Arrays.toString(DataSample.AllDataSamples.getLast().temperature));
            }else{
                DataSample.AllDataSamples.removeLast();
                DataSample.AllDataSamples.addFirst(new DataSample(temperatures,humidities,unixTimeStampAtThisMoment));
            }

            System.out.println("size : "+DataSample.AllDataSamples.size());

            coreLogic.doPeriodicTasks();
            // In here I should inform the part of code which is interested to know if ArrayList is updated with new value.
        } catch (IOException e) {
            System.out.println("Not connected to the server! Please check the connection and refresh ");
           // throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        System.out.println("getting the : "+ counter++ );
        connectToServer();

    }
}




