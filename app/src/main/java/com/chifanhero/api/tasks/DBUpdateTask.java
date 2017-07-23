package com.chifanhero.api.tasks;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 6/25/17.
 */
public class DBUpdateTask implements Runnable {

    private final ChifanheroRestaurantService chifanheroRestaurantService;
    private Future<RestaurantSearchResponse> responseFuture;

    public DBUpdateTask(ChifanheroRestaurantService chifanheroRestaurantService, Future<RestaurantSearchResponse> searchFuture) {
        this.chifanheroRestaurantService = chifanheroRestaurantService;
        this.responseFuture = searchFuture;
    }

    @Override
    public void run() {
        if (responseFuture.isDone()) {
            try {
                RestaurantSearchResponse restaurantSearchResponse = responseFuture.get();
                Optional.ofNullable(restaurantSearchResponse.getResults()).ifPresent(restaurants -> {
                    List<Restaurant> toSave = restaurants.stream().filter(restaurant -> !Boolean.TRUE.equals(restaurant.getShowOnly())).collect(Collectors.toList());
                    chifanheroRestaurantService.bulkUpsert(toSave);
                    chifanheroRestaurantService.markRecommendations(toSave);
                });
            } catch (InterruptedException | ExecutionException e) {
                // ignore
            }
        }
    }
}
