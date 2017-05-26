package com.chifanhero.api.models.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by shiyan on 5/4/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesSearchResponse {
    private List<Place> results;

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }
}
