package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 7/2/17.
 */
public class GoogleNearbySearchTaskTest {

    @Test
    public void test() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setKeyword("chinese");
        GooglePlacesService mockService = EasyMock.mock(GooglePlacesService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        EasyMock.expect(mockService.nearBySearch(nearbySearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, mockService);
        RestaurantSearchResponse response = googleNearbySearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
    }
}
