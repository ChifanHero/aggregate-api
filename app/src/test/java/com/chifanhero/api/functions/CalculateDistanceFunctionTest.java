package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.utils.DistanceCalculator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * Created by shiyan on 7/2/17.
 */
public class CalculateDistanceFunctionTest {

    @Test
    public void test() {
        Location center = new Location();
        center.setLon(-121.34);
        center.setLat(37.56);
        CalculateDistanceFunction calculateDistanceFunction = new CalculateDistanceFunction(center);
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        List<Restaurant> restaurants = Collections.singletonList(createRestaurant());
        input.setResults(restaurants);
        RestaurantSearchResponse response = calculateDistanceFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getResults());
        Restaurant restaurant = response.getResults().get(0);
        Assert.assertTrue(restaurant.getDistance().getValue().doubleValue() == DistanceCalculator.getDistanceInMi(37.56, -121.34, 37.57, -121.12, 2).doubleValue());
    }

    private Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        Coordinates coordinates = new Coordinates();
        coordinates.setLongitude(-121.12);
        coordinates.setLatitude(37.57);
        restaurant.setCoordinates(coordinates);
        return restaurant;
    }

}
