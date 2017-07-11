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
                restaurants.sort((restaurant1, restaurant2) -> {
                    if (restaurant1.getDistance() == null) {
                        return 1;
                    }
                    if (restaurant2.getDistance() == null) {
                        return -1;
                    }
                    return restaurant1.getDistance() >= restaurant2.getDistance()? 1: -1;
                });
            } else if (sortOrder == SortOrder.HOTTEST) {
                restaurants.sort((restaurant1, restaurant2) -> {
                    if (restaurant1.getRating() == null) {
                        return 1;
                    }
                    if (restaurant2.getRating() == null) {
                        return -1;
                    }
                    return restaurant1.getRating() >= restaurant2.getRating()? -1: 1;
                });
            } else {
                // promote chifanhero restaurants with score > 1
                restaurants.sort((restaurant1, restaurant2) -> {
                    Double score1 = 0.0;
                    Double score2 = 0.0;
                    if (restaurant1.getScore() != null) {
                        score1 = restaurant1.getScore() >= 1? restaurant1.getScore(): score1;
                    }
                    if (restaurant2.getScore() != null) {
                        score2 = restaurant2.getScore() >= 1? restaurant2.getScore(): score2;
                    }
                    if (score1 > score2) {
                        return -1;
                    } else if (score1.equals(score2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                });
            }
        });
        return input;
    }
}
