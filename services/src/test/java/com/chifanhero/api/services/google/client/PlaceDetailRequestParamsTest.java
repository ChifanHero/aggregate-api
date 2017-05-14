package com.chifanhero.api.services.google.client;

import com.chifanhero.api.services.google.client.request.PlaceDetailRequestParams;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PlaceDetailRequestParamsTest {

    @Test
    public void test() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setKey("key");
        placeDetailRequestParams.setPlaceId("1qaz2wsx");
        Map<String, Object> params = placeDetailRequestParams.getParams();
        Assert.assertEquals("key", params.get("key"));
        Assert.assertEquals("1qaz2wsx", params.get("placeid"));
    }


    @Test
    public void testReferenceOnly() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setKey("key");
        placeDetailRequestParams.setReference("1qaz2wsx");
        Map<String, Object> params = placeDetailRequestParams.getParams();
        Assert.assertEquals("key", params.get("key"));
        Assert.assertEquals("1qaz2wsx", params.get("reference"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceIdAndReference() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setKey("key");
        placeDetailRequestParams.setReference("1qaz2wsx");
        placeDetailRequestParams.setPlaceId("1234");
        placeDetailRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoPlaceIdOrReference() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setKey("key");
        placeDetailRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyRequired() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setPlaceId("1234");
        placeDetailRequestParams.getParams();
    }
}
