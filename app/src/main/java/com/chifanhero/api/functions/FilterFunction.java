package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.Optional;

/**
 * Created by shiyan on 6/28/17.
 */
public class FilterFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private boolean openNow = false;

    public FilterFunction(NearbySearchRequest nearbySearchRequest) {
        if (nearbySearchRequest.getOpenNow() != null) {
            openNow = nearbySearchRequest.getOpenNow();
        }
    }

    public FilterFunction(TextSearchRequest textSearchRequest) {
        if (textSearchRequest.getOpenNow() != null) {
            openNow = textSearchRequest.getOpenNow();
        }
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        if (!openNow) {
            return input;
        }
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            restaurants.removeIf(restaurant -> restaurant.getOpenNow() == null || !restaurant.getOpenNow());
        });
        return input;
    }
}
