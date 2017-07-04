package com.chifanhero.api.models.google;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by shiyan on 6/14/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {

    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
