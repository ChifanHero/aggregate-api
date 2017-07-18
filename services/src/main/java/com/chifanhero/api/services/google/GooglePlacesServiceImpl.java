package com.chifanhero.api.services.google;

import com.chifanhero.api.configs.GoogleConfigs;
import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.google.PlaceDetailResponse;
import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.client.GooglePlacesClient;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.PlaceDetailRequestParams;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import com.chifanhero.api.services.google.client.request.converters.NearBySearchRequestConverter;
import com.chifanhero.api.services.google.client.response.converters.ResponseConverter;
import com.chifanhero.api.services.google.client.response.converters.RestaurantConverter;
import com.chifanhero.api.services.google.client.request.converters.TextSearchRequestConverter;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 5/1/17.
 */
@Service
public class GooglePlacesServiceImpl implements GooglePlacesService {

    private final GooglePlacesClient client;
    private final ListeningExecutorService executorService;

    @Autowired
    public GooglePlacesServiceImpl(GooglePlacesClient client, @Qualifier("listenableExecutorService") ListeningExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Override
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
        NearBySearchRequestParams nearBySearchRequestParams = NearBySearchRequestConverter.toParams(nearbySearchRequest);
        nearBySearchRequestParams.setKey(GoogleConfigs.API_KEY);
        try {
            PlacesSearchResponse placesSearchResponse = client.nearBySearch(nearBySearchRequestParams).get();
            return ResponseConverter.toRestaurantSearchResponse(placesSearchResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest, List<Location> pointsGroup) {
        Preconditions.checkNotNull(nearbySearchRequest);
        Preconditions.checkNotNull(pointsGroup);
        List<NearbySearchRequest> requests = pointsGroup.stream().map(points -> {
            NearbySearchRequest clonedRequest = nearbySearchRequest.clone();
            clonedRequest.setLocation(points);
            return clonedRequest;
        }).collect(Collectors.toList());
        requests.add(nearbySearchRequest);
        List<ListenableFuture<PlacesSearchResponse>> futures = requests.stream().map(request -> {
            NearBySearchRequestParams nearBySearchRequestParams = NearBySearchRequestConverter.toParams(request);
            nearBySearchRequestParams.setKey(GoogleConfigs.API_KEY);
            return executorService.submit(() -> client.nearBySearch(nearBySearchRequestParams).get());
        }).collect(Collectors.toList());
        ListenableFuture<List<PlacesSearchResponse>> resultFuture = Futures.allAsList(futures);
        try {
            List<PlacesSearchResponse> placesSearchResponses = resultFuture.get();
            List<RestaurantSearchResponse> converted = placesSearchResponses.stream().map(ResponseConverter::toRestaurantSearchResponse).collect(Collectors.toList());
            RestaurantSearchResponse searchResponse = new RestaurantSearchResponse();
            converted.forEach(restaurantSearchResponse -> {
                searchResponse.setResults(restaurantSearchResponse.getResults());
                searchResponse.setErrors(restaurantSearchResponse.getErrors());
            });
            return searchResponse;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest) {
        TextSearchRequestParams textSearchRequestParams = TextSearchRequestConverter.toParams(textSearchRequest);
        textSearchRequestParams.setKey(GoogleConfigs.API_KEY);
        try {
            PlacesSearchResponse placesSearchResponse = client.textSearch(textSearchRequestParams).get();
            return ResponseConverter.toRestaurantSearchResponse(placesSearchResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest, List<Location> pointsGroup) {
        return null;
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
        List<ListenableFuture<PlaceDetailResponse>> collect = placeIds.stream().map(placeId -> {
            PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
            placeDetailRequestParams.setKey(GoogleConfigs.API_KEY);
            placeDetailRequestParams.setPlaceId(placeId);
            return executorService.submit(() -> client.getPlaceDetail(placeDetailRequestParams).get());
        }).collect(Collectors.toList());
        ListenableFuture<List<PlaceDetailResponse>> resultFuture = Futures.allAsList(collect);
        try {
            List<PlaceDetailResponse> placeDetailResponses = resultFuture.get();
            return placeDetailResponses.stream()
                    .map(PlaceDetailResponse::getResult)
                    .collect(Collectors.toMap(Place::getPlaceId, RestaurantConverter::toRestaurant));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
