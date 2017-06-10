package com.chifanhero.api.services.elasticsearch.response;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.models.response.SearchResponse;
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

    public static SearchResponse toLocalSearchResponse(org.elasticsearch.action.search.SearchResponse searchResponse) {
        Preconditions.checkNotNull(searchResponse);
        SearchResponse response = new SearchResponse();
        List<Result> results = Optional.ofNullable(searchResponse.getHits()).map(SearchHits::getHits).map(searchHits -> Arrays.stream(searchHits).map(SearchResponseConverter::hitToResult).collect(Collectors.toList())).orElse(Collections.emptyList());
        response.setResults(results);
        return response;
    }

    private static Result hitToResult(SearchHit searchHit) {
        Preconditions.checkNotNull(searchHit);
        Result result = new Result();
        result.setSource(Source.CHIFANHERO);
        Map<String, Object> source = searchHit.getSourceAsMap();
        Optional.ofNullable(source.get(FieldNames.NAME)).ifPresent(name -> result.setName((String) name));
        Optional.ofNullable(source.get(FieldNames.ENGLISH_NAME)).ifPresent(englishName -> result.setEnglighName((String) englishName));
        Optional.ofNullable(source.get(FieldNames.RATING)).ifPresent(rating -> result.setRating((Double) rating));
        Optional.ofNullable(source.get(FieldNames.GOOGLE_PLACE_ID)).ifPresent(placeId -> result.setPlaceId((String) placeId));
        Optional.ofNullable(source.get(FieldNames.COORDINATES)).ifPresent(lonlat -> {
            List<Double> lonlatList = (List<Double>) lonlat;
            Coordinates coordinates = new Coordinates();
            coordinates.setLongitude(lonlatList.get(0));
            coordinates.setLatitude(lonlatList.get(1));
            result.setCoordinates(coordinates);
        });
        return result;
    }
}
