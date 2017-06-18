package com.chifanhero.api.models.request;

import com.chifanhero.api.models.response.Error;
import com.chifanhero.api.models.response.ErrorLevel;
import com.chifanhero.api.models.response.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NearbySearchRequestTest {

    @Test
    public void testNullLocation() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(400);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.NULL_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLatLon() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(400);
        Location location = new Location();
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLat() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(400);
        Location location = new Location();
        location.setLon(123.4);
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidLocationNoLon() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(400);
        Location location = new Location();
        location.setLat(123.4);
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_LOCATION, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNullRadius() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.NULL_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNegativeRadius() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(-2);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testRadiusOver() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(50001);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RADIUS, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testInvalidSortOrder() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setSortOrder("invalid");
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_SORT_ORDER, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testNegativeRating() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setRating(-1.0);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RATING, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testRatingOver() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(500);
        Location location = new Location();
        location.setLat(123.4);
        location.setLon(234.5);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setRating(5.1);
        List<Error> errors = nearbySearchRequest.validate();
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals(ErrorMessage.INVALID_RATING, error.getMessage());
        Assert.assertEquals(ErrorLevel.ERROR, error.getLevel());
    }

    @Test
    public void testDefaultValue() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Assert.assertEquals(SortOrder.BEST_MATCH.getValue(), nearbySearchRequest.getSortOrder());
        Assert.assertEquals("restaurant", nearbySearchRequest.getType());
        Assert.assertEquals("chinese", nearbySearchRequest.getKeyword());
        Assert.assertEquals(false, nearbySearchRequest.getOpenNow());
    }

    @Test
    public void testToString() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setKeyword("chinese");
        Location location = new Location();
        location.setLat(37.12);
        location.setLon(-121.34);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setSortOrder(SortOrder.NEAREST.name());
        nearbySearchRequest.setRadius(500);
        nearbySearchRequest.setRating(4.5);
        nearbySearchRequest.setOpenNow(true);
        Assert.assertEquals("{location={lat=37.12;lon=-121.34};radius=500;type=restaurant;keyword=chinese;sortOrder=NEAREST;openNow=true;rating=4.5}", nearbySearchRequest.toString());
    }
}
