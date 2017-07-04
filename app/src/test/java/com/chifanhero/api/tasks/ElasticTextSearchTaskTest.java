package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 7/2/17.
 */
public class ElasticTextSearchTaskTest {

    @Test
    public void test() throws Exception {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("chinese");
        ElasticsearchService mockElasticsearchService = EasyMock.mock(ElasticsearchService.class);
        RestaurantSearchResponse restaurantSearchResponse = new RestaurantSearchResponse();
        EasyMock.expect(mockElasticsearchService.textSearch(textSearchRequest)).andReturn(restaurantSearchResponse);
        EasyMock.replay(mockElasticsearchService);
        ElasticTextSearchTask elasticTextSearchTask = new ElasticTextSearchTask(textSearchRequest, mockElasticsearchService);
        RestaurantSearchResponse response = elasticTextSearchTask.call();
        Assert.assertTrue(response == restaurantSearchResponse);
        EasyMock.verify(mockElasticsearchService);
    }
}
