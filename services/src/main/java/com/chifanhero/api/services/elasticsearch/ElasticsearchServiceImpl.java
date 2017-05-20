package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shiyan on 5/19/17.
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final TransportClient transportClient;

    @Autowired
    public ElasticsearchServiceImpl(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    @Override
    public SearchResponse nearBySearch(NearbySearchRequest nearbySearchRequest) {
        return null;
    }

    @Override
    public SearchResponse textSearch(TextSearchRequest textSearchRequest) {
        return null;
    }
}
