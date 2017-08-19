package com.chifanhero.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by shiyan on 8/18/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Distance {

    private Double value;
    private DistanceUnit unit;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DistanceUnit getUnit() {
        return unit;
    }

    public void setUnit(DistanceUnit unit) {
        this.unit = unit;
    }
}
