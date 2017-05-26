package com.chifanhero.api.services.google.client.request;

import com.chifanhero.api.common.GetRequestParams;
import com.chifanhero.api.common.annotations.ParamKey;

import javax.validation.constraints.NotNull;

/**
 * Text search request
 * Created by shiyan on 5/3/17.
 */
public class TextSearchRequestParams extends GetRequestParams {

    /**
     * The text string on which to search. The Google Places service will return candidate matches based on this string and order the results based on their perceived relevance. This parameter becomes optional if the type parameter is also used in the search request.
     */
    private String query;

    /**
     * Application API key
     */
    @NotNull
    private String key;

    /**
     * The latitude/longitude around which to retrieve place information. This must be specified as latitude,longitude.
     */
    private String location;

    /**
     * Defines the distance (in meters) within which to bias place results. The maximum allowed radius is 50 000 meters. Results inside of this region will be ranked higher than results outside of the search circle; however, prominent results from outside of the search radius may be included.
     */
    private String radius;

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
     * Returns the next 20 results from a previously run search. Setting a pagetoken parameter will execute a search with the same parameters used previously — all parameters other than pagetoken will be ignored.
     */
    @ParamKey("pagetoken")
    private String pageToken;

    /**
     * Restricts the results to places matching the specified type. Only one type may be specified (if more than one type is provided, all types following the first entry are ignored).
     */
    private String type;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void validate() {
        super.validate();
        if (this.type == null && this.query == null) {
            throw new IllegalArgumentException("must have at least one of type or query");
        }
    }
}
