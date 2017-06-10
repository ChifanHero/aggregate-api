package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.models.response.Restaurant;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ChifanheroRestaurantService {

    void bulkUpsert(List<Restaurant> entities);

    Map<String, Restaurant> batchGetByGooglePlaceId(List<String> googlePlaceIds);

    void bulkUpsertInBackground(List<Restaurant> entities);

    void expireDocuments();
}
