package com.jadifans.opert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class SensorServer  {

    boolean successfulConnection = true;
    Document doc = null;
    State state = State.getInstance();
    SensorServer(){
    }

//ServerURL for test : http://49.12.208.81:1374
    public boolean connectToServer() {
        String serverURL = getServerAddress();
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
            }else {
                successfulConnection = false;
                throw new RuntimeException();
            }

            long unixTimeStampAtThisMoment = Instant.now().getEpochSecond();
            //every year has 525960 minutes .the maximum size of arrayList that we need .
            //storing last data in last place of linked list
            successfulConnection = true;
            if (DataSample.AllDataSamples.size()<525960) {
                DataSample.AllDataSamples.add(new DataSample(temperatures, humidities, unixTimeStampAtThisMoment));
                System.out.println(Arrays.toString(DataSample.AllDataSamples.getLast().temperature));
            }else{
                DataSample.AllDataSamples.removeFirst();
                DataSample.AllDataSamples.add(new DataSample(temperatures,humidities,unixTimeStampAtThisMoment));
            }
        } catch (IOException e) {
            System.out.println("Not connected to the server! Please check the connection and refresh ");
           // throw new RuntimeException(e);
            successfulConnection = false;
        }
       return successfulConnection;
    }

    private String getServerAddress() {
        AtomicReference<StringBuilder> str = new AtomicReference<>(new StringBuilder());
        str.get().append("http://");
        str.get().append(state.IPAddress);
        str.get().append(":");
        str.get().append(state.PortNumber);
        return str.toString().trim();
    }
}




