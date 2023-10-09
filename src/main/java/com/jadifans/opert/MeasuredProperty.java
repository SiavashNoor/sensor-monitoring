package com.jadifans.opert;

public class MeasuredProperty {


    // this variable is used to be more general and
    boolean thisPropertyIncluded;
    boolean includeUpper;
    boolean includeLower;
    int upperThreshold;
    int lowerThreshold;
    boolean hasAlert;


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
