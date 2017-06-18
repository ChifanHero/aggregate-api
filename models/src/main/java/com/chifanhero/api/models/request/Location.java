package com.chifanhero.api.models.request;

import java.util.Optional;

/**
 * Created by shiyan on 5/6/17.
 */
public class Location extends RequestComponent {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Optional.ofNullable(lat).ifPresent(value ->
                sb.append("lat=").append(value).append(";")
        );
        Optional.ofNullable(lon).ifPresent(value ->
                sb.append("lon=").append(value).append(";")
        );
        if (sb.charAt(sb.length() - 1) == ';') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
