package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class ElasticNearbySearchTask implements Callable<RestaurantSearchResponse> {

    private NearbySearchRequest nearbySearchRequest;
    private final ElasticsearchService elasticsearchService;

    public ElasticNearbySearchTask(NearbySearchRequest nearbySearchRequest, ElasticsearchService elasticsearchService) {
        this.nearbySearchRequest = nearbySearchRequest;
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return elasticsearchService.nearBySearch(nearbySearchRequest);
    }
}
