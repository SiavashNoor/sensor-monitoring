package com.jadifans.opert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.Instant;
import java.util.TimerTask;


public class SensorServer extends TimerTask {
    Document doc = null;
    int counter=1;
    public void connectToServer() {


        String serverURL = "http://49.12.208.81:1374";

        try {
            doc = Jsoup.connect(serverURL).get();
            System.out.println(doc);
        } catch (IOException e) {
            System.out.println("Not connected to the server! Please check the connection and refresh ");
           // throw new RuntimeException(e);
        }

        System.out.println(doc);
        if(doc!=null) {
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
            System.out.println(unixTimeStampAtThisMoment);

            DataSample.AllDataSamples.add(new DataSample(temperatures,humidities,unixTimeStampAtThisMoment));

        } else System.out.println("failed to get data from server ");
    }

    @Override
    public void run() {
        System.out.println("getting the : "+ counter++ );

        connectToServer();
    }

}




