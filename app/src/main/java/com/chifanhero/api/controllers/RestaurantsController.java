package com.chifanhero.api.controllers;

import com.chifanhero.api.functions.*;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.chifanhero.api.tasks.CacheUpdateTask;
import com.chifanhero.api.tasks.DBUpdateTask;
import com.chifanhero.api.tasks.ElasticNearbySearchTask;
import com.chifanhero.api.tasks.GoogleNearbySearchTask;
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

    private final ListeningExecutorService executorService;
    private final ChifanheroRestaurantService chifanheroRestaurantService;
    private final ElasticsearchService elasticsearchService;
    private final GooglePlacesService googlePlacesService;

    @Autowired
    public RestaurantsController(
            ListeningExecutorService service,
            ChifanheroRestaurantService chifanheroRestaurantService,
            ElasticsearchService elasticsearchService,
            GooglePlacesService googlePlacesService) {
        this.executorService = service;
        this.chifanheroRestaurantService = chifanheroRestaurantService;
        this.elasticsearchService = elasticsearchService;
        this.googlePlacesService = googlePlacesService;
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
        ElasticNearbySearchTask elasticNearbySearchTask = new ElasticNearbySearchTask(nearbySearchRequest, elasticsearchService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, googlePlacesService);
        ListenableFuture<RestaurantSearchResponse> elasticNearbySearchFuture = executorService.submit(elasticNearbySearchTask);
        ListenableFuture<RestaurantSearchResponse> googleNearbySearchFuture = executorService.submit(googleNearbySearchTask);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(googleNearbySearchFuture);
        googleNearbySearchFuture.addListener(dbUpdateTask, executorService);
        ListenableFuture<List<RestaurantSearchResponse>> listListenableFuture = Futures.successfulAsList(elasticNearbySearchFuture, googleNearbySearchFuture);
        ListenableFuture<RestaurantSearchResponse> dedupeFuture = Futures.transform(listListenableFuture, new RestaurantsMergeFunction());
        ListenableFuture<RestaurantSearchResponse> fulfillRestaurantFuture = Futures.transform(dedupeFuture, new FulfillRestaurantsFunction(googlePlacesService));
        fulfillRestaurantFuture.addListener(new CacheUpdateTask(nearbySearchRequest, fulfillRestaurantFuture), executorService);
        ListenableFuture<RestaurantSearchResponse> filteredFuture = Futures.transform(fulfillRestaurantFuture, new FilterFunction(nearbySearchRequest));
        ListenableFuture<RestaurantSearchResponse> sortedFuture = Futures.transform(filteredFuture, new SortFunction(SortOrder.valueOf(nearbySearchRequest.getSortOrder())));
        ListenableFuture<RestaurantSearchResponse> result = Futures.transform(sortedFuture, new CalculateDistanceFunction(nearbySearchRequest.getLocation()));
        try {
            return result.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
