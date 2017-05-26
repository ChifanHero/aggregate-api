package com.chifanhero.api.services.google.client;

import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.RankBy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PlaceNearBySearchRequestParamsTest {

    @Test
    public void test() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRadius("500");
        placeNearBySearchRequestParams.setKeyword("restaurant");
        placeNearBySearchRequestParams.setLanguage("en");
        placeNearBySearchRequestParams.setOpenNow(true);
        placeNearBySearchRequestParams.setPageToken("page_token");
        placeNearBySearchRequestParams.setRankBy(RankBy.prominence);
        placeNearBySearchRequestParams.setType("chinese");
        Map<String, Object> params = placeNearBySearchRequestParams.getParams();
        Assert.assertEquals("key", params.get("key"));
        Assert.assertEquals("37.242312,-121.764886", params.get("location"));
        Assert.assertEquals("500", params.get("radius"));
        Assert.assertEquals("restaurant", params.get("keyword"));
        Assert.assertEquals("en", params.get("language"));
        Assert.assertEquals(true, params.get("opennow"));
        Assert.assertEquals("page_token", params.get("pagetoken"));
        Assert.assertEquals(RankBy.prominence, params.get("rankby"));
        Assert.assertEquals("chinese", params.get("type"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyRequired() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRadius("500");
        placeNearBySearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocationRequired() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setRadius("500");
        placeNearBySearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxRadius() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRadius("500000");
        placeNearBySearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRadiusRequiredForNonDistanceRankby() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRankByDistanceNoTypeNoKeyword() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRankBy(RankBy.distance);
        placeNearBySearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRankByDistanceWithRadius() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setKeyword("chinese");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRankBy(RankBy.distance);
        placeNearBySearchRequestParams.setRadius("500");
        placeNearBySearchRequestParams.getParams();
    }

    @Test
    public void testRankByDistanceWithKeyword() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setKeyword("chinese");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRankBy(RankBy.distance);
        Map<String, Object> params = placeNearBySearchRequestParams.getParams();
        Assert.assertEquals("key", params.get("key"));
        Assert.assertEquals("chinese", params.get("keyword"));
        Assert.assertEquals("37.242312,-121.764886", params.get("location"));
        Assert.assertEquals(RankBy.distance, params.get("rankby"));
    }

    @Test
    public void testRankByDistanceWithType() {
        NearBySearchRequestParams placeNearBySearchRequestParams = new NearBySearchRequestParams();
        placeNearBySearchRequestParams.setKey("key");
        placeNearBySearchRequestParams.setType("chinese");
        placeNearBySearchRequestParams.setLocation("37.242312,-121.764886");
        placeNearBySearchRequestParams.setRankBy(RankBy.distance);
        Map<String, Object> params = placeNearBySearchRequestParams.getParams();
        Assert.assertEquals("key", params.get("key"));
        Assert.assertEquals("chinese", params.get("type"));
        Assert.assertEquals("37.242312,-121.764886", params.get("location"));
        Assert.assertEquals(RankBy.distance, params.get("rankby"));
    }
}
