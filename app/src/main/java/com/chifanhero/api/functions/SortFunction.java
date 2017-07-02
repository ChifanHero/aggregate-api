package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.Optional;

/**
 * Created by shiyan on 6/28/17.
 */
public class SortFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private SortOrder sortOrder;

    public SortFunction(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            if (sortOrder == SortOrder.NEAREST) {
                restaurants.sort((restaurant1, restaurant2) -> restaurant1.getDistance() >= restaurant2.getDistance()? 1: -1);
            } else if (sortOrder == SortOrder.HOTTEST) {
                restaurants.sort((restaurant1, restaurant2) -> {
                    if (restaurant1.getRating() == null) {
                        return 1;
                    }
                    if (restaurant2.getRating() == null) {
                        return 2;
                    }
                    return restaurant1.getRating() > restaurant2.getRating()? 1: -1;
                });
            }
        });
        return input;
    }
}
