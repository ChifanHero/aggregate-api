package com.chifanhero.api.models.google;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by shiyan on 5/4/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailResponse {
    private Place result;

    public Place getResult() {
        return result;
    }

    public void setResult(Place result) {
        this.result = result;
    }
}
