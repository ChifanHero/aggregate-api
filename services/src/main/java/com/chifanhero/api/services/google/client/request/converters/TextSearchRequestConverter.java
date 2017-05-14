package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;

public class TextSearchRequestConverter {

    public static TextSearchRequestParams toParams(TextSearchRequest textSearchRequest) {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setRadius(String.valueOf(textSearchRequest.getRadius()));
        textSearchRequestParams.setType(textSearchRequest.getType());
        textSearchRequestParams.setOpenNow(textSearchRequest.getOpenNow());
        textSearchRequestParams.setQuery(textSearchRequest.getQuery());
        textSearchRequestParams.setLocation(LocationConverter.toLocationParam(textSearchRequest.getLocation()));
        // TODO - pageToken?
        return textSearchRequestParams;
    }
}
