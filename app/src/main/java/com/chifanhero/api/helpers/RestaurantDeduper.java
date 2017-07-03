package com.chifanhero.api.helpers;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.Source;

import java.util.*;

/**
 * Dedupe restaurants by placeId
 * Created by shiyan on 6/24/17.
 */
public class RestaurantDeduper {
    /**
     * Dedupe restaurants by placeId.
     * It will throw out invalid restaurant (with no placeId or source)
     * If two restaurants have the same placeId, the one come from google will be kept
     * @param restaurants
     * @return
     */
    public List<Restaurant> dedupe(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) {
            return Collections.emptyList();
        }
        List<Restaurant> deduped = new ArrayList<>();
        Map<String, Restaurant> map = new HashMap<>();
        restaurants.stream().filter(this::isValid).forEach(restaurant -> {
            Restaurant existing = map.get(restaurant.getPlaceId());
            if (existing == null) {
                map.put(restaurant.getPlaceId(), restaurant);
            } else {
                if (existing.getSource() == Source.CHIFANHERO) {
                    existing.applyPatch(restaurant);
                } else if (existing.getSource() == Source.GOOGLE) {
                    restaurant.applyPatch(existing);
                    map.put(restaurant.getPlaceId(), restaurant);
                }
            }
        });
        map.forEach((placeId, restaurant) -> deduped.add(restaurant));
        return deduped;
    }

    private boolean isValid(Restaurant restaurant) {
        return restaurant != null
                && restaurant.getSource() != null
                && restaurant.getPlaceId() != null;
    }
}
