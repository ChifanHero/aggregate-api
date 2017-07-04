package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.Coordinates;

/**
 * Created by shiyan on 6/14/17.
 */
public class CoordinatesConverter {

    public static com.chifanhero.api.models.response.Coordinates toCommonCoordinates(Coordinates googleCoordinates) {
        if (googleCoordinates == null) {
            return null;
        }
        com.chifanhero.api.models.response.Coordinates coordinates = new com.chifanhero.api.models.response.Coordinates();
        coordinates.setLatitude(googleCoordinates.getLat());
        coordinates.setLongitude(googleCoordinates.getLng());
        return coordinates;
    }
}
