package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.chifanhero.api.utils.GeoUtil;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyan on 7/2/17.
 */
public class GoogleNearbySearchTaskTest {

    @Test
    public void test() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
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
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest, locations)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, mockService);
        RestaurantSearchResponse response = googleNearbySearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
    }
}
