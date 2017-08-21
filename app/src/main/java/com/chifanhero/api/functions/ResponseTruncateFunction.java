package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.Optional;

/**
 * Created by shiyan on 8/20/17.
 */
public class ResponseTruncateFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> input.setResults(restaurants.subList(0, 20)));
        return input;
    }
}
