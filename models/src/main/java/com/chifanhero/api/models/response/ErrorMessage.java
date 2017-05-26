package com.chifanhero.api.models.response;

/**
 * Created by shiyan on 5/6/17.
 */
public class ErrorMessage {

    public final static String NULL_LOCATION = "location cannot be null";
    public final static String INVALID_LOCATION = "invalid location";
    public final static String NULL_RADIUS = "radius cannot be null";
    public final static String INVALID_RADIUS = "radius must be between 0 to 50000";
    public final static String INVALID_SORT_ORDER = "sortOrder must be hottest or nearest";
    public final static String INVALID_RATING = "rating must be between 0.0 to 5.0";
    public final static String INVALID_QUERY = "query cannot be null or empty";
}
