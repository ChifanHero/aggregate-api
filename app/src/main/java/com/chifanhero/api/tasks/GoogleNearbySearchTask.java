package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class GoogleNearbySearchTask implements Callable<RestaurantSearchResponse> {

    public GoogleNearbySearchTask(NearbySearchRequest nearbySearchRequest) {

    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return null;
    }
}
