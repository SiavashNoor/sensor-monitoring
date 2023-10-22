package com.jadifans.opert;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Humidity extends MeasuredProperty {

    Humidity(boolean thisPropertyIncluded,
             boolean includeUpper,
             boolean includeLower,
             int upperThreshold,
             int lowerThreshold,
             boolean hasAlert) {
        super(thisPropertyIncluded,
                includeUpper,
                includeLower,
                upperThreshold,
                lowerThreshold,
                hasAlert);
    }

    public Humidity() {
    }
}
