package com.chifanhero.api.services.google.client.client;

import com.chifanhero.api.common.GetRequestParams;
import com.chifanhero.api.common.annotations.ParamKey;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Nearby search parameters
 * Created by shiyan on 5/2/17.
 */
public class NearBySearchRequestParams extends GetRequestParams {

    /**
     * Application API key
     */
    @NotNull
    private String key;

    /**
     * The latitude/longitude around which to retrieve place information. This must be specified as latitude,longitude.
     */
    @NotNull
    private String location;

    /**
     * Defines the distance (in meters) within which to return place results. The maximum allowed radius is 50 000 meters. Note that radius must not be included if rankby=distance (described under Optional parameters below) is specified.
     */
    private String radius;

    /**
     * A term to be matched against all content that Google has indexed for this place, including but not limited to name, type, and address, as well as customer reviews and other third-party content.
     */
    private String keyword;

    /**
     * The language code, indicating in which language the results should be returned, if possible.
     */
    private String language;

    /**
     * Returns only those places that are open for business at the time the query is sent. Places that do not specify opening hours in the Google Places database will not be returned if you include this parameter in your query.
     */
    @ParamKey("opennow")
    private Boolean openNow;

    /**
     * Specifies the order in which results are listed. Note that rankby must not be included if radius (described under Required parameters above) is specified.
     */
    @ParamKey("rankby")
    private RankBy rankBy;

    /**
     * Restricts the results to places matching the specified type. Only one type may be specified (if more than one type is provided, all types following the first entry are ignored).
     */
    private String type;

    /**
     * Returns the next 20 results from a previously run search. Setting a pagetoken parameter will execute a search with the same parameters used previously — all parameters other than pagetoken will be ignored.
     */
    @ParamKey("pagetoken")
    private String pageToken;

    public String getKey() {
        return key;
    }

    public NearBySearchRequestParams setKey(String key) {
        this.key = key;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public NearBySearchRequestParams setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getRadius() {
        return radius;
    }

    public NearBySearchRequestParams setRadius(String radius) {
        this.radius = radius;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public NearBySearchRequestParams setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public NearBySearchRequestParams setLanguage(String language) {
        this.language = language;
        return this;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public NearBySearchRequestParams setOpenNow(Boolean openNow) {
        this.openNow = openNow;
        return this;
    }

    public RankBy getRankBy() {
        return rankBy;
    }

    public NearBySearchRequestParams setRankBy(RankBy rankBy) {
        this.rankBy = rankBy;
        return this;
    }

    public String getType() {
        return type;
    }

    public NearBySearchRequestParams setType(String type) {
        this.type = type;
        return this;
    }

    public String getPageToken() {
        return pageToken;
    }

    public NearBySearchRequestParams setPageToken(String pageToken) {
        this.pageToken = pageToken;
        return this;
    }

    @Override
    public void validate() {
        super.validate();
        validateRadius();
        validateRankBy();
    }

    private void validateRankBy() {
        if (rankBy != null && rankBy == RankBy.distance) {
            if (keyword == null && type == null) {
                throw new IllegalArgumentException("If rankby=distance  is specified, then one or more of keyword, name, or type is required.");
            }
        }
    }

    private void validateRadius() {
        if ((rankBy == null || rankBy != RankBy.distance) && radius == null) {
            throw new IllegalArgumentException("radius is required if rankby != distance");
        }
        if (rankBy != null && rankBy == RankBy.distance && radius != null) {
            throw new IllegalArgumentException("radius must not be included if rankby == distance");
        }
        if (radius != null && new BigDecimal(radius).compareTo(new BigDecimal("50000")) > 0) {
            throw new IllegalArgumentException("radius cannot be greater than 50000 meters");
        }
    }
}
