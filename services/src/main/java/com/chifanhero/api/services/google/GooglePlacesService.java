package com.chifanhero.api.services.google;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 5/1/17.
 */
public interface GooglePlacesService {

    RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest);

    RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest);

    Restaurant get(String placeId);

    Map<String, Restaurant> batchGet(List<String> placeIds);

}
