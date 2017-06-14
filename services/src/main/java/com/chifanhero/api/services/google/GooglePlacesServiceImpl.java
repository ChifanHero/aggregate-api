package com.chifanhero.api.services.google;

import com.chifanhero.api.async.FutureResolver;
import com.chifanhero.api.configs.GoogleConfigs;
import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.google.PlaceDetailResponse;
import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.client.GooglePlacesClient;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.PlaceDetailRequestParams;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import com.chifanhero.api.services.google.client.request.converters.NearBySearchRequestConverter;
import com.chifanhero.api.services.google.client.request.converters.ResponseConverter;
import com.chifanhero.api.services.google.client.request.converters.RestaurantConverter;
import com.chifanhero.api.services.google.client.request.converters.TextSearchRequestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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
        NearBySearchRequestParams nearBySearchRequestParams = NearBySearchRequestConverter.toParams(nearbySearchRequest);
        try {
            PlacesSearchResponse placesSearchResponse = client.nearBySearch(nearBySearchRequestParams).get();
            return ResponseConverter.toRestaurantSearchResponse(placesSearchResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest) {
        TextSearchRequestParams textSearchRequestParams = TextSearchRequestConverter.toParams(textSearchRequest);
        try {
            PlacesSearchResponse placesSearchResponse = client.textSearch(textSearchRequestParams).get();
            return ResponseConverter.toRestaurantSearchResponse(placesSearchResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Restaurant get(String placeId) {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setKey(GoogleConfigs.API_KEY);
        placeDetailRequestParams.setPlaceId(placeId);
        try {
            PlaceDetailResponse placeDetailResponse = client.getPlaceDetail(placeDetailRequestParams).get();
            return Optional.ofNullable(placeDetailResponse).map(PlaceDetailResponse::getResult).map(RestaurantConverter::toRestaurant).orElse(null);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Restaurant> batchGet(List<String> placeIds) {
        List<Future<PlaceDetailResponse>> collect = placeIds.stream().map(placeId -> {
            PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
            placeDetailRequestParams.setKey(GoogleConfigs.API_KEY);
            placeDetailRequestParams.setPlaceId(placeId);
            return client.getPlaceDetail(placeDetailRequestParams);
        }).collect(Collectors.toList());
        List<PlaceDetailResponse> list = futureResolver.resolve(collect).get();
        return list.stream()
                .map(PlaceDetailResponse::getResult)
                .collect(Collectors.toMap(Place::getPlaceId, RestaurantConverter::toRestaurant));
    }
}
