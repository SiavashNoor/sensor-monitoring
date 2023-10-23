package com.jadifans.opert;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.xml.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


//this is a singleton design pattern to save configs just in one instance.
//annotation for converting this class object into the xml file. using JAXB . JAXB requires this annotation to indicate the root Element for marshalling
@XmlRootElement(namespace = "com.jadifans.opert")
public class State implements Serializable{

    @XmlTransient
    private static State state = null;

    private boolean hasConnectionAlert;
    private String choiceBoxOption;
    private String IPAddress;
    private String PortNumber;

    @XmlTransient
    public static Set<StateObserver> stateObserverSet = new HashSet<>();


    @XmlElementWrapper(name = "stationList")
    @XmlElement(name = "station")
    ObservableList<Station> stations = FXCollections.observableArrayList();


   @XmlElementWrapper(name = "tableList")
    @XmlElement(name = "row")
    ObservableList<TableContentRepresent> tableContent = FXCollections.observableArrayList();


    private State() {
    }

    public static synchronized State getInstance(StateObserver os ) {
        if (state == null) {
            state = new State();
        }
        //just before getting the instance, os object assigns for the changes .
        assignForChanges(os);
        return state;

    }


    public static void UseThisInstance(State s) {
        for(int i=0;i<s.stations.size();i++){
            FXMLLoader loader = new FXMLLoader(State.class.getResource("StationTile.fxml"));
            try {
                Parent root = loader.load();
                StationTile stationTileInstance = loader.getController();
                s.stations.get(i).setRoot(root);
                s.stations.get(i).setStationTile(stationTileInstance);
            }catch (IOException e) {
                System.out.println(" failed to create an instance of StationTile!");
                throw new RuntimeException(e);
            }
        }

        setInstance(s);
        informObservers();

        System.out.println("this is from unmarshalling :     ");
        System.out.println(state.getIPAddress());
        System.out.println(state.getPortNumber());
        System.out.println("this is the unmarsharller instance  of state s :  "+s);
        System.out.println("this is the state instance in State after setInstance     "+state);
        System.out.println("-------------------------");
    }

    private static void informObservers() {
        if (!stateObserverSet.isEmpty()){
            for (StateObserver o:stateObserverSet
                 ) {
                o.updateCurrentInstance(state);
            }
        }
        System.out.println("informing observers has just been done :");
    }

    public static void setInstance(State s) {
        state  = s;
    }


    public <T> boolean isNull(T[] t) {
        boolean b = false;
        for (T i : t) {
            if (i == null) {
                b = true;
                break;
            }
        }
        System.out.println(b);
        return b;
    }

    @XmlElement
    public String getPortNumber() {
        return PortNumber;
    }


    public void setPortNumber(String portNumber) {
        PortNumber = portNumber;
    }



    public void setConnectionAlert(boolean hasConnectionAlert) {
        this.hasConnectionAlert = hasConnectionAlert;
    }
    @XmlElement
    public boolean isConnectionAlert() {
        return hasConnectionAlert;
    }

    @XmlElement
    public String getChoiceBoxOption() {
        return choiceBoxOption;
    }


    public void setChoiceBoxOption(String choiceBoxOption) {
        this.choiceBoxOption = choiceBoxOption;
    }
    @XmlElement
    public String getIPAddress() {
        return IPAddress;
    }


    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }



    //try this later or remove it .
    public static void assignForChanges(StateObserver o){
        stateObserverSet.add(o);
    }




}



