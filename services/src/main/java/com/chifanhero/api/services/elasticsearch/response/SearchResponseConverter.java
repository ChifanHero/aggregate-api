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
import java.util.List;

/**
 * Convert elasticsearch response to domain response
 * Created by shiyan on 5/21/17.
 */
public class SearchResponseConverter {

    public static RestaurantSearchResponse toLocalSearchResponse(JSONObject searchResponse) {
        Preconditions.checkNotNull(searchResponse);
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        if (searchResponse.has("hits")) {
            JSONObject hits = searchResponse.getJSONObject("hits");
            if (hits.has("hits")) {
                JSONArray hitsArray = hits.getJSONArray("hits");
                List<Restaurant> results = new ArrayList<>();
                for (int i = 0; i < hitsArray.length(); i++) {
                    results.add(SearchResponseConverter.toRestaurant(hitsArray.getJSONObject(i)));
                }
                response.setResults(results);
            }
        }
        return response;
    }

    private static Restaurant toRestaurant(JSONObject hit) {
        Preconditions.checkNotNull(hit);
        Restaurant restaurant = new Restaurant();
        restaurant.setSource(Source.CHIFANHERO);
        JSONObject source = hit.getJSONObject("_source");
        if (source.has(FieldNames.NAME)) {
            restaurant.setName(source.getString(FieldNames.NAME));
        }
        if (source.has(FieldNames.ENGLISH_NAME)) {
            restaurant.setGoogleName(source.getString(FieldNames.ENGLISH_NAME));
        }
        if (source.has(FieldNames.RATING)) {
            restaurant.setRating(source.getDouble(FieldNames.RATING));
        }
        if (source.has(FieldNames.GOOGLE_PLACE_ID)) {
            restaurant.setPlaceId(source.getString(FieldNames.GOOGLE_PLACE_ID));
        }
        if (source.has(FieldNames.COORDINATES)) {
            JSONArray lonlat = source.getJSONArray(FieldNames.COORDINATES);
            Coordinates coordinates = new Coordinates();
            coordinates.setLongitude(lonlat.getDouble(0));
            coordinates.setLatitude(lonlat.getDouble(1));
            restaurant.setCoordinates(coordinates);
        }
        if (source.has(FieldNames.BLACKLISTED)) {
            restaurant.setBlacklisted(source.getBoolean(FieldNames.BLACKLISTED));
        }
        return restaurant;
    }
}
