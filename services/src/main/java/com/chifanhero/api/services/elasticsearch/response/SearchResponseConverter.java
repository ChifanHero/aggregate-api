package com.chifanhero.api.services.elasticsearch.response;

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
        result.setName((String) source.get(FieldNames.NAME));
        result.setEnglighName((String) source.get(FieldNames.ENGLISH_NAME));
        result.setRating((Double) source.get(FieldNames.RATING));
        result.setPlaceId((String) source.get(FieldNames.GOOGLE_PLACE_ID));
        //TODO - get coordinates
        return result;
    }
}
