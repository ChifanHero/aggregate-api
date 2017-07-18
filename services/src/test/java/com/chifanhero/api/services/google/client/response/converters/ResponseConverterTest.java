package com.chifanhero.api.services.google.client.response.converters;

import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.client.response.converters.ResponseConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyan on 6/15/17.
 */
public class ResponseConverterTest {

    @Test
    public void test() {
        PlacesSearchResponse placesSearchResponse = new PlacesSearchResponse();
        List<Place> results = new ArrayList<>();
        Place place = new Place();
        results.add(place);
        placesSearchResponse.setResults(results);
        RestaurantSearchResponse restaurantSearchResponse = ResponseConverter.toRestaurantSearchResponse(placesSearchResponse);
        Assert.assertTrue(restaurantSearchResponse.getResults().size() == 1);
        Restaurant restaurant = restaurantSearchResponse.getResults().get(0);
        Assert.assertNotNull(restaurant.getId());
    }
}
