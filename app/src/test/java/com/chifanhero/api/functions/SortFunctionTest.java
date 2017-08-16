package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by shiyan on 7/2/17.
 */
public class SortFunctionTest {

    @Test
    public void testNearest() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant("1", null, 2.0), createRestaurant("2", null, 1.0), createRestaurant("3", null, null)));
        SortFunction sortFunction = new SortFunction(SortOrder.NEAREST);
        RestaurantSearchResponse response = sortFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertEquals("2", response.getResults().get(0).getId());
        Assert.assertEquals("1", response.getResults().get(1).getId());
        Assert.assertEquals("3", response.getResults().get(2).getId());
    }

    @Test
    public void testRating() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant("1", 4.0, null), createRestaurant("2", 5.0, null), createRestaurant("3", null, null)));
        SortFunction sortFunction = new SortFunction(SortOrder.RATING);
        RestaurantSearchResponse response = sortFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertEquals("2", response.getResults().get(0).getId());
        Assert.assertEquals("1", response.getResults().get(1).getId());
        Assert.assertEquals("3", response.getResults().get(2).getId());
    }

    private Restaurant createRestaurant(String id, Double rating, Double distance) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setRating(rating);
        restaurant.setDistance(distance);
        return restaurant;
    }
}
