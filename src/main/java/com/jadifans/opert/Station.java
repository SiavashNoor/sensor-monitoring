package com.jadifans.opert;

public class Station {
    boolean includeTemp;
    boolean includeHumidity;
    boolean includeAlert;
    String name;


    //this is temporary
    Station(String name, boolean includeTemp, boolean includeHumidity) {
        this.name = name;
        this.includeTemp = includeTemp;
        this.includeHumidity = includeHumidity;
    }
    Station(String name, boolean includeTemp, boolean includeHumidity,boolean includeAlert) {
        this.name = name;
        this.includeTemp = includeTemp;
        this.includeHumidity = includeHumidity;
        this.includeAlert = includeAlert;
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
