package com.chifanhero.api.cache;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import org.junit.Assert;
import org.junit.Test;

public class CacheKeyRetrieverTest {

    @Test
    public void testNearbySearchRequest() {
        NearbySearchRequest nearbySearchRequest = createNearbySearchRequest();
        String cacheKey = CacheKeyRetriever.from(nearbySearchRequest);
        Assert.assertEquals("{location={lat=37.1235;lon=-121.6543};radius=500;type=restaurant;keyword=chinese;sortOrder=NEAREST;openNow=false;rating=4.5}", cacheKey);
    }

    @Test
    public void testTextSearchRequest() {
        TextSearchRequest textSearchRequest = createTextSearchRequest();
        String cacheKey = CacheKeyRetriever.from(textSearchRequest);
        Assert.assertEquals("{query=shao mountain;type=restaurant;location={lat=37.1235;lon=-121.6543};radius=500;sortOrder=NEAREST;openNow=true;rating=4.5}", cacheKey);
    }

    private TextSearchRequest createTextSearchRequest() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setRating(4.5);
        textSearchRequest.setLocation(createLocation());
        textSearchRequest.setQuery("shao mountain");
        textSearchRequest.setRadius(500);
        textSearchRequest.setOpenNow(true);
        textSearchRequest.setSortOrder(SortOrder.NEAREST.name());
        textSearchRequest.setType("restaurant");
        return textSearchRequest;
    }

    private NearbySearchRequest createNearbySearchRequest() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setType("restaurant");
        nearbySearchRequest.setSortOrder(SortOrder.NEAREST.name());
        nearbySearchRequest.setKeyword("chinese");
        nearbySearchRequest.setRadius(500);
        nearbySearchRequest.setRating(4.5);
        Location location = createLocation();
        nearbySearchRequest.setLocation(location);
        return nearbySearchRequest;
    }

    private Location createLocation() {
        Location location = new Location();
        location.setLat(37.123456);
        location.setLon(-121.654321);
        return location;
    }
}
