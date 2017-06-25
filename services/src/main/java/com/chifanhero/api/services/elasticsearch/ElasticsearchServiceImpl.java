package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.elasticsearch.client.ElasticsearchRestClient;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import com.chifanhero.api.services.elasticsearch.query.helpers.QueryHelper;
import com.chifanhero.api.services.elasticsearch.query.helpers.RequestHelper;
import com.chifanhero.api.services.elasticsearch.query.helpers.SortHelper;
import com.chifanhero.api.services.elasticsearch.response.SearchResponseConverter;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Elasticsearch API
 * Created by shiyan on 5/19/17.
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    public final static String INDEX = "chifanhero";
    public final static String TYPE = "Restaurant";

    private final ElasticsearchRestClient restClient;

    @Autowired
    public ElasticsearchServiceImpl(ElasticsearchRestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
        QueryBuilder query = QueryHelper.buildNearbySearchQuery(nearbySearchRequest);
        SortBuilder sort = null;
        if (SortOrder.NEAREST.name().equals(nearbySearchRequest.getSortOrder())) {
            sort = SortHelper.buildGeoDistanceSort(FieldNames.COORDINATES, nearbySearchRequest.getLocation().getLat(), nearbySearchRequest.getLocation().getLon(), DistanceUnit.MILES);
        } else if (SortOrder.HOTTEST.name().equals(nearbySearchRequest.getSortOrder())) {
            sort = SortHelper.buildSort(FieldNames.RATING, org.elasticsearch.search.sort.SortOrder.DESC);
        }
        String request = RequestHelper.buildSearchRequest(query, sort);
        JSONObject searchResponse = restClient.search(INDEX, TYPE, request);
        return SearchResponseConverter.toLocalSearchResponse(searchResponse);
    }

    @Override
    public RestaurantSearchResponse textSearch(TextSearchRequest textSearchRequest) {
        QueryBuilder query = QueryHelper.buildTextSearchQuery(textSearchRequest);
        SortBuilder sort = null;
        if (SortOrder.NEAREST.name().equals(textSearchRequest.getSortOrder())) {
            sort = SortHelper.buildGeoDistanceSort(FieldNames.COORDINATES, textSearchRequest.getLocation().getLat(), textSearchRequest.getLocation().getLon(), DistanceUnit.MILES);
        } else if (SortOrder.HOTTEST.name().equals(textSearchRequest.getSortOrder())) {
            sort = SortHelper.buildSort(FieldNames.RATING, org.elasticsearch.search.sort.SortOrder.DESC);
        }
        String request = RequestHelper.buildSearchRequest(query, sort);
        JSONObject searchResponse = restClient.search(INDEX, TYPE, request);
        return SearchResponseConverter.toLocalSearchResponse(searchResponse);
    }
}
