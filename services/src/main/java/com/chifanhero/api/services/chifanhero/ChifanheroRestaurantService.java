package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.common.exceptions.ChifanheroException;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ChifanheroRestaurantService {

    void bulkUpsert(List<Restaurant> entities);

    Map<String, Restaurant> batchGetByGooglePlaceId(List<String> googlePlaceIds);

    void bulkUpsertInBackground(List<Restaurant> entities);

    void expireData();

    void markRecommendations(List<Restaurant> restaurants);

    void trackViewCount(String restaurantId);

    void tryPublishRestaurant(String restaurantId);

    UserInfo createNewUser(String userId) throws ChifanheroException;

    UserInfo getUser(String userId);
}
