package com.jadifans.opert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableContentRepresent {
    private String stationName ;
    private String tempUpperValue;
    private String tempLowerValue;
    private String humLowerValue;
    private String humUpperValue;
    private String includeTempAlert;
    private String includeHumAlert;


    public TableContentRepresent(String stationName, String tempUpperValue, String tempLowerValue,String includeTempAlert, String humLowerValue, String humUpperValue,  String includeHumAlert) {
        this.stationName = stationName;
        this.tempUpperValue = tempUpperValue;
        this.tempLowerValue = tempLowerValue;
        this.humLowerValue = humLowerValue;
        this.humUpperValue = humUpperValue;
        this.includeTempAlert = includeTempAlert;
        this.includeHumAlert = includeHumAlert;
    }

    public TableContentRepresent(){}

    @XmlElement
    public String getStationName() {
        return stationName;
    }
    @XmlElement
    public String getTempUpperValue() {
        return tempUpperValue;
    }
    @XmlElement
    public String getTempLowerValue() {
        return tempLowerValue;
    }
    @XmlElement
    public String getHumLowerValue() {
        return humLowerValue;
    }
    @XmlElement
    public String getHumUpperValue() {
        return humUpperValue;
    }
    @XmlElement
    public String getIncludeTempAlert() {
        return includeTempAlert;
    }
    @XmlElement
    public String getIncludeHumAlert() {
        return includeHumAlert;
    }



    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setTempUpperValue(String tempUpperValue) {
        this.tempUpperValue = tempUpperValue;
    }

    public void setTempLowerValue(String tempLowerValue) {
        this.tempLowerValue = tempLowerValue;
    }

    public void setHumLowerValue(String humLowerValue) {
        this.humLowerValue = humLowerValue;
    }

    public void setHumUpperValue(String humUpperValue) {
        this.humUpperValue = humUpperValue;
    }

    public void setIncludeTempAlert(String includeTempAlert) {
        this.includeTempAlert = includeTempAlert;
    }

    public void setIncludeHumAlert(String includeHumAlert) {
        this.includeHumAlert = includeHumAlert;
    }
}
