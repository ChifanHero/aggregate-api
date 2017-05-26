package com.chifanhero.api.services.google;

import com.chifanhero.api.async.FutureResolver;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.models.response.SearchResponse;
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

    @Autowired
    public GooglePlacesServiceImpl(FutureResolver futureResolver) {
        this.futureResolver = futureResolver;
    }

    @Override
    public SearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
        return null;
    }

    @Override
    public SearchResponse textSearch(TextSearchRequest textSearchRequest) {
        return null;
    }

    @Override
    public Result get(String placeId) {
        return null;
    }

    @Override
    public Map<String, Result> batchGet(List<String> placeIds) {
        return null;
    }
}
