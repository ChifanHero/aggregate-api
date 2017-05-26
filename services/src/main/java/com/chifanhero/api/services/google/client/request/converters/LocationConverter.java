package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.services.google.client.request.LocationParam;

public class LocationConverter {

    public static String toLocationParam(Location location) {
        LocationParam locationParam = new LocationParam();
        locationParam.setLongitude(location.getLon());
        locationParam.setLatitude(location.getLat());
        return locationParam.getParam();
    }
}
