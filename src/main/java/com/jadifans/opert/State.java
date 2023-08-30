package com.jadifans.opert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

//this is a singleton design pattern to save configs just in one instance.
public class State {
   private static State state = null;

   public String choiceBoxOption ;
   public String IPAddress ;
   public String PortNumber;
   ObservableList<Station> observableStations = FXCollections.observableArrayList();

    Station[] stations = new Station[4];
    //four alarms for stations and the last one is for connection.
    CheckBox[] alarms = new CheckBox[5];
   int tempThreshold= 40;

  private State(){
  }

  public static synchronized State getInstance(){
    if(state==null){
      state= new State();
    }
    return state;
  }

  public  <T> boolean isNull(T[] t ){
      boolean b = false;
      for ( T i:t) {
          if (i == null) {
              b = true;
              break;
          }
      }
      System.out.println(b);
      return b ;
  }


  public boolean isThereAnyDataAboveThreshold() {
      boolean isAbove = false;
      if (DataSample.AllDataSamples.size() >0) {
          for (int i = 0; i < DataSample.AllDataSamples.getFirst().temperature.length; i++) {
              if (DataSample.AllDataSamples.getLast().temperature[i] >= tempThreshold && stations[i].includeTemp) {
                  isAbove = true;
              }
          }
      }
      return isAbove;
  }
}



