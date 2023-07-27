package com.jadifans.opert;

import javafx.scene.control.CheckBox;

public class State {

  static String choiceBoxOption ;
  static String IPAddress ;
  static String PortNumber;

  static Station[] stations = new Station[4];


  //four alarms for stations and the last one is for connection.
  static CheckBox[] alarms = new CheckBox[5];
  static int tempThreshold= 40;



}



