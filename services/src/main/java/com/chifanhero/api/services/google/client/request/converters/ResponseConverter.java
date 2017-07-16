package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Convert PlacesSearchResponse to RestaurantSearchResponse
 * Created by shiyan on 6/13/17.
 */
public class ResponseConverter {

    public static RestaurantSearchResponse toRestaurantSearchResponse(PlacesSearchResponse placesSearchResponse) {
        Preconditions.checkNotNull(placesSearchResponse);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        Optional.ofNullable(placesSearchResponse.getResults()).ifPresent(results -> {
            List<Restaurant> restaurants = results.stream().map(place -> {
                Restaurant restaurant = RestaurantConverter.toRestaurant(place);
                restaurant.setId(IdGenerator.getNewObjectId());
                return restaurant;
            }).collect(Collectors.toList());
            restaurantSearchResponse.setResults(restaurants);
        });
        return restaurantSearchResponse;
    }
}
