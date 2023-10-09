package com.jadifans.opert;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

import java.io.Serializable;


public class Station implements Serializable {
    Temperature temperature;
    Humidity humidity;
    String name;
    StationTile stationTile;
    Parent root;

    Station(String name, Temperature temperature, Humidity humidity, Parent root, StationTile stationTile) {
        this.name =name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.stationTile = stationTile;
        this.root = root;

            }

    public StationTile getStationTile() {
        return stationTile;
    }

    public void setStationTile(StationTile stationTile) {
        this.stationTile = stationTile;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
