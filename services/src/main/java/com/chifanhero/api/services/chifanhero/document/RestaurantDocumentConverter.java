package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.services.chifanhero.KeyNames;
import com.google.common.base.Preconditions;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class RestaurantDocumentConverter {

    public static Document toDocument(Restaurant restaurant) {
        Preconditions.checkNotNull(restaurant);
        Preconditions.checkNotNull(restaurant.getPlaceId());
        Document document = new Document();
        document.append(KeyNames.GOOGLE_PLACE_ID, restaurant.getPlaceId());
//        Optional.ofNullable(restaurant.getName()).ifPresent(name -> document.append(KeyNames.NAME, restaurant.getName()));
        Optional.ofNullable(restaurant.getGoogleName()).ifPresent(googleName -> document.append(KeyNames.GOOGLE_NAME, restaurant.getGoogleName()));
        Optional.ofNullable(restaurant.getCoordinates()).filter(coordinates -> coordinates.getLatitude() != null && coordinates.getLongitude() != null)
                .ifPresent(coordinates -> document.append(KeyNames.COORDINATES, Arrays.asList(restaurant.getCoordinates().getLongitude(),
                        restaurant.getCoordinates().getLatitude())));
        return document;
    }

    public static Restaurant toResult(Document document) {
        Restaurant restaurant = new Restaurant();
        // Currently only need names, coordinates and rating for testing
        restaurant.setId(document.getString(KeyNames.ID));
        restaurant.setName(document.getString(KeyNames.NAME));
        restaurant.setGoogleName(document.getString(KeyNames.GOOGLE_NAME));
        restaurant.setPlaceId(document.getString(KeyNames.GOOGLE_PLACE_ID));
        restaurant.setRecommendationCandidate(document.getBoolean(KeyNames.IS_RECOMMENDATION_CANDIDATE));
        restaurant.setRating(document.getDouble(KeyNames.GOOGLE_RATING));
        Optional.ofNullable(document.get(KeyNames.COORDINATES)).ifPresent(lonlat -> {
            List<Double> lonlatList = (List<Double>) lonlat;
            Coordinates coordinates = new Coordinates();
            coordinates.setLongitude(lonlatList.get(0));
            coordinates.setLatitude(lonlatList.get(1));
            restaurant.setCoordinates(coordinates);
        });
        return restaurant;
    }

}
