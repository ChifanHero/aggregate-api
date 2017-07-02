package com.chifanhero.api.functions;

import com.chifanhero.api.helpers.RestaurantDeduper;
import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by shiyan on 6/26/17.
 */
public class RestaurantsMergeFunction implements Function<List<RestaurantSearchResponse>, RestaurantSearchResponse> {
    @Override
    public RestaurantSearchResponse apply(List<RestaurantSearchResponse> restaurantSearchResponses) {
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        List<Error> errors = new ArrayList<>();
        List<Restaurant> results = new ArrayList<>();
        restaurantSearchResponses.forEach(searchResponse -> {
            Optional.ofNullable(searchResponse.getErrors()).ifPresent(errors::addAll);
            Optional.ofNullable(searchResponse.getResults()).ifPresent(results::addAll);
        });
        List<Restaurant> deduped = RestaurantDeduper.dedupe(results);
        restaurantSearchResponse.setErrors(errors);
        restaurantSearchResponse.setResults(deduped);
        return restaurantSearchResponse;
    }
}
