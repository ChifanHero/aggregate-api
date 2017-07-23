package com.chifanhero.api.tasks;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by shiyan on 7/2/17.
 */
public class DBUpdateTaskTest {

    @Test
    public void test() {
        ChifanheroRestaurantService mockService = EasyMock.mock(ChifanheroRestaurantService.class);
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        response.setResults(restaurants);
        CompletableFuture<RestaurantSearchResponse> future = CompletableFuture.completedFuture(response);
        mockService.bulkUpsert(restaurants);
        EasyMock.expectLastCall();
        mockService.markRecommendations(restaurants);
        EasyMock.expectLastCall();
        EasyMock.replay(mockService);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(mockService, future);
        dbUpdateTask.run();
        EasyMock.verify(mockService);
    }

    @Test
    public void testShowOnly() {
        ChifanheroRestaurantService mockService = EasyMock.mock(ChifanheroRestaurantService.class);
        Restaurant restaurant = new Restaurant();
        Restaurant showOnlyRestaurant = new Restaurant();
        showOnlyRestaurant.setShowOnly(true);
        List<Restaurant> restaurants = Arrays.asList(restaurant, showOnlyRestaurant);
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        response.setResults(restaurants);
        CompletableFuture<RestaurantSearchResponse> future = CompletableFuture.completedFuture(response);
        mockService.bulkUpsert(Collections.singletonList(restaurant));
        EasyMock.expectLastCall();
        mockService.markRecommendations(Collections.singletonList(restaurant));
        EasyMock.expectLastCall();
        EasyMock.replay(mockService);
        DBUpdateTask dbUpdateTask = new DBUpdateTask(mockService, future);
        dbUpdateTask.run();
        EasyMock.verify(mockService);
    }
}
