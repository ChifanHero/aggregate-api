package com.chifanhero.api.services.elasticsearch.response;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convert elasticsearch response to domain response
 * Created by shiyan on 5/21/17.
 */
public class SearchResponseConverter {

    public static RestaurantSearchResponse toLocalSearchResponse(JSONObject searchResponse) {
        if (searchResponse == null) {
            return null;
        }
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        if (searchResponse.has("hits")) {
            JSONObject hits = searchResponse.getJSONObject("hits");
            if (hits.has("hits")) {
                JSONArray hitsArray = hits.getJSONArray("hits");
                List<Restaurant> results = new ArrayList<>();
                for (int i = 0; i < hitsArray.length(); i++) {
                    results.add(toRestaurant(hitsArray.getJSONObject(i)));
                }
                response.setResults(results);
            }
        }
        return response;
    }

    public static Map<String, Restaurant> convertBatchGetResponse(JSONObject batchGetResponse) {
        if (batchGetResponse == null) {
            return null;
        }
        Map<String, Restaurant> result = new HashMap<>();
        if (batchGetResponse.has("docs")) {
            JSONArray docsArray = batchGetResponse.getJSONArray("docs");
            for (int i = 0; i < docsArray.length(); i++) {
                Restaurant restaurant = toRestaurant(docsArray.getJSONObject(i));
                result.put(restaurant.getId(), restaurant);
            }
        }
        return result;
    }

    private static Restaurant toRestaurant(JSONObject hit) {
        if (hit == null || !hit.has("_source")) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setSource(Source.CHIFANHERO);
        JSONObject source = hit.getJSONObject("_source");
        if (hit.has("_score") && !hit.isNull("_score")) {
            restaurant.setScore(hit.optDouble("_score"));
        }
        if (hit.has("_id") && !hit.isNull("_id")) {
            restaurant.setId(hit.optString("_id"));
        }
        if (source.has(FieldNames.NAME)) {
            restaurant.setName(source.optString(FieldNames.NAME));
        }
        if (source.has(FieldNames.GOOGLE_NAME)) {
            restaurant.setGoogleName(source.optString(FieldNames.GOOGLE_NAME));
        }
        if (source.has(FieldNames.GOOGLE_RATING)) {
            restaurant.setRating(source.optDouble(FieldNames.GOOGLE_RATING));
        }
        if (source.has(FieldNames.RATING)) {
            restaurant.setRating(source.optDouble(FieldNames.RATING));
        }
        if (source.has(FieldNames.GOOGLE_PLACE_ID)) {
            restaurant.setPlaceId(source.optString(FieldNames.GOOGLE_PLACE_ID));
        }
        if (source.has(FieldNames.COORDINATES)) {
            JSONArray lonlat = source.getJSONArray(FieldNames.COORDINATES);
            Coordinates coordinates = new Coordinates();
            coordinates.setLongitude(lonlat.optDouble(0));
            coordinates.setLatitude(lonlat.optDouble(1));
            restaurant.setCoordinates(coordinates);
        }
        if (source.has(FieldNames.BLACKLISTED)) {
            restaurant.setBlacklisted(source.optBoolean(FieldNames.BLACKLISTED));
        }
        return restaurant;
    }
}
