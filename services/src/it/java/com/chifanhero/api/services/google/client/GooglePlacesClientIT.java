package com.chifanhero.api.services.google.client;

import com.chifanhero.api.configs.GoogleConfigs;
import com.chifanhero.api.models.google.PlaceDetailResponse;
import com.chifanhero.api.models.google.PlacesSearchResponse;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.PlaceDetailRequestParams;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.asyncHttpClient;

//TODO - this test relies on google API and hence is unstable. Should probably record the downstream http request for test
public class GooglePlacesClientIT {

    private final AsyncHttpClient asyncHttpClient = asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setRequestTimeout(2000));
    private final GooglePlacesClient client = new GooglePlacesClient(asyncHttpClient);

    @Test
    public void testNearbySearch() throws ExecutionException, InterruptedException {
        NearBySearchRequestParams nearBySearchRequestParams = createNearBySearchRequestParams();
        Future<PlacesSearchResponse> placesSearchResponseFuture = client.nearBySearch(nearBySearchRequestParams);
        PlacesSearchResponse placesSearchResponse = placesSearchResponseFuture.get();
        Assert.assertEquals(20, placesSearchResponse.getResults().size());
    }

    @Test
    public void testTextSearch() throws ExecutionException, InterruptedException {
        TextSearchRequestParams textSearchRequestParams = createTextSearchRequestParams();
        Future<PlacesSearchResponse> placesSearchResponseFuture = client.textSearch(textSearchRequestParams);
        PlacesSearchResponse placesSearchResponse = placesSearchResponseFuture.get();
        Assert.assertEquals(1, placesSearchResponse.getResults().size());
    }

    @Test
    public void testGetPlaceDetail() throws ExecutionException, InterruptedException {
        PlaceDetailRequestParams placeDetailRequestParams = createPlaceDetailRequestParams();
        Future<PlaceDetailResponse> placeDetailResponseFuture = client.getPlaceDetail(placeDetailRequestParams);
        PlaceDetailResponse placeDetailResponse = placeDetailResponseFuture.get();
        Assert.assertNotNull(placeDetailResponse.getResult());
    }

    @Test
    public void testMultipleRequestAndPageToken() throws ExecutionException, InterruptedException {
        NearBySearchRequestParams nearBySearchRequestParams = createNearBySearchRequestParams();
        Future<PlacesSearchResponse> placesSearchResponseFuture = client.nearBySearch(nearBySearchRequestParams);
        PlacesSearchResponse placesSearchResponse = placesSearchResponseFuture.get();
        NearBySearchRequestParams newParams = new NearBySearchRequestParams();
        newParams.setKey(GoogleConfigs.API_KEY);
        newParams.setPageToken(placesSearchResponse.getNextPageToken());
        Thread.sleep(1050);
        Future<PlacesSearchResponse> newResponseFuture = client.nearBySearch(newParams);
        PlacesSearchResponse newResponse = newResponseFuture.get();
        Assert.assertTrue(newResponse.getResults().size() > 0);
    }

    private NearBySearchRequestParams createNearBySearchRequestParams() {
        NearBySearchRequestParams nearBySearchRequestParams = new NearBySearchRequestParams();
        nearBySearchRequestParams.setLocation("37.308835,-121.993801");
        nearBySearchRequestParams.setRadius("3200");
        nearBySearchRequestParams.setType("restaurant");
        nearBySearchRequestParams.setKeyword("chinese");
        nearBySearchRequestParams.setKey(GoogleConfigs.API_KEY);
        return nearBySearchRequestParams;
    }

    private TextSearchRequestParams createTextSearchRequestParams() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setKey(GoogleConfigs.API_KEY);
        textSearchRequestParams.setLocation("37.308835,-121.993801");
        textSearchRequestParams.setQuery("韶山印象");
        textSearchRequestParams.setType("restaurant");
        textSearchRequestParams.setRadius("5000");
        return textSearchRequestParams;
    }

    private PlaceDetailRequestParams createPlaceDetailRequestParams() {
        PlaceDetailRequestParams placeDetailRequestParams = new PlaceDetailRequestParams();
        placeDetailRequestParams.setPlaceId("ChIJl41rEWC1j4AR9m5ndL7k-Js");
        placeDetailRequestParams.setKey(GoogleConfigs.API_KEY);
        return placeDetailRequestParams;
    }

}
