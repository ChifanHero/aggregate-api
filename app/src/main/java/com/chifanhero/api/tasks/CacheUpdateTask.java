package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.SearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by shiyan on 6/25/17.
 */
public class CacheUpdateTask implements Runnable {

    public CacheUpdateTask(SearchRequest searchRequest, ListenableFuture<RestaurantSearchResponse> future) {

    }

    @Override
    public void run() {

    }
}
