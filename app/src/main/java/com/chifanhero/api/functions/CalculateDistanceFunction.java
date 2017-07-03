package com.chifanhero.api.functions;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.utils.DistanceCalculator;
import com.google.common.base.Function;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 6/29/17.
 */
public class CalculateDistanceFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private Location center;

    public CalculateDistanceFunction(Location center) {
        this.center = center;
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> restaurants.forEach(restaurant -> {
            Coordinates coordinates = restaurant.getCoordinates();
            if (coordinates != null) {
                Double distanceInMi = DistanceCalculator.getDistanceInMi(center.getLat(), center.getLon(), coordinates.getLatitude(), coordinates.getLongitude(), 2);
                restaurant.setDistance(distanceInMi);
            }
        }));
        return input;
    }
}
