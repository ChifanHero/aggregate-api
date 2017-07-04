package com.chifanhero.api.models.response;

import java.util.List;

/**
 * Created by shiyan on 5/6/17.
 */
public class RestaurantSearchResponse {

    private List<Restaurant> results;
    private List<Error> errors;

    public List<Restaurant> getResults() {
        return results;
    }

    public void setResults(List<Restaurant> results) {
        this.results = results;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
