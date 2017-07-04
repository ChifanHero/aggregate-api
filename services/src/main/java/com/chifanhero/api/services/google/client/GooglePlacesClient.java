package com.chifanhero.api.services.google.client;

import com.chifanhero.api.common.GetRequest;
import com.chifanhero.api.models.google.PlaceDetailResponse;
import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.PlaceDetailRequestParams;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Google places API
 * Created by shiyan on 5/2/17.
 */
@Component
public class GooglePlacesClient {

    private final AsyncHttpClient asyncHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GooglePlacesClient(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    /**
     * A Nearby Search lets you search for places within a specified area.
     *
     * @param nearBySearchRequestParams request parameters
     * @return asynchronous future response
     */
    public Future<PlacesSearchResponse> nearBySearch(NearBySearchRequestParams nearBySearchRequestParams) {
        Preconditions.checkNotNull(nearBySearchRequestParams);
        String url = new GetRequest("https://maps.googleapis.com/maps/api/place/nearbysearch/json", nearBySearchRequestParams.getParams()).getUrl();
        return asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<PlacesSearchResponse>() {
            @Override
            public PlacesSearchResponse onCompleted(Response response) throws Exception {
                return objectMapper.readValue(response.getResponseBodyAsBytes(), PlacesSearchResponse.class);
            }
        });
    }

    /**
     * The Google Places API Text Search Service is a web service that returns information about a set of places based on a string — for example "pizza in New York" or "shoe stores near Ottawa" or "123 Main Street".
     * The service responds with a list of places matching the text string and any location bias that has been set.
     * @param textSearchRequestParams request parameters
     * @return asynchronous future response
     */
    public Future<PlacesSearchResponse> textSearch(TextSearchRequestParams textSearchRequestParams) {
        Preconditions.checkNotNull(textSearchRequestParams);
        String url = new GetRequest("https://maps.googleapis.com/maps/api/place/textsearch/json", textSearchRequestParams.getParams()).getUrl();
        return asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<PlacesSearchResponse>() {
            @Override
            public PlacesSearchResponse onCompleted(Response response) throws Exception {
                return objectMapper.readValue(response.getResponseBodyAsBytes(), PlacesSearchResponse.class);
            }
        });
    }

    /**
     * A Place Details request returns more comprehensive information about the indicated place such as its complete address, phone number, user rating and reviews.
     * @param placeDetailRequestParams request parameters
     * @return asynchronous future response
     */
    public Future<PlaceDetailResponse> getPlaceDetail(PlaceDetailRequestParams placeDetailRequestParams) {
        Preconditions.checkNotNull(placeDetailRequestParams);
        String url = new GetRequest("https://maps.googleapis.com/maps/api/place/details/json", placeDetailRequestParams.getParams()).getUrl();
        return asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<PlaceDetailResponse>() {
            @Override
            public PlaceDetailResponse onCompleted(Response response) throws Exception {
                return objectMapper.readValue(response.getResponseBodyAsBytes(), PlaceDetailResponse.class);
            }
        });
    }

}
