package com.chifanhero.api.services.google.client;

import com.chifanhero.api.models.google.PlacesSearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by shiyan on 5/3/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGooglePlacesClient {

    @Autowired
    GooglePlacesClient client;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        NearBySearchRequestParams nearBySearchRequestParams = new NearBySearchRequestParams();
        nearBySearchRequestParams.setLocation(new LocationParam().setLatitude(37.3088354).setLongitude(-121.99380080000003).getParam()).setRadius("500").setType("restaurant").setKeyword("chinese").setKey("AIzaSyBv3gtDERygNxP2lk7fwoPMcNCPfuGZdW0");
        Future<PlacesSearchResponse> responseFuture = client.nearBySearch(nearBySearchRequestParams);
        PlacesSearchResponse response = responseFuture.get();
//        System.out.println(response.getResponseBody());
    }
}
