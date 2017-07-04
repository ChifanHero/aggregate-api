package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;

import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class GoogleTextSearchTask implements Callable<RestaurantSearchResponse> {

    private TextSearchRequest textSearchRequest;
    private final GooglePlacesService googlePlacesService;

    public GoogleTextSearchTask(TextSearchRequest textSearchRequest, GooglePlacesService googlePlacesService) {
        this.textSearchRequest = textSearchRequest;
        this.googlePlacesService = googlePlacesService;
    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        return googlePlacesService.textSearch(textSearchRequest);
    }
}
