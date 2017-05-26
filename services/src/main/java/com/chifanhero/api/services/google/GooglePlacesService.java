package com.chifanhero.api.services.google;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.models.response.SearchResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 5/1/17.
 */
public interface GooglePlacesService {

    SearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest);

    SearchResponse textSearch(TextSearchRequest textSearchRequest);

    Result get(String placeId);

    Map<String, Result> batchGet(List<String> placeIds);

}
