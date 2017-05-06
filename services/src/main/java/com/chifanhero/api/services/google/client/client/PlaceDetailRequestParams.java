package com.chifanhero.api.services.google.client.client;

import com.chifanhero.api.common.GetRequestParams;
import com.chifanhero.api.common.annotations.ParamKey;
import com.chifanhero.api.common.annotations.Union;
import com.chifanhero.api.common.annotations.Unions;

import javax.validation.constraints.NotNull;

/**
 * Created by shiyan on 5/4/17.
 */
@Unions({
        @Union(field1 = "placeId", field2 = "reference")
})
public class PlaceDetailRequestParams extends GetRequestParams {

    /**
     * A textual identifier that uniquely identifies a place, returned from a Place Search. You must supply one of placeid or reference, but not both
     */
    @ParamKey("placeid")
    private String placeId;

    /**
     * Application API key
     */
    @NotNull
    private String key;

    /**
     * A textual identifier that uniquely identifies a place, returned from a Place Search. The reference is now deprecated in favor of placeid.
     */
    private String reference;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
