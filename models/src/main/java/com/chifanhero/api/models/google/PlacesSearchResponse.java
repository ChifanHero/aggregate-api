package com.chifanhero.api.models.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by shiyan on 5/4/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesSearchResponse {

    @JsonProperty("next_page_token")
    private String nextPageToken;
    private List<Place> results;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }
}
