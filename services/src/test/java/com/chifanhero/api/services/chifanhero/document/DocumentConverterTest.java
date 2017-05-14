package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.services.chifanhero.KeyNames;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DocumentConverterTest {

    @Test
    public void testDocumentToResult() {
        Document document = new Document();
        document.append(KeyNames.NAME, "吃饭英雄");
        document.append(KeyNames.ENGLISH_NAME, "chifanhero");
        document.append(KeyNames.GOOGLE_PLACE_ID, "google_place_id");
        Result result = DocumentConverter.toResult(document);
        Assert.assertEquals("吃饭英雄", result.getName());
        Assert.assertEquals("chifanhero", result.getEnglighName());
        Assert.assertEquals("google_place_id", result.getPlaceId());
    }

    @Test
    public void testResultToDocument() {
        Result result = new Result();
        result.setName("吃饭英雄");
        result.setEnglighName("chifanhero");
        result.setRecommendationCandidate(true);
        result.setPlaceId("place_id");
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(37.242312);
        coordinates.setLongitude(-121.764887);
        result.setCoordinates(coordinates);
        Document document = DocumentConverter.toDocument(result);
        Assert.assertEquals("吃饭英雄", document.getString(KeyNames.NAME));
        Assert.assertEquals("chifanhero", document.getString(KeyNames.ENGLISH_NAME));
        Assert.assertNull(document.getString(KeyNames.ID));
        Assert.assertTrue(document.getBoolean(KeyNames.IS_RECOMMENDATION_CANDIDATE));
        List lonlat = (List) document.get(KeyNames.COORDINATES);
        Assert.assertEquals(-121.764887, lonlat.get(0));
        Assert.assertEquals(37.242312, lonlat.get(1));
        Assert.assertEquals("place_id", document.getString(KeyNames.GOOGLE_PLACE_ID));
    }
}
