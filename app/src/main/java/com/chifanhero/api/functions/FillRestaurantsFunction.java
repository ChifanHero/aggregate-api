package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 6/26/17.
 */
public class FillRestaurantsFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private final GooglePlacesService googlePlacesService;

    public FillRestaurantsFunction(GooglePlacesService googlePlacesService) {
        this.googlePlacesService = googlePlacesService;
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            List<String> placeIds = restaurants.stream().map(Restaurant::getPlaceId).collect(Collectors.toList());
            Map<String, Restaurant> restaurantMap = googlePlacesService.batchGet(placeIds);
            List<Restaurant> fulfilled = new ArrayList<>(restaurantMap.values());
            input.setResults(fulfilled);
        });
        return input;
    }
}
