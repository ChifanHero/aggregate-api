package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class ElasticNearbySearchTask implements Callable<RestaurantSearchResponse> {

    public ElasticNearbySearchTask(NearbySearchRequest nearbySearchRequest) {

    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return null;
    }
}
