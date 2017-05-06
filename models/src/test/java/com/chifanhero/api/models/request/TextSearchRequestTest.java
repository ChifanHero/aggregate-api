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
        Assert.assertEquals(SortOrder.NEAREST.getValue(), textSearchRequest.getSortOrder());
        Assert.assertEquals("restaurant", textSearchRequest.getType());
        Assert.assertEquals(false, textSearchRequest.getOpenNow());
    }
}
