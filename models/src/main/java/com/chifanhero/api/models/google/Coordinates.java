package com.chifanhero.api.models.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by shiyan on 6/14/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {

    private Double lat;
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
