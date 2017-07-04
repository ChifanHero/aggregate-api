package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class ElasticTextSearchTask implements Callable<RestaurantSearchResponse> {

    private TextSearchRequest textSearchRequest;
    private final ElasticsearchService elasticsearchService;

    public ElasticTextSearchTask(TextSearchRequest textSearchRequest, ElasticsearchService elasticsearchService) {
        this.textSearchRequest = textSearchRequest;
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return elasticsearchService.textSearch(textSearchRequest);
    }
}
