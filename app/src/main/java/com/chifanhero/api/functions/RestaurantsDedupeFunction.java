package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.List;

/**
 * Created by shiyan on 6/26/17.
 */
public class RestaurantsDedupeFunction implements Function<List<RestaurantSearchResponse>, RestaurantSearchResponse> {
    @Override
    public RestaurantSearchResponse apply(List<RestaurantSearchResponse> restaurantSearchResponses) {
        return null;
    }
}
