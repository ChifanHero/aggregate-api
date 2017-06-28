package com.chifanhero.api.controllers;

import com.chifanhero.api.functions.FulfillRestaurantsFunction;
import com.chifanhero.api.functions.RestaurantsDedupeFunction;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.tasks.CacheUpdateTask;
import com.chifanhero.api.tasks.DBUpdateTask;
import com.chifanhero.api.tasks.ElasticNearbySearchTask;
import com.chifanhero.api.tasks.GoogleNearbySearchTask;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class RestaurantsController {

    private final ListeningExecutorService service;

    @Autowired
    public RestaurantsController(ListeningExecutorService service) {
        this.service = service;
    }

    // http://localhost:8080/nearBy?radius=500&location.lat=123.4&location.lon=234.5&rating=4.5
    @RequestMapping("/nearBy")
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest, HttpServletResponse response) {
        RestaurantSearchResponse searchResponse = new RestaurantSearchResponse();
        List<Error> errors = nearbySearchRequest.validate();
        if (!errors.isEmpty()) {
            searchResponse.setErrors(errors);
            return searchResponse;
        }
        ElasticNearbySearchTask elasticNearbySearchTask = new ElasticNearbySearchTask(nearbySearchRequest);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest);
        ListenableFuture<RestaurantSearchResponse> elasticNearbySearchFuture = service.submit(elasticNearbySearchTask);
        ListenableFuture<RestaurantSearchResponse> googleNearbySearchFuture = service.submit(googleNearbySearchTask);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(googleNearbySearchFuture);
        googleNearbySearchFuture.addListener(dbUpdateTask, service);
        ListenableFuture<List<RestaurantSearchResponse>> listListenableFuture = Futures.successfulAsList(elasticNearbySearchFuture, googleNearbySearchFuture);
        ListenableFuture<RestaurantSearchResponse> dedupeFuture = Futures.transform(listListenableFuture, new RestaurantsDedupeFunction());
        ListenableFuture<RestaurantSearchResponse> fulfillRestaurantFuture = Futures.transform(dedupeFuture, new FulfillRestaurantsFunction());
        fulfillRestaurantFuture.addListener(new CacheUpdateTask(nearbySearchRequest, fulfillRestaurantFuture), service);
        try {
            return fulfillRestaurantFuture.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
