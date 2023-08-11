package com.jadifans.opert;

import javafx.scene.control.CheckBox;

//this is a singleton design pattern to save configs just in one instance.
public class State {
   private static State state = null;

   public String choiceBoxOption ;
   public String IPAddress ;
   public String PortNumber;

  private State(){
  }

  public static synchronized State getInstance(){
    if(state==null){
      state= new State();
    }
    return state;
  }

   Station[] stations = new Station[4];
  //four alarms for stations and the last one is for connection.
   CheckBox[] alarms = new CheckBox[5];
   int tempThreshold= 40;

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



