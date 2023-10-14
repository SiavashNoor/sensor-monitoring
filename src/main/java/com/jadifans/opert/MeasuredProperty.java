package com.jadifans.opert;

public class MeasuredProperty {


    // this variable is used to be more general and
    boolean thisPropertyIncluded;
    boolean includeUpper;
    boolean includeLower;
    int upperThreshold;
    int lowerThreshold;
    boolean hasAlert;

    public void setThisPropertyIncluded(boolean thisPropertyIncluded) {
        this.thisPropertyIncluded = thisPropertyIncluded;
    }

    public void setIncludeUpper(boolean includeUpper) {
        this.includeUpper = includeUpper;
    }

    public void setIncludeLower(boolean includeLower) {
        this.includeLower = includeLower;
    }

    public void setUpperThreshold(int upperThreshold) {
        this.upperThreshold = upperThreshold;
    }

    public void setLowerThreshold(int lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

    public void setHasAlert(boolean hasAlert) {
        this.hasAlert = hasAlert;
    }

    public boolean isThisPropertyIncluded() {
        return thisPropertyIncluded;
    }

    public boolean isIncludeUpper() {
        return includeUpper;
    }

    public boolean isIncludeLower() {
        return includeLower;
    }

    public int getUpperThreshold() {
        return upperThreshold;
    }

    public int getLowerThreshold() {
        return lowerThreshold;
    }

    public boolean isHasAlert() {
        return hasAlert;
    }

    MeasuredProperty(boolean thisPropertyIncluded,
                     boolean includeUpper,
                     boolean includeLower,
                     int upperThreshold,
                     int lowerThreshold,
                     boolean hasAlert) {
        this.thisPropertyIncluded = thisPropertyIncluded;
        this.includeUpper = includeUpper;
        this.includeLower = includeLower;
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
        this.hasAlert = hasAlert;

    }


    public boolean checkThreshold(int dataSample) {
        boolean bool = false;
        if (thisPropertyIncluded && hasAlert) {
            if (includeUpper) {
                if (dataSample >= upperThreshold) {
                    bool = true;
                }
            } else if (includeLower) {
                if (dataSample <= lowerThreshold) {
                    bool = true;
                }
            }
        }
        return bool;
    }


}
