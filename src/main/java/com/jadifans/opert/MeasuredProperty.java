package com.jadifans.opert;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasuredProperty {

   private boolean thisPropertyIncluded;
   private boolean includeUpper;
   private boolean includeLower;
   private int upperThreshold;
   private int lowerThreshold;
   private boolean hasAlert;

    MeasuredProperty(boolean thisPropertyIncluded,
                     boolean includeUpper,
                     boolean includeLower,
                     int upperThreshold,
                     int lowerThreshold,
                     boolean hasAlert) {
        this.setThisPropertyIncluded(thisPropertyIncluded);
        this.setIncludeUpper(includeUpper);
        this.setIncludeLower(includeLower);
        this.setUpperThreshold(upperThreshold);
        this.setLowerThreshold(lowerThreshold);
        this.setHasAlert(hasAlert);

    }

    public MeasuredProperty() {

    }

    public boolean checkThresholdIsOK(int dataSample) {
        boolean bool1  = false;
        boolean bool2  = false;

        if (isIncludeUpper()) {
            if (dataSample < getUpperThreshold()) {
                bool1 = true;
            }
        }else{
            bool1 = true;
        }

       if (isIncludeLower()) {
            if (dataSample > getLowerThreshold()) {
                bool2 = true;
            }
       }else{
           bool2 = true;
       }
        return bool1&bool2;
    }
    @XmlElement
    public boolean isThisPropertyIncluded() {
        return thisPropertyIncluded;
    }

    public void setThisPropertyIncluded(boolean thisPropertyIncluded) {
        this.thisPropertyIncluded = thisPropertyIncluded;
    }
    @XmlElement
    public boolean isIncludeUpper() {
        return includeUpper;
    }

    public void setIncludeUpper(boolean includeUpper) {
        this.includeUpper = includeUpper;
    }
    @XmlElement
    public boolean isIncludeLower() {
        return includeLower;
    }

    public void setIncludeLower(boolean includeLower) {
        this.includeLower = includeLower;
    }
    @XmlElement
    public int getUpperThreshold() {
        return upperThreshold;
    }

    public void setUpperThreshold(int upperThreshold) {
        this.upperThreshold = upperThreshold;
    }
    @XmlElement
    public int getLowerThreshold() {
        return lowerThreshold;
    }

    public void setLowerThreshold(int lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }
    @XmlElement
    public boolean isHasAlert() {
        return hasAlert;
    }

    public void setHasAlert(boolean hasAlert) {
        this.hasAlert = hasAlert;
    }
}