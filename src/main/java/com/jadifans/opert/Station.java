package com.jadifans.opert;


public class Station {
    boolean includeTemp;
    boolean includeHumidity;
    String name;


    Station(String name, boolean includeTemp, boolean includeHumidity) {
        this.name = name;
        this.includeTemp = includeTemp;
        this.includeHumidity = includeHumidity;
    }


}
