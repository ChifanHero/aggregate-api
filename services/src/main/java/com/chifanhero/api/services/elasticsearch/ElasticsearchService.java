package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.SearchResponse;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ElasticsearchService {

    SearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest);

    SearchResponse textSearch(TextSearchRequest textSearchRequest);
}
