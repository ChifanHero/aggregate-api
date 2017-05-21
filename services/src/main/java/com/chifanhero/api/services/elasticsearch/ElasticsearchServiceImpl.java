package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.SearchResponse;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import com.chifanhero.api.services.elasticsearch.query.helpers.QueryHelper;
import com.chifanhero.api.services.elasticsearch.query.helpers.SortHelper;
import com.chifanhero.api.services.elasticsearch.response.SearchResponseConverter;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shiyan on 5/19/17.
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    public final static String INDEX = "chifanhero";
    public final static String TYPE = "Restaurant";

    private final TransportClient transportClient;

    @Autowired
    public ElasticsearchServiceImpl(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    @Override
    public SearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DEFAULT)
                .setQuery(QueryHelper.buildNearbySearchQuery(nearbySearchRequest));
        if (SortOrder.NEAREST.name().equals(nearbySearchRequest.getSortOrder())) {
            searchRequestBuilder.addSort(SortHelper.buildGeoDistanceSort(FieldNames.COORDINATES, nearbySearchRequest.getLocation().getLat(), nearbySearchRequest.getLocation().getLon(), DistanceUnit.MILES));
        } else if (SortOrder.HOTTEST.name().equals(nearbySearchRequest.getSortOrder())) {
            searchRequestBuilder.addSort(FieldNames.RATING, org.elasticsearch.search.sort.SortOrder.DESC);
        }
        return SearchResponseConverter.toLocalSearchResponse(searchRequestBuilder.get());
    }

    @Override
    public SearchResponse textSearch(TextSearchRequest textSearchRequest) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DEFAULT)
                .setQuery(QueryHelper.buildTextSearchQuery(textSearchRequest));
        if (SortOrder.NEAREST.name().equals(textSearchRequest.getSortOrder())) {
            searchRequestBuilder.addSort(SortHelper.buildGeoDistanceSort(FieldNames.COORDINATES, textSearchRequest.getLocation().getLat(), textSearchRequest.getLocation().getLon(), DistanceUnit.MILES));
        } else if (SortOrder.HOTTEST.name().equals(textSearchRequest.getSortOrder())) {
            searchRequestBuilder.addSort(FieldNames.RATING, org.elasticsearch.search.sort.SortOrder.DESC);
        }
        return SearchResponseConverter.toLocalSearchResponse(searchRequestBuilder.get());
    }
}
