package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.services.chifanhero.KeyNames;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DocumentConverterTest {

    @Test
    public void testDocumentToResult() {
        Document document = new Document();
        document.append(KeyNames.NAME, "吃饭英雄");
        document.append(KeyNames.ENGLISH_NAME, "chifanhero");
        document.append(KeyNames.GOOGLE_PLACE_ID, "google_place_id");
        document.append(KeyNames.COORDINATES, Arrays.asList(-121.99 ,37.30));
        Restaurant restaurant = DocumentConverter.toResult(document);
        Assert.assertEquals("吃饭英雄", restaurant.getName());
        Assert.assertEquals("chifanhero", restaurant.getEnglighName());
        Assert.assertEquals("google_place_id", restaurant.getPlaceId());
        Coordinates coordinates = restaurant.getCoordinates();
        Assert.assertNotNull(coordinates);
        Assert.assertEquals(new Double(-121.99), coordinates.getLongitude());
        Assert.assertEquals(new Double(37.30), coordinates.getLatitude());
    }

    @Test
    public void testResultToDocument() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("吃饭英雄");
        restaurant.setEnglighName("chifanhero");
        restaurant.setRecommendationCandidate(true);
        restaurant.setPlaceId("place_id");
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(37.242312);
        coordinates.setLongitude(-121.764887);
        restaurant.setCoordinates(coordinates);
        Document document = DocumentConverter.toDocument(restaurant);
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
