package com.chifanhero.api.services.google.client;


import org.junit.Assert;
import org.junit.Test;

public class TestLocationParam {

    @Test
    public void test() {
        LocationParam locationParam = new LocationParam();
        locationParam.setLatitude(37.241652);
        locationParam.setLongitude(-121.763879);
        Assert.assertEquals("37.241652,-121.763879", locationParam.getParam());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLatRequired() {
        LocationParam locationParam = new LocationParam();
        locationParam.setLongitude(-121.763879);
        locationParam.getParam();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLonRequired() {
        LocationParam locationParam = new LocationParam();
        locationParam.setLatitude(37.241652);
        locationParam.getParam();
    }
}
