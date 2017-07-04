package com.chifanhero.api.models.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by shiyan on 4/27/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
