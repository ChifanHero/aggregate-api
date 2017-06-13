package com.chifanhero.api.services.google;

import com.chifanhero.api.async.FutureResolver;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.client.GooglePlacesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 5/1/17.
 */
@Service
public class GooglePlacesServiceImpl implements GooglePlacesService {

    private final FutureResolver futureResolver;
    private final GooglePlacesClient client;

    @Autowired
    public GooglePlacesServiceImpl(FutureResolver futureResolver, GooglePlacesClient client) {
        this.futureResolver = futureResolver;
        this.client = client;
    }

    @Override
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
//        client.nearBySearch(null).
        return null;
    }

    @Override
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest) {
        return null;
    }

    @Override
    public Restaurant get(String placeId) {
        return null;
    }

    @Override
    public Map<String, Restaurant> batchGet(List<String> placeIds) {
        return null;
    }
}
