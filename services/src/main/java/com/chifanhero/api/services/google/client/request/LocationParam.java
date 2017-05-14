package com.chifanhero.api.services.google.client.request;

import com.chifanhero.api.common.GetRequestParam;

import javax.validation.constraints.NotNull;

/**
 * Location parameter
 * Created by shiyan on 5/3/17.
 */
public class LocationParam extends GetRequestParam<String> {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public LocationParam setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public LocationParam setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String getParam() {
        validate();
        return String.valueOf(latitude) +
                "," +
                longitude;
    }
}
