package com.chifanhero.api.common;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Takes base url and parameters and create a complete get url
 * Created by shiyan on 4/29/17.
 */
public class GetRequest { // This class should probably be moved to a separate module or package to be shared.
// For now since no other code is using it, it can be left here.

    private final static Logger LOGGER = LoggerFactory.getLogger(GetRequest.class);

    private String baseUrl;
    private Map<String, Object> requestParams;
    private String url;

    public GetRequest(String baseUrl, Map<String, Object> requestParams) {
        Preconditions.checkArgument(baseUrl != null);
        this.baseUrl = baseUrl;
        this.requestParams = requestParams;
        this.url = computeUrl();
        LOGGER.debug(url);
    }

    private String computeUrl() {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (requestParams.size() > 0) {
            urlBuilder.append("?");
            requestParams.entrySet().forEach(paramEntry -> {
                urlBuilder.append(paramEntry.getKey());
                urlBuilder.append("=");
                urlBuilder.append(paramEntry.getValue().toString());
                urlBuilder.append("&");
            });
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        return urlBuilder.toString();
    }

    public String getUrl() {
        return url;
    }
}
