package com.chifanhero.api.models.request;

import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.ErrorLevel;
import com.chifanhero.api.models.response.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextSearchRequest extends SearchRequest {

    private String query;
    private String type = "restaurant";
    private Location location;
    private Integer radius;
    private String sortOrder = SortOrder.BEST_MATCH.getValue();
    private Boolean openNow = false;
    private Double rating;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    List<Error> validate() {
        List<Error> errors = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            errors.add(new Error(ErrorMessage.INVALID_QUERY, ErrorLevel.ERROR));
        }
        if (location == null) {
            errors.add(new Error(ErrorMessage.NULL_LOCATION, ErrorLevel.ERROR));
        } else if (location.getLat() == null || location.getLon() == null){
            errors.add(new Error(ErrorMessage.INVALID_LOCATION, ErrorLevel.ERROR));
        }
        if (radius == null) {
            errors.add(new Error(ErrorMessage.NULL_RADIUS, ErrorLevel.ERROR));
        } else if (radius <= 0 || radius > 50000) {
            errors.add(new Error(ErrorMessage.INVALID_RADIUS, ErrorLevel.ERROR));
        }
        if ((!SortOrder.HOTTEST.getValue().equals(sortOrder))
                && (!SortOrder.NEAREST.getValue().equals(sortOrder))
                && (!SortOrder.BEST_MATCH.getValue().equals(sortOrder))) {
            errors.add(new Error(ErrorMessage.INVALID_SORT_ORDER, ErrorLevel.ERROR));
        }
        if (rating != null && (rating < 0.0 || rating > 5.0)) {
            errors.add(new Error(ErrorMessage.INVALID_RATING, ErrorLevel.ERROR));
        }
        return Collections.unmodifiableList(errors);
    }
}
