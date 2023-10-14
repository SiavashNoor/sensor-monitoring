package com.jadifans.opert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Serializable;



//this is a singleton design pattern to save configs just in one instance.
public class State implements Serializable {


   private static State state = null;
   public boolean hasConnectionAlert;
   public String choiceBoxOption ;
   public String IPAddress ;
   public String PortNumber;
//this is transient because it contains unSerializable objects .
   transient  ObservableList<Station> stations = FXCollections.observableArrayList();
   transient  ObservableList<TableContentRepresent> tableContent = FXCollections.observableArrayList();


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



