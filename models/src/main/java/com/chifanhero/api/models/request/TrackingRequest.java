package com.chifanhero.api.models.request;

/**
 * Created by shiyan on 8/20/17.
 */
public class TrackingRequest {

    private String restaurantId;
    private String userIdentifier;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
}
