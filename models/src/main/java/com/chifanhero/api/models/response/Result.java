package com.chifanhero.api.models.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by shiyan on 5/6/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result {

    private String address;

    private String phone;

    private String id;

    private String name;

    @JsonProperty("english_name")
    private String englighName;

    @JsonIgnore
    private String placeId;

    private Double rating;

    @JsonIgnore
    private String reference;

    @JsonIgnore
    private Source source;

    private Picture picture;

    @JsonIgnore
    private Boolean permanentlyClosed;

    @JsonIgnore
    private Boolean openNow;

    private Double distance;

    @JsonIgnore
    private Coordinates coordinates;

    @JsonIgnore
    private boolean recommendationCandidate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEnglighName() {
        return englighName;
    }

    public void setEnglighName(String englighName) {
        this.englighName = englighName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Boolean getPermanentlyClosed() {
        return permanentlyClosed;
    }

    public void setPermanentlyClosed(Boolean permanentlyClosed) {
        this.permanentlyClosed = permanentlyClosed;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isRecommendationCandidate() {
        return recommendationCandidate;
    }

    public void setRecommendationCandidate(boolean recommendationCandidate) {
        this.recommendationCandidate = recommendationCandidate;
    }
}
