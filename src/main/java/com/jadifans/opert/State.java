package com.jadifans.opert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//this is a singleton design pattern to save configs just in one instance.
public class State {
   private static State state = null;

   public String choiceBoxOption ;
   public String IPAddress ;
   public String PortNumber;
   ObservableList<Station> stations = FXCollections.observableArrayList();

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



}



