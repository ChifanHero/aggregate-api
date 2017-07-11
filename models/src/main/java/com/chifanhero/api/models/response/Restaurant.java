package com.chifanhero.api.models.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shiyan on 5/6/17.
 */
public class Restaurant {

    private String address;

    private String phone;

    private String id;

    private String name;

    @JsonProperty("google_name")
    private String googleName;

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
    private Boolean recommendationCandidate;

    @JsonIgnore
    private Boolean blacklisted;

    @JsonIgnore
    private Double score;

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

    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
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

    public Boolean getRecommendationCandidate() {
        return recommendationCandidate;
    }

    public void setRecommendationCandidate(Boolean recommendationCandidate) {
        this.recommendationCandidate = recommendationCandidate;
    }

    public Boolean getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void applyPatch(Restaurant patch) {
        if (patch == null) {
            return;
        }
        if (!placeId.equals(patch.getPlaceId())) {
            throw new IllegalArgumentException("Place id has to be same.");
        }
        address = address == null? patch.getAddress(): address;
        phone = phone == null? patch.getPhone(): phone;
        id = id == null? patch.getId(): id;
        name = name == null? patch.getName(): name;
        googleName = googleName == null? patch.getGoogleName(): googleName;
        rating = rating == null? patch.getRating(): rating;
        picture = picture == null? patch.getPicture(): picture;
        permanentlyClosed = permanentlyClosed == null? patch.getPermanentlyClosed(): permanentlyClosed;
        openNow = openNow == null? patch.getOpenNow(): openNow;
        distance = distance == null? patch.getDistance(): distance;
        coordinates = coordinates == null? patch.getCoordinates(): coordinates;
        blacklisted = blacklisted == null? patch.getBlacklisted(): blacklisted;
        score = score == null? patch.getScore(): score;
    }
}
