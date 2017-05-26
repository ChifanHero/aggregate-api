package com.chifanhero.api.models.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Created by shiyan on 5/6/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchResponse {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
