package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class ElasticTextSearchTask implements Callable<RestaurantSearchResponse> {

    public ElasticTextSearchTask(TextSearchRequest textSearchRequest) {

    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return null;
    }
}
