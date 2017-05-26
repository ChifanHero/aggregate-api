package com.chifanhero.api.services.yelp.oauth;

import com.chifanhero.api.configs.YelpConfigs;
import com.google.common.base.Preconditions;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyan on 4/27/17.
 */
@Component
public class OauthService {

    private final CloseableHttpClient httpClient;

    @Autowired
    public OauthService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getBearToken() {
        HttpPost post = new HttpPost(YelpConfigs.OAUTH_ENDPOINT);
        List<BasicNameValuePair> parametersBody = new ArrayList<>();

        parametersBody.add(new BasicNameValuePair(OauthConstants.GRANT_TYPE_KEY,
                OauthConstants.GRANT_TYPE_VALUE));


        parametersBody.add(new BasicNameValuePair(OauthConstants.CLIENT_ID_KEY,
                YelpConfigs.APP_ID));

        parametersBody.add(new BasicNameValuePair(
                OauthConstants.CLIENT_SECRET_KEY, YelpConfigs.APP_SECRET));

        CloseableHttpResponse response;
        String accessToken;
        try {
            post.setEntity(new UrlEncodedFormEntity(parametersBody, StandardCharsets.UTF_8));
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            JSONObject jsonObject = parseResponse(response);

            accessToken = jsonObject.getString(OauthConstants.ACCESS_TOKEN_KEY);
            return accessToken;
        } catch (IOException ignored) {

        }
        return null;
    }

    private JSONObject parseResponse(CloseableHttpResponse response) throws IOException {
        Preconditions.checkNotNull(response);
        String json = EntityUtils.toString(response.getEntity());
        return new JSONObject(json);
    }
}
