package com.chifanhero.api.services.elasticsearch.client;

import com.chifanhero.api.configs.ElasticConfigs;
import com.chifanhero.api.configs.type.Host;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

/**
 * This is a simple elastic search client backed by AsyncHttpClient.
 * We need this because Elasticsearch Transport client has issues.
 * Created by shiyan on 6/20/17.
 */
@Component
public class ElasticsearchRestClient {

    private final AsyncHttpClient httpClient;
    private final String baseUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ElasticsearchRestClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
        Host host = ElasticConfigs.HOSTS.get(0);
        baseUrl = "http://" + host.getHost() + ":" + host.getPort() + "/";
    }

    public ElasticsearchRestClient(AsyncHttpClient httpClient, String host, String port) {
        this.httpClient = httpClient;
        baseUrl = "http://" + host + ":" + port + "/";
    }

    //http://ec2-54-69-87-122.us-west-2.compute.amazonaws.com:9200/chifanhero
    public int createIndex(String index, String settings) {
        String endPoint = baseUrl + index;
        try {
            Response response = httpClient.preparePut(endPoint).setHeader(HttpHeaders.CONTENT_TYPE, "application/json").setBody(settings.getBytes()).execute().get();
            return response.getStatusCode();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // http://ec2-54-69-87-122.us-west-2.compute.amazonaws.com:9200/chifanhero/_mapping/Restaurant
    public int createMapping(String index, String type, String mapping) {
        String endPoint = baseUrl + index + "/_mapping/" + type;
        try {
            Response response = httpClient.preparePut(endPoint).setHeader(HttpHeaders.CONTENT_TYPE, "application/json").setBody(mapping.getBytes()).execute().get();
            return response.getStatusCode();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //http://ec2-54-69-87-122.us-west-2.compute.amazonaws.com:9200/chifanhero/Restaurant/_search
    public JSONObject search(String index, String type, String query) {
        String endPoint = baseUrl + index + "/" + type + "/_search";
        try {
            return httpClient.preparePost(endPoint).setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").setBody(query).execute(new AsyncCompletionHandler<JSONObject>() {
                @Override
                public JSONObject onCompleted(Response response) throws Exception {
                    return new JSONObject(response.getResponseBody(Charset.forName("UTF-8")));
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public int indexDocument(String index, String type, String id, String document) {
        String endPoint = baseUrl + index + "/" + type + "/" + id;
        try {
            Response response = httpClient.preparePut(endPoint).setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").setCharset(Charset.forName("utf-8")).setBody(document).execute().get();
            return response.getStatusCode();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
