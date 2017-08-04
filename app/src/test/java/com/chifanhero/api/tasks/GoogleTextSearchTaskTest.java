package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by shiyan on 7/2/17.
 */
public class GoogleTextSearchTaskTest {

    @Test
    public void test() throws Exception {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("chinese");
        GooglePlacesService mockService = EasyMock.mock(GooglePlacesService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        Restaurant restaurant = new Restaurant();
        restaurantSearchResponse.setResults(Collections.singletonList(restaurant));
        EasyMock.expect(mockService.textSearch(textSearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockService);
        GoogleTextSearchTask googleTextSearchTask = new GoogleTextSearchTask(textSearchRequest, mockService);
        RestaurantSearchResponse response = googleTextSearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
        Assert.assertTrue(response.getResults().get(0).isOnHold());
    }
}
