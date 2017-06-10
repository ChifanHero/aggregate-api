package com.chifanhero.api.services.elasticsearch.response;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import com.google.common.base.Preconditions;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Convert elasticsearch response to domain response
 * Created by shiyan on 5/21/17.
 */
public class SearchResponseConverter {

    public static RestaurantSearchResponse toLocalSearchResponse(org.elasticsearch.action.search.SearchResponse searchResponse) {
        Preconditions.checkNotNull(searchResponse);
        RestaurantSearchResponse response = new RestaurantSearchResponse();
        List<Restaurant> restaurants = Optional.ofNullable(searchResponse.getHits()).map(SearchHits::getHits).map(searchHits -> Arrays.stream(searchHits).map(SearchResponseConverter::hitToResult).collect(Collectors.toList())).orElse(Collections.emptyList());
        response.setResults(restaurants);
        return response;
    }

    private static Restaurant hitToResult(SearchHit searchHit) {
        Preconditions.checkNotNull(searchHit);
        Restaurant restaurant = new Restaurant();
        restaurant.setSource(Source.CHIFANHERO);
        Map<String, Object> source = searchHit.getSourceAsMap();
        Optional.ofNullable(source.get(FieldNames.NAME)).ifPresent(name -> restaurant.setName((String) name));
        Optional.ofNullable(source.get(FieldNames.ENGLISH_NAME)).ifPresent(englishName -> restaurant.setEnglighName((String) englishName));
        Optional.ofNullable(source.get(FieldNames.RATING)).ifPresent(rating -> restaurant.setRating((Double) rating));
        Optional.ofNullable(source.get(FieldNames.GOOGLE_PLACE_ID)).ifPresent(placeId -> restaurant.setPlaceId((String) placeId));
        Optional.ofNullable(source.get(FieldNames.COORDINATES)).ifPresent(lonlat -> {
            List<Double> lonlatList = (List<Double>) lonlat;
            Coordinates coordinates = new Coordinates();
            coordinates.setLongitude(lonlatList.get(0));
            coordinates.setLatitude(lonlatList.get(1));
            restaurant.setCoordinates(coordinates);
        });
        return restaurant;
    }
}
