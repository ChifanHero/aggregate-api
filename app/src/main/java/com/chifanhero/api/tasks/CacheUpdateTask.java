package com.chifanhero.api.tasks;

import com.chifanhero.api.cache.CacheKeyRetriever;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.cache.Cache;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by shiyan on 6/25/17.
 */
public class CacheUpdateTask implements Runnable {

    private final Cache<String, RestaurantSearchResponse> cache;
    private SearchRequest searchRequest;
    private Future<RestaurantSearchResponse> responseFuture;

    public CacheUpdateTask(Cache<String, RestaurantSearchResponse> cache, SearchRequest searchRequest, Future<RestaurantSearchResponse> future) {
        this.cache = cache;
        this.searchRequest = searchRequest;
        this.responseFuture = future;
    }

    @Override
    public void run() {
        Optional.ofNullable(getKey(searchRequest)).ifPresent(key -> {
            try {
                cache.put(key, responseFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                // ignore error
            }
        });

    }

    private String getKey(SearchRequest searchRequest) {
        if (searchRequest instanceof NearbySearchRequest) {
            return CacheKeyRetriever.from((NearbySearchRequest) searchRequest);
        } else if (searchRequest instanceof TextSearchRequest) {
            return CacheKeyRetriever.from((TextSearchRequest) searchRequest);
        } else {
            return null;
        }
    }
}
