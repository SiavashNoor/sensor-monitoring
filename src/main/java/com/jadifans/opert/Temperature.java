package com.jadifans.opert;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Temperature extends MeasuredProperty {

    Temperature(boolean thisPropertyIncluded,
                boolean includeUpper,
                boolean includeLower,
                int upperThreshold,
                int lowerThreshold,
                boolean hasAlert) {
        super(thisPropertyIncluded, includeUpper, includeLower, upperThreshold, lowerThreshold, hasAlert);
    }

    public Temperature() {
    }
}

