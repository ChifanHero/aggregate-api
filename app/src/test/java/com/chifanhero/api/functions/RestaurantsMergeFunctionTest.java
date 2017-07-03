package com.chifanhero.api.functions;

import com.chifanhero.api.helpers.RestaurantDeduper;
import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.ErrorLevel;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by shiyan on 7/2/17.
 */
public class RestaurantsMergeFunctionTest {

    @Test
    public void test() {
        RestaurantDeduper mockDeduper = EasyMock.mock(RestaurantDeduper.class);
        List<RestaurantSearchResponse> input = Arrays.asList(createResponse(null), createResponse(createErrors()));
        List<Restaurant> restaurants = new ArrayList<>();
        input.forEach(searchResponse -> restaurants.addAll(searchResponse.getResults()));
        EasyMock.expect(mockDeduper.dedupe(restaurants)).andReturn(Collections.singletonList(new Restaurant()));
        EasyMock.replay(mockDeduper);
        RestaurantsMergeFunction mergeFunction = new RestaurantsMergeFunction(mockDeduper);
        RestaurantSearchResponse response = mergeFunction.apply(input);
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getErrors().size());
        Assert.assertEquals(1, response.getResults().size());
        EasyMock.verify(mockDeduper);
    }

    private RestaurantSearchResponse createResponse(List<Error> errors) {
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        response.setErrors(errors);
        response.setResults(Collections.singletonList(new Restaurant()));
        return response;
    }

    private List<Error> createErrors() {
        return Collections.singletonList(new Error("", ErrorLevel.ERROR));
    }

}
