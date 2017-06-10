package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.services.chifanhero.KeyNames;
import com.google.common.base.Preconditions;
import org.bson.Document;

import java.util.Arrays;
import java.util.Optional;


public class DocumentConverter {

    public static Document  toDocument(Restaurant restaurant) {
        Preconditions.checkNotNull(restaurant);
        Preconditions.checkNotNull(restaurant.getPlaceId());
        Document document = new Document();
        document.append(KeyNames.IS_RECOMMENDATION_CANDIDATE, restaurant.isRecommendationCandidate())
                .append(KeyNames.GOOGLE_PLACE_ID, restaurant.getPlaceId());
        Optional.ofNullable(restaurant.getName()).ifPresent(name -> document.append(KeyNames.NAME, restaurant.getName()));
        Optional.ofNullable(restaurant.getEnglighName()).ifPresent(englishName -> document.append(KeyNames.ENGLISH_NAME, restaurant.getEnglighName()));
        Optional.ofNullable(restaurant.getCoordinates()).filter(coordinates -> coordinates.getLatitude() != null && coordinates.getLongitude() != null)
                .ifPresent(coordinates -> document.append(KeyNames.COORDINATES, Arrays.asList(restaurant.getCoordinates().getLongitude(),
                        restaurant.getCoordinates().getLatitude())));
        return document;
    }

    public static Restaurant toResult(Document document) {
        Restaurant restaurant = new Restaurant();
        // Currently only need names
        restaurant.setName(document.getString(KeyNames.NAME));
        restaurant.setEnglighName(document.getString(KeyNames.ENGLISH_NAME));
        restaurant.setPlaceId(document.getString(KeyNames.GOOGLE_PLACE_ID));
        return restaurant;
    }

}
