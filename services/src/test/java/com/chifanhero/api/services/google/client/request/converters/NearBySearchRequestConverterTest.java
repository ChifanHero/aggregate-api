package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.RankBy;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 6/14/17.
 */
public class NearBySearchRequestConverterTest {

    @Test
    public void testDistanceSort() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(37.322);
        location.setLon(-121.965);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setOpenNow(true);
        nearbySearchRequest.setKeyword("chinese");
        nearbySearchRequest.setRadius(500);
        nearbySearchRequest.setSortOrder(SortOrder.NEAREST.name());
        nearbySearchRequest.setType("restaurant");
        NearBySearchRequestParams nearBySearchRequestParams = NearBySearchRequestConverter.toParams(nearbySearchRequest);
        Assert.assertEquals("37.322,-121.965", nearBySearchRequestParams.getLocation());
        Assert.assertEquals(true, nearBySearchRequestParams.isOpenNow());
        Assert.assertEquals("chinese", nearBySearchRequestParams.getKeyword());
        Assert.assertEquals(RankBy.distance, nearBySearchRequestParams.getRankBy());
        Assert.assertNull(nearBySearchRequestParams.getRadius());
    }

    @Test
    public void testNonDistanceSort() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(37.322);
        location.setLon(-121.965);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setOpenNow(true);
        nearbySearchRequest.setKeyword("chinese");
        nearbySearchRequest.setType("restaurant");
        nearbySearchRequest.setRadius(500);
        NearBySearchRequestParams nearBySearchRequestParams = NearBySearchRequestConverter.toParams(nearbySearchRequest);
        Assert.assertEquals("37.322,-121.965", nearBySearchRequestParams.getLocation());
        Assert.assertEquals(true, nearBySearchRequestParams.isOpenNow());
        Assert.assertEquals("chinese", nearBySearchRequestParams.getKeyword());
        Assert.assertNull(nearBySearchRequestParams.getRankBy());
        Assert.assertEquals("500", nearBySearchRequestParams.getRadius());
    }

}
