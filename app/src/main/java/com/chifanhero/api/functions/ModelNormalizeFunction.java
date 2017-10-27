package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.Optional;

/**
 * Created by shiyan on 7/17/17.
 */
public class ModelNormalizeFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> restaurants.forEach(restaurant -> {
            if (restaurant.getName() == null) {
                restaurant.setName(restaurant.getGoogleName());
            }
        }));
        return input;
    }
}
