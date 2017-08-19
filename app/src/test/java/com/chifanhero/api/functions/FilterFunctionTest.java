package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.Distance;
import com.chifanhero.api.models.response.DistanceUnit;
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
        RestaurantSearchResponse response = filterFunction.apply(createInputForOpennowTest());
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getResults().size());
    }

    @Test
    public void testOpenNowFalse() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setOpenNow(false);
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(createInputForOpennowTest());
        Assert.assertNotNull(response);
        Assert.assertEquals(3, response.getResults().size());
    }

    @Test
    public void testRating() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRating(4.5);
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(createInputForRatingTest());
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getResults().size());
    }

    @Test
    public void testRadius() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(3200);
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(createInputForRadiusTest());
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getResults().size());
        Assert.assertTrue(response.getResults().get(0).getDistance().getValue() == 1.5);
    }

    @Test
    public void testBlacklisted() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        Restaurant restaurant = new Restaurant();
        restaurant.setBlacklisted(true);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setBlacklisted(false);
        input.setResults(Arrays.asList(restaurant, restaurant2));
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        FilterFunction filterFunction = new FilterFunction(nearbySearchRequest);
        RestaurantSearchResponse response = filterFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getResults().size());
        Assert.assertFalse(response.getResults().get(0).getBlacklisted());
    }

    private RestaurantSearchResponse createInputForOpennowTest() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant(true, null, null), createRestaurant(false, null, null), createRestaurant(null, null, null)));
        return input;
    }

    private RestaurantSearchResponse createInputForRadiusTest() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant(null, null, 2.1), createRestaurant(null, null, 1.5), createRestaurant(null, null, null)));
        return input;
    }

    private RestaurantSearchResponse createInputForRatingTest() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant(null, 4.5, null), createRestaurant(null, 4.0, null), createRestaurant(null, null, null)));
        return input;
    }

    private Restaurant createRestaurant(Boolean opennow, Double rating, Double distanceValue) {
        Restaurant restaurant = new Restaurant();
        restaurant.setOpenNow(opennow);
        restaurant.setRating(rating);
        Distance distance = new Distance();
        distance.setValue(distanceValue);
        distance.setUnit(DistanceUnit.mi);
        restaurant.setDistance(distance);
        return restaurant;
    }
}
