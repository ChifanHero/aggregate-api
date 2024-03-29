package com.chifanhero.api.models.request;

/**
 * Created by shiyan on 5/6/17.
 */
public enum SortOrder {

    NEAREST("nearest"),
    RATING("rating"),
    BEST_MATCH("best_match");

    private String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
