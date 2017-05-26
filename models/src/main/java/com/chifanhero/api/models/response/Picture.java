package com.chifanhero.api.models.response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by shiyan on 5/6/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Picture {

    @JsonProperty("photo_reference")
    private String photoReference;
    private String url;

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
}
