package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.services.chifanhero.KeyNames;
import com.google.common.base.Preconditions;
import org.bson.Document;

import java.util.Arrays;
import java.util.Optional;


public class DocumentConverter {

    public static Document  toDocument(Result result) {
        Preconditions.checkNotNull(result);
        Preconditions.checkNotNull(result.getPlaceId());
        Document document = new Document();
        document.append(KeyNames.IS_RECOMMENDATION_CANDIDATE, result.isRecommendationCandidate())
                .append(KeyNames.GOOGLE_PLACE_ID, result.getPlaceId());
        Optional.ofNullable(result.getName()).ifPresent(name -> document.append(KeyNames.NAME, result.getName()));
        Optional.ofNullable(result.getEnglighName()).ifPresent(englishName -> document.append(KeyNames.ENGLISH_NAME, result.getEnglighName()));
        Optional.ofNullable(result.getCoordinates()).filter(coordinates -> coordinates.getLatitude() != null && coordinates.getLongitude() != null)
                .ifPresent(coordinates -> document.append(KeyNames.COORDINATES, Arrays.asList(result.getCoordinates().getLongitude(),
                        result.getCoordinates().getLatitude())));
        return document;
    }

    public static Result toResult(Document document) {
        Result result = new Result();
        // Currently only need names
        result.setName(document.getString(KeyNames.NAME));
        result.setEnglighName(document.getString(KeyNames.ENGLISH_NAME));
        result.setPlaceId(document.getString(KeyNames.GOOGLE_PLACE_ID));
        return result;
    }

}
