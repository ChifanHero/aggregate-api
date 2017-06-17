package com.chifanhero.api.models.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by shiyan on 6/14/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    private Coordinates location;

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }
}
