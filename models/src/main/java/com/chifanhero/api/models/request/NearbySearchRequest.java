package com.chifanhero.api.models.request;

import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.ErrorLevel;
import com.chifanhero.api.models.response.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NearbySearchRequest extends SearchRequest<NearbySearchRequest> {

    private Location location;
    private Integer radius;
    private String type = "restaurant";
    private String keyword = "chinese";
    private String sortOrder = SortOrder.BEST_MATCH.getValue();
    private Boolean openNow;
    private Double rating;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    public List<Error> validate() {
        List<Error> errors = new ArrayList<>();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Optional.ofNullable(location).ifPresent(value ->
                sb.append("location=").append(value.toString()).append(";"));
        Optional.ofNullable(radius).ifPresent(value ->
                sb.append("radius=").append(value).append(";"));
        Optional.ofNullable(type).ifPresent(value ->
                sb.append("type=").append(type).append(";"));
        Optional.ofNullable(keyword).ifPresent(value ->
                sb.append("keyword=").append(value).append(";"));
        Optional.ofNullable(sortOrder).ifPresent(value ->
                sb.append("sortOrder=").append(value).append(";"));
        Optional.ofNullable(openNow).ifPresent(value ->
                sb.append("openNow=").append(value).append(";"));
        Optional.ofNullable(rating).ifPresent(value ->
                sb.append("rating=").append(value).append(";"));
        if (sb.charAt(sb.length() - 1) == ';') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NearbySearchRequest)) {
            return false;
        }
        NearbySearchRequest target = (NearbySearchRequest) obj;
        return EqualUtil.equal(location, target.getLocation())
                && EqualUtil.equal(radius, target.getRadius())
                && EqualUtil.equal(type, target.getType())
                && EqualUtil.equal(keyword, target.getKeyword())
                && EqualUtil.equal(sortOrder, target.getSortOrder())
                && EqualUtil.equal(openNow, target.getOpenNow())
                && EqualUtil.equal(rating, target.getRating());
    }

    @Override
    public NearbySearchRequest clone() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Optional.ofNullable(location).ifPresent(value ->
                nearbySearchRequest.setLocation(location.clone()));
        nearbySearchRequest.setOpenNow(openNow);
        nearbySearchRequest.setRating(rating);
        nearbySearchRequest.setRadius(radius);
        nearbySearchRequest.setKeyword(keyword);
        nearbySearchRequest.setSortOrder(sortOrder);
        nearbySearchRequest.setType(type);
        return nearbySearchRequest;
    }

}
