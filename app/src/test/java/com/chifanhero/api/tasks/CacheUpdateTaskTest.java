package com.chifanhero.api.tasks;

import com.chifanhero.api.cache.CacheKeyRetriever;
import com.chifanhero.api.factories.CacheFactory;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.cache.Cache;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * Created by shiyan on 7/2/17.
 */
public class CacheUpdateTaskTest {

    @Test
    public void testNearbySearch() {
        Cache<String, RestaurantSearchResponse> cache = new CacheFactory().buildCache();
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setKeyword("chinese");
        CompletableFuture<RestaurantSearchResponse> future = CompletableFuture.completedFuture(createResponse());
        CacheUpdateTask cacheUpdateTask = new CacheUpdateTask(cache, nearbySearchRequest, future);
        cacheUpdateTask.run();
        RestaurantSearchResponse cachedValue = cache.getIfPresent(CacheKeyRetriever.from(nearbySearchRequest));
        Assert.assertNotNull(cachedValue);
        Assert.assertNotNull(cachedValue.getResults());
    }

    @Test
    public void testTextSearch() {
        Cache<String, RestaurantSearchResponse> cache = new CacheFactory().buildCache();
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("chinese");
        CompletableFuture<RestaurantSearchResponse> future = CompletableFuture.completedFuture(createResponse());
        CacheUpdateTask cacheUpdateTask = new CacheUpdateTask(cache, textSearchRequest, future);
        cacheUpdateTask.run();
        RestaurantSearchResponse cachedValue = cache.getIfPresent(CacheKeyRetriever.from(textSearchRequest));
        Assert.assertNotNull(cachedValue);
        Assert.assertNotNull(cachedValue.getResults());
    }

    private RestaurantSearchResponse createResponse() {
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        restaurantSearchResponse.setResults(Collections.emptyList());
        return restaurantSearchResponse;
    }
}
