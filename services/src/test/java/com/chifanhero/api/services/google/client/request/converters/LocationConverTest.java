package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.Location;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 6/14/17.
 */
public class LocationConverTest {

    @Test
    public void test() {
        Location location = new Location();
        location.setLat(37.335);
        location.setLon(-122.015);
        String locationParam = LocationConverter.toLocationParam(location);
        Assert.assertEquals("37.335,-122.015", locationParam);
    }
}
