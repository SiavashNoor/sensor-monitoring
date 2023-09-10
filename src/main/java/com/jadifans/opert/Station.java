package com.jadifans.opert;

import javafx.scene.Parent;



public class Station {

    boolean includeTemp;
    boolean includeHumidity;
    boolean includeAlert;
    String name;
    StationTile stationTile;
    Parent root;

    Station(String name, boolean includeTemp, boolean includeHumidity, boolean includeAlert, Parent root, StationTile stationTile) {
        this.name =name;
        this.includeTemp =includeTemp;
        this.includeHumidity = includeHumidity;
        this.includeAlert = includeAlert;
        this.stationTile = stationTile;
        this.root = root;
    }

    public StationTile getStationTile() {
        return stationTile;
    }

    public void setStationTile(StationTile stationTile) {
        this.stationTile = stationTile;
    }

    public boolean isIncludeTemp() {
        return includeTemp;
    }

    public void setIncludeTemp(boolean includeTemp) {
        this.includeTemp = includeTemp;
    }

    public boolean isIncludeHumidity() {
        return includeHumidity;
    }

    public void setIncludeHumidity(boolean includeHumidity) {
        this.includeHumidity = includeHumidity;
    }

    public boolean isIncludeAlert() {
        return includeAlert;
    }

    public void setIncludeAlert(boolean includeAlert) {
        this.includeAlert = includeAlert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
