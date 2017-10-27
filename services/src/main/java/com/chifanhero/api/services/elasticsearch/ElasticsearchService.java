package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

import java.util.Map;
import java.util.Set;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ElasticsearchService {

    RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest);

    RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest);

    Map<String, Restaurant> batchGetRestaurant(Set<String> restaurantIds);
}
