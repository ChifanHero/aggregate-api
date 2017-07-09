package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.google.common.base.Function;

import java.util.*;
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
            Map<String, Restaurant> linkedMap = new LinkedHashMap<>(); // use linked hashmap to preserve order
            Set<String> placeToFill = new HashSet<>();
            for (Restaurant restaurant : restaurants) {
                linkedMap.put(restaurant.getPlaceId(), restaurant);
                if (restaurant.getSource() == Source.CHIFANHERO) {
                    placeToFill.add(restaurant.getPlaceId());
                }
            }
            Map<String, Restaurant> filled = googlePlacesService.batchGet(new ArrayList<>(placeToFill));
            for (Map.Entry<String, Restaurant> entry : linkedMap.entrySet()) {
                if (placeToFill.contains(entry.getKey())) {
                    Restaurant filledRestaurant = entry.getValue();
                    filledRestaurant.applyPatch(filled.get(entry.getKey()));
                    linkedMap.put(entry.getKey(), filledRestaurant);
                }
            }
            input.setResults(new ArrayList<>(linkedMap.values()));
        });
        return input;
    }
}
