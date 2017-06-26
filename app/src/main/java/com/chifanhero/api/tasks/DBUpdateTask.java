package com.chifanhero.api.tasks;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by shiyan on 6/25/17.
 */
public class DBUpdateTask implements Runnable {

    public DBUpdateTask(ListenableFuture<RestaurantSearchResponse> searchFuture) {

    }

    @Override
    public void run() {

    }
}
