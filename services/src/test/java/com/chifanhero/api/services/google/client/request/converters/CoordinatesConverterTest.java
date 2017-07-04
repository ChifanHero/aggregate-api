package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.Coordinates;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 6/14/17.
 */
public class CoordinatesConverterTest {

    @Test
    public void test() {
        Coordinates googleCoordinates = new Coordinates();
        googleCoordinates.setLat(37.335);
        googleCoordinates.setLng(-122.015);
        com.chifanhero.api.models.response.Coordinates coordinates = CoordinatesConverter.toCommonCoordinates(googleCoordinates);
        Assert.assertTrue(coordinates.getLatitude() == 37.335);
        Assert.assertTrue(coordinates.getLongitude() == -122.015);
    }
}
