package com.chifanhero.api.services.elasticsearch.client;

import org.asynchttpclient.AsyncHttpClient;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This is a simple elastic search client backed by AsyncHttpClient.
 * We need this because Elasticsearch Transport client has issues.
 * Created by shiyan on 6/20/17.
 */
@Component
public class ElasticsearchClient {

    private final AsyncHttpClient httpClient;

    @Autowired
    public ElasticsearchClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    
    public int createIndex(String index, String settings) {
        return 0;
    }

    public int createMapping(String index, String type, String mapping) {
        return 0;
    }

    public SearchResponse search(String index, String type, String query) {
        return null;
    }

    public int bulkIndex(String index, String type, List<String> documents) {
        return 0;
    }
}
