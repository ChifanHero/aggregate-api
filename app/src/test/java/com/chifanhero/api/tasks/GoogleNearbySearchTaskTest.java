package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.chifanhero.api.utils.GeoUtil;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by shiyan on 7/2/17.
 */
public class GoogleNearbySearchTaskTest {

    @Test
    public void test() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(1999);
        nearbySearchRequest.setKeyword("chinese");
        GooglePlacesService mockService = EasyMock.mock(GooglePlacesService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        restaurantSearchResponse.setResults(Collections.singletonList(new Restaurant()));
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, mockService);
        RestaurantSearchResponse response = googleNearbySearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
    }

    @Test
    public void testRaduis2000() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(2000);
        Location location = new Location();
        location.setLat(37.45);
        location.setLon(-121.34);
        double[][] coordinatesGroup = GeoUtil.getCoordinatesGroup(37.45, -121.34, 2);
        List<Location> locations = new ArrayList<>();
        for (double[] coordinates : coordinatesGroup) {
            Location loc = new Location();
            loc.setLat(coordinates[0]);
            loc.setLon(coordinates[1]);
            locations.add(loc);
        }
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setKeyword("chinese");
        GooglePlacesService mockService = EasyMock.mock(GooglePlacesService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        restaurantSearchResponse.setResults(Collections.singletonList(new Restaurant()));
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest, locations)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, mockService);
        RestaurantSearchResponse response = googleNearbySearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
    }

//    @Test
    public void testChineseFoodSearch() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(1999);
        NearbySearchRequest nearbySearchRequest2 = new NearbySearchRequest();
        nearbySearchRequest2.setRadius(1999);
        nearbySearchRequest2.setKeyword("chinese+food");
        GooglePlacesService mockService = EasyMock.mock(GooglePlacesService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        RestaurantSearchResponse restaurantSearchResponse2 = new RestaurantSearchResponse();
        restaurantSearchResponse2.setResults(Collections.singletonList(new Restaurant()));
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest2)).andReturn(restaurantSearchResponse2);
        EasyMock.replay(mockService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, mockService);
        RestaurantSearchResponse response = googleNearbySearchTask.call();
        Assert.assertTrue(response.getResults().size() == 1);
        Assert.assertTrue(response.getResults().get(0).isOnHold());
    }
}
