package com.chifanhero.api.services.yelp.client;

import com.chifanhero.api.models.yelp.Business;
import com.chifanhero.api.services.yelp.oauth.Oauth;
import org.asynchttpclient.AsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Client to interact with Yelp Fusion API.
 * Created by shiyan on 4/27/17.
 */
@Component
public class AsyncYelpFusionClient {

    private final Oauth oauth;
    private final AsyncHttpClient asyncHttpClient;

    @Autowired
    public AsyncYelpFusionClient(Oauth oauth, AsyncHttpClient asyncHttpClient) {
        this.oauth = oauth;
        this.asyncHttpClient = asyncHttpClient;
    }

    /**
     * Get business through Business API
     * @param businessId the business id
     * @return business
     */
    public Future<Business> getBusiness(String businessId) {
        return null;
    }

}
