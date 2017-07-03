package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by shiyan on 7/2/17.
 */
public class FilterFunctionTest {

    @Test
    public void testOpenNowTrue() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setOpenNow(true);
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(createInput());
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getResults().size());
    }

    @Test
    public void testOpenNowFalse() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setOpenNow(false);
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(createInput());
        Assert.assertNotNull(response);
        Assert.assertEquals(3, response.getResults().size());
    }

    private RestaurantSearchResponse createInput() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant(true), createRestaurant(false), createRestaurant(null)));
        return input;
    }

    private Restaurant createRestaurant(Boolean opennow) {
        Restaurant restaurant = new Restaurant();
        restaurant.setOpenNow(opennow);
        return restaurant;
    }
}
