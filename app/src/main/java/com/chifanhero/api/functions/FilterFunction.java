package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.base.Function;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 6/28/17.
 */
public class FilterFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private Boolean openNow = false;
    private Double rating;
    private Integer radius;

    public FilterFunction(NearbySearchRequest nearbySearchRequest) {
        openNow = nearbySearchRequest.getOpenNow();
        radius = nearbySearchRequest.getRadius();
        rating = nearbySearchRequest.getRating();
    }

    public FilterFunction(TextSearchRequest textSearchRequest) {
        openNow = textSearchRequest.getOpenNow();
        radius = textSearchRequest.getRadius();
        rating = textSearchRequest.getRating();
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        filterBlacklisted(input);
        filterByOpennow(input);
        filterByDistance(input);
        filterByRating(input);
        filterPermenatelyClosed(input);
        return input;
    }

    private void filterPermenatelyClosed(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            List<Restaurant> filtered = restaurants.stream().filter(restaurant -> !Boolean.TRUE.equals(restaurant.getPermanentlyClosed())).collect(Collectors.toList());
            input.setResults(filtered);
        });
    }

    private void filterBlacklisted(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            List<Restaurant> filtered = restaurants.stream().filter(restaurant -> restaurant.getBlacklisted() == null || !restaurant.getBlacklisted()).collect(Collectors.toList());
            input.setResults(filtered);
        });
    }

    private void filterByRating(RestaurantSearchResponse input) {
        if (rating != null) {
            Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
                List<Restaurant> filtered = restaurants.stream().filter(restaurant -> restaurant.getRating() != null && restaurant.getRating() >= rating).collect(Collectors.toList());
                input.setResults(filtered);
            });
        }
    }

    private void filterByDistance(RestaurantSearchResponse input) {
        if (radius != null) {
            Double radiusInMi = ((double) radius) / (1.6d * 1000.0d);
            Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
                List<Restaurant> filtered = restaurants.stream().filter(restaurant -> restaurant.getDistance() != null && restaurant.getDistance() <= radiusInMi).collect(Collectors.toList());
                input.setResults(filtered);
            });
        }
    }

    private void filterByOpennow(RestaurantSearchResponse input) {
        if (openNow != null && openNow) {
            Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
                List<Restaurant> filtered = restaurants.stream().filter(restaurant -> Boolean.TRUE.equals(restaurant.getOpenNow())).collect(Collectors.toList());
                input.setResults(filtered);
            });
        }
    }
}
