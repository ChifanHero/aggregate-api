package com.chifanhero.api.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shiyan on 5/6/17.
 */
public class Picture {

    @JsonProperty("google_photo_reference")
    private String photoReference;
    private String url;

    @JsonProperty("html_attributes")
    private List<String> htmlAttributions;

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributes) {
        this.htmlAttributions = htmlAttributes;
    }
}
