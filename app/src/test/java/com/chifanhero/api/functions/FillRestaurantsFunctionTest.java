package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiyan on 7/2/17.
 */
public class FillRestaurantsFunctionTest {

    @Test
    public void test() {
        RestaurantSearchResponse input = new RestaurantSearchResponse();
        input.setResults(Arrays.asList(createRestaurant("1"), createRestaurant("2")));
        GooglePlacesService mockGooglePlacesService = EasyMock.mock(GooglePlacesService.class);
        EasyMock.expect(mockGooglePlacesService.batchGet(Arrays.asList("1", "2"))).andReturn(createResponseMap());
        EasyMock.replay(mockGooglePlacesService);
        FillRestaurantsFunction fillRestaurantsFunction = new FillRestaurantsFunction(mockGooglePlacesService);
        RestaurantSearchResponse response = fillRestaurantsFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertEquals(2, response.getResults().size());
        response.getResults().forEach(restaurant -> {
            if (restaurant.getPlaceId().equals("1")) {
                Assert.assertEquals("restaurant1", restaurant.getName());
            } else if (restaurant.getPlaceId().equals("2")) {
                Assert.assertEquals("restaurant2", restaurant.getName());
            } else {
                Assert.fail();
            }
        });
    }

    private Map<String,Restaurant> createResponseMap() {
        Map<String, Restaurant> responseMap = new HashMap<>();
        Restaurant restaurant1 = createRestaurant("1");
        restaurant1.setName("restaurant1");
        responseMap.put("1", restaurant1);
        Restaurant restaurant2 = createRestaurant("2");
        restaurant2.setName("restaurant2");
        responseMap.put("2", restaurant2);
        return responseMap;
    }

    private Restaurant createRestaurant(String placeId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId(placeId);
        return restaurant;
    }
}
