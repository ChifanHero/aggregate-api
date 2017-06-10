package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ElasticsearchService {

    RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest);

    RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest);
}
