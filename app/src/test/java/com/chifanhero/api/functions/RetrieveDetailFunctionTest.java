package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 10/8/17.
 */
public class RetrieveDetailFunctionTest {

    @Test
    public void test() {
        String elasticId1 = "elasticId1";
        String elasticId2 = "elasticId2";
        String elasticId3 = "elasticId3";
        String googleId = "googleId";
        ElasticsearchService mockElasticService = EasyMock.mock(ElasticsearchService.class);
        Map<String, Restaurant> mockResponse = new HashMap<>();
        mockResponse.put(elasticId1, createRestaurant(elasticId1, "餐厅2", Source.CHIFANHERO));
        mockResponse.put(elasticId2, createRestaurant(elasticId2, "餐厅3", Source.CHIFANHERO));
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        restaurantSearchResponse.setResults(
                Arrays.asList(
                        createRestaurant(googleId, "restaurant1", Source.GOOGLE),
                        createRestaurant(elasticId1, "restaurant2", Source.GOOGLE),
                        createRestaurant(elasticId2, "restaurant3", Source.GOOGLE),
                        createRestaurant(elasticId3, "restaurant4", Source.CHIFANHERO)
                )
        );
        EasyMock.expect(mockElasticService.batchGetRestaurant(EasyMock.anyObject()))
                .andReturn(mockResponse);
        EasyMock.replay(mockElasticService);
        RetrieveDetailFunction function = new RetrieveDetailFunction(mockElasticService);
        RestaurantSearchResponse response = function.apply(restaurantSearchResponse);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getResults());
        Assert.assertTrue(response.getResults().size() == 4);
        Map<String, Restaurant> results = response.getResults().stream().collect(Collectors.toMap(Restaurant::getId, restaurant -> restaurant));
        Assert.assertNotNull(results.get(googleId));
        Assert.assertNotNull(results.get(elasticId1));
        Assert.assertNotNull(results.get(elasticId2));
        Assert.assertNotNull(results.get(elasticId3));
        Assert.assertEquals("restaurant1", results.get(googleId).getName());
        Assert.assertEquals("餐厅2", results.get(elasticId1).getName());
        Assert.assertEquals("餐厅3", results.get(elasticId2).getName());
        Assert.assertEquals("restaurant4", results.get(elasticId3).getName());
        EasyMock.verify(mockElasticService);
    }

    private Restaurant createRestaurant(String id, String name, Source source) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setPlaceId(id);
        restaurant.setSource(source);
        restaurant.setName(name);
        return restaurant;
    }
}
