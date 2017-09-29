package com.chifanhero.api.models.request;

import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.ErrorLevel;
import com.chifanhero.api.models.response.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TextSearchRequestTest {

    @Test
    public void testNullLocation() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(400);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.NULL_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLatLon() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(400);
        Location location = new Location();
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLat() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(400);
        Location location = new Location();
        location.setLon(123.4);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLon() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(400);
        Location location = new Location();
        location.setLat(123.4);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNullRadius() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.NULL_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNegativeRadius() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(-2);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testRadiusOver() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(50001);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidSortOrder() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        textSearchRequest.setSortOrder("invalid");
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_SORT_ORDER, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNegativeRating() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRating(-1.0);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RATING, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testRatingOver() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("sizzling");
        textSearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRating(5.1);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RATING, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNullQuery() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_QUERY, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testEmptyQuery() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("");
        textSearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        textSearchRequest.setLocation(location);
        List<Error> errors = textSearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_QUERY, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testDefaultValue() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        Assert.assertEquals(SortOrder.BEST_MATCH.getValue(), textSearchRequest.getSortOrder());
        Assert.assertEquals("restaurant", textSearchRequest.getType());
        Assert.assertNull(textSearchRequest.getOpenNow());
    }

    @Test
    public void testToString() {
        TextSearchRequest textSearchRequest = createTextSearchRequest();
        Assert.assertEquals("{query=hunan impression;type=restaurant;location={lat=37.12;lon=-121.34};radius=500;sortOrder=NEAREST;openNow=true;rating=4.5}", textSearchRequest.toString());
    }

    @Test
    public void testCloneAndEquals() {
        TextSearchRequest textSearchRequest = createTextSearchRequest();
        TextSearchRequest newTextSearchRequest = textSearchRequest.clone();
        Assert.assertTrue(textSearchRequest.equals(newTextSearchRequest));
    }

    @Test
    public void testNotEqual() {
        TextSearchRequest textSearchRequest = createTextSearchRequest();
        TextSearchRequest newTextSearchRequest = textSearchRequest.clone();
        newTextSearchRequest.setQuery("query");
        Assert.assertFalse(textSearchRequest.equals(newTextSearchRequest));
    }

    private TextSearchRequest createTextSearchRequest() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        Location location = new Location();
        location.setLat(37.12);
        location.setLon(-121.34);
        textSearchRequest.setLocation(location);
        textSearchRequest.setOpenNow(true);
        textSearchRequest.setType("restaurant");
        textSearchRequest.setQuery("hunan impression");
        textSearchRequest.setRadius(500);
        textSearchRequest.setRating(4.5);
        textSearchRequest.setSortOrder(SortOrder.NEAREST.name());
        return  textSearchRequest;
    }
}
