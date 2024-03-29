package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 7/2/17.
 */
public class ElasticNearbySearchTaskTest {

    @Test
    public void test() throws Exception {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setKeyword("chinese");
        ElasticsearchService mockElasticsearchService = EasyMock.mock(ElasticsearchService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        EasyMock.expect(mockElasticsearchService.nearBySearch(nearbySearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockElasticsearchService);
        ElasticNearbySearchTask elasticNearbySearchTask = new ElasticNearbySearchTask(nearbySearchRequest, mockElasticsearchService);
        RestaurantSearchResponse response = elasticNearbySearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
        EasyMock.verify(mockElasticsearchService);
    }
}
