package com.chifanhero.api.controllers;

import com.chifanhero.api.cache.CacheKeyRetriever;
import com.chifanhero.api.functions.*;
import com.chifanhero.api.helpers.RestaurantDeduper;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.request.TrackingRequest;
import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.TrackingResponse;
import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.chifanhero.api.tasks.*;
import com.google.common.cache.Cache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class RestaurantsController {

    private final ListeningExecutorService executorService;
    private final ChifanheroRestaurantService chifanheroRestaurantService;
    private final ElasticsearchService elasticsearchService;
    private final GooglePlacesService googlePlacesService;
    private final Cache<String, RestaurantSearchResponse> cache;

    @Autowired
    public RestaurantsController(
            @Qualifier("listenableExecutorService") ListeningExecutorService service,
            ChifanheroRestaurantService chifanheroRestaurantService,
            ElasticsearchService elasticsearchService,
            GooglePlacesService googlePlacesService,
            Cache<String, RestaurantSearchResponse> cache) {
        this.executorService = service;
        this.chifanheroRestaurantService = chifanheroRestaurantService;
        this.elasticsearchService = elasticsearchService;
        this.googlePlacesService = googlePlacesService;
        this.cache = cache;
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
//        if (nearbySearchRequest.getOpenNow() != Boolean.TRUE) {
//            searchResponse = cache.getIfPresent(CacheKeyRetriever.from(nearbySearchRequest));
//            if (searchResponse != null) {
//                return searchResponse;
//            }
//        }
        ElasticNearbySearchTask elasticNearbySearchTask = new ElasticNearbySearchTask(nearbySearchRequest, elasticsearchService);
        GoogleNearbySearchTask googleNearbySearchTask = new GoogleNearbySearchTask(nearbySearchRequest, googlePlacesService);
        ListenableFuture<RestaurantSearchResponse> elasticNearbySearchFuture = executorService.submit(elasticNearbySearchTask);
        ListenableFuture<RestaurantSearchResponse> googleNearbySearchFuture = executorService.submit(googleNearbySearchTask);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(chifanheroRestaurantService, googleNearbySearchFuture);
        googleNearbySearchFuture.addListener(dbUpdateTask, executorService);
        ListenableFuture<List<RestaurantSearchResponse>> listListenableFuture = Futures.allAsList(elasticNearbySearchFuture, googleNearbySearchFuture);
        ListenableFuture<RestaurantSearchResponse> dedupeFuture = Futures.transform(listListenableFuture, new RestaurantsMergeFunction(new RestaurantDeduper()));
        ListenableFuture<RestaurantSearchResponse> fulfillRestaurantFuture = Futures.transform(dedupeFuture, new FillRestaurantsFunction(googlePlacesService));
        ListenableFuture<RestaurantSearchResponse> calculatedFuture = Futures.transform(fulfillRestaurantFuture, new CalculateDistanceFunction(nearbySearchRequest.getLocation()));
        ListenableFuture<RestaurantSearchResponse> filteredFuture = Futures.transform(calculatedFuture, new FilterFunction(nearbySearchRequest));
        ListenableFuture<RestaurantSearchResponse> sortedFuture = Futures.transform(filteredFuture, new SortFunction(SortOrder.valueOf(nearbySearchRequest.getSortOrder().toUpperCase())));
        ListenableFuture<RestaurantSearchResponse> transformedFuture = Futures.transform(sortedFuture, new ModelNormalizeFunction());
        ListenableFuture<RestaurantSearchResponse> truncatedFuture = Futures.transform(transformedFuture, new ResponseTruncateFunction());
        ListenableFuture<RestaurantSearchResponse> detailedFuture = Futures.transform(truncatedFuture, new RetrieveDetailFunction(elasticsearchService));
//        detailedFuture.addListener(new CacheUpdateTask(cache, nearbySearchRequest, truncatedFuture), executorService);
        try {
            return detailedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/text")
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest, HttpServletResponse response) {
        ElasticTextSearchTask elasticTextSearchTask = new ElasticTextSearchTask(textSearchRequest, elasticsearchService);
        GoogleTextSearchTask googleTextSearchTask = new GoogleTextSearchTask(textSearchRequest, googlePlacesService);
        ListenableFuture<RestaurantSearchResponse> elasticTextSearchFuture = executorService.submit(elasticTextSearchTask);
        ListenableFuture<RestaurantSearchResponse> googleTextSearchFuture = executorService.submit(googleTextSearchTask);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(chifanheroRestaurantService, googleTextSearchFuture);
        googleTextSearchFuture.addListener(dbUpdateTask, executorService);
        ListenableFuture<List<RestaurantSearchResponse>> listListenableFuture = Futures.allAsList(elasticTextSearchFuture, googleTextSearchFuture);
        ListenableFuture<RestaurantSearchResponse> dedupeFuture = Futures.transform(listListenableFuture, new RestaurantsMergeFunction(new RestaurantDeduper()));
        ListenableFuture<RestaurantSearchResponse> fulfillRestaurantFuture = Futures.transform(dedupeFuture, new FillRestaurantsFunction(googlePlacesService));
        ListenableFuture<RestaurantSearchResponse> calculatedFuture = Futures.transform(fulfillRestaurantFuture, new CalculateDistanceFunction(textSearchRequest.getLocation()));
        ListenableFuture<RestaurantSearchResponse> filteredFuture = Futures.transform(calculatedFuture, new FilterFunction(textSearchRequest));
        ListenableFuture<RestaurantSearchResponse> sortedFuture = Futures.transform(filteredFuture, new SortFunction(SortOrder.valueOf(textSearchRequest.getSortOrder().toUpperCase())));
        ListenableFuture<RestaurantSearchResponse> transformedFuture = Futures.transform(sortedFuture, new ModelNormalizeFunction());
        ListenableFuture<RestaurantSearchResponse> detailedFuture = Futures.transform(transformedFuture, new RetrieveDetailFunction(elasticsearchService));
        try {
            return detailedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/track", method = RequestMethod.POST)
    public TrackingResponse trackView(@RequestBody TrackingRequest trackingRequest, HttpServletResponse response) {
        TrackingResponse trackingResponse = new TrackingResponse();
        if (trackingRequest == null || trackingRequest.getRestaurantId() == null || trackingRequest.getUserIdentifier() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            trackingResponse.setSuccess(false);
            return trackingResponse;
        }
        chifanheroRestaurantService.trackViewCount(trackingRequest.getRestaurantId(), trackingRequest.getUserIdentifier());
        chifanheroRestaurantService.tryPublishRestaurant(trackingRequest.getRestaurantId());
        trackingResponse.setSuccess(true);
        return trackingResponse;
    }
}
