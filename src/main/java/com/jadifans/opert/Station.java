package com.jadifans.opert;

import javafx.scene.Parent;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Station {

    private Temperature temperature;
    private Humidity humidity;
    private String name;
    private StationTile stationTile;
    private Parent root;

    Station(String name, Temperature temperature, Humidity humidity, Parent root, StationTile stationTile) {
        this.setName(name);
        this.setTemperature(temperature);
        this.setHumidity(humidity);
        this.setStationTile(stationTile);
        this.setRoot(root);
    }

    Station() {
    }


    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @XmlElement
    public Temperature getTemperature() {
        return temperature;
    }

    @XmlElement
    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }
    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlTransient
    public StationTile getStationTile() {
        return stationTile;
    }

    public void setStationTile(StationTile stationTile) {
        this.stationTile = stationTile;
    }
    @XmlTransient
    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}
