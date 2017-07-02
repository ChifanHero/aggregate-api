package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class GoogleNearbySearchTask implements Callable<RestaurantSearchResponse> {

    private NearbySearchRequest nearbySearchRequest;
    private final GooglePlacesService googlePlacesService;

    public GoogleNearbySearchTask(NearbySearchRequest nearbySearchRequest, GooglePlacesService googlePlacesService) {
        this.nearbySearchRequest = nearbySearchRequest;
        this.googlePlacesService = googlePlacesService;
    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return googlePlacesService.nearBySearch(nearbySearchRequest);
    }
}
