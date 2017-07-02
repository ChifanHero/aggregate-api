package com.chifanhero.api.models.response;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 7/1/17.
 */
public class RestaurantTest {

    @Test
    public void testApplyNull() {
        Restaurant restaurant = createDefaultRestaurant();
        restaurant.applyPatch(null);
        assertDefaultRestaurant(restaurant);
    }

    @Test
    public void testApplyEmpty() {
        Restaurant restaurant = createDefaultRestaurant();
        Restaurant patch = new Restaurant();
        patch.setPlaceId(restaurant.getPlaceId());
        restaurant.applyPatch(patch);
        assertDefaultRestaurant(restaurant);
    }

    @Test
    public void testApplyFull() {
        Restaurant restaurant = new Restaurant();
        Restaurant patch = createDefaultRestaurant();
        restaurant.setPlaceId(patch.getPlaceId());
        restaurant.applyPatch(patch);
        assertDefaultRestaurant(restaurant);
    }

    @Test
    public void testDifferent() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("newName");
        Restaurant patch = createDefaultRestaurant();
        restaurant.setPlaceId(patch.getPlaceId());
        restaurant.applyPatch(patch);
        Assert.assertEquals("newName", restaurant.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyWrongPlaceId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId("1");
        Restaurant patch = new Restaurant();
        patch.setPlaceId("2");
        restaurant.applyPatch(patch);
    }

    private Restaurant createDefaultRestaurant() {
        return createRestaurant("name", "englishName", "placeId", "address",
                "1234567", 4.5, createPicture("http://url", "photoReference"), false,
                false, 2.5, createCoordinates(37.2, -121.3));
    }

    private void assertDefaultRestaurant(Restaurant restaurant) {
        Assert.assertEquals("name", restaurant.getName());
        Assert.assertEquals("englishName", restaurant.getEnglighName());
        Assert.assertEquals("placeId", restaurant.getPlaceId());
        Assert.assertEquals("address", restaurant.getAddress());
        Assert.assertEquals("1234567", restaurant.getPhone());
        Assert.assertTrue(restaurant.getRating() == 4.5);
        Assert.assertNotNull(restaurant.getPicture());
        Assert.assertEquals("http://url", restaurant.getPicture().getUrl());
        Assert.assertEquals("photoReference", restaurant.getPicture().getPhotoReference());
        Assert.assertFalse(restaurant.getPermanentlyClosed());
        Assert.assertFalse(restaurant.getOpenNow());
        Assert.assertTrue(restaurant.getDistance() == 2.5);
        Assert.assertNotNull(restaurant.getCoordinates());
        Assert.assertTrue(restaurant.getCoordinates().getLatitude() == 37.2);
        Assert.assertTrue(restaurant.getCoordinates().getLongitude() == -121.3);
    }

    private Restaurant createRestaurant(String name, String englishName, String placeId, String address, String phone,
                                        Double rating, Picture picture, Boolean permanentlyClosed,
                                        Boolean openNow, Double distance, Coordinates coordinates) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setEnglighName(englishName);
        restaurant.setPlaceId(placeId);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setRating(rating);
        restaurant.setPicture(picture);
        restaurant.setPermanentlyClosed(permanentlyClosed);
        restaurant.setOpenNow(openNow);
        restaurant.setDistance(distance);
        restaurant.setCoordinates(coordinates);
        return restaurant;
    }

    private Coordinates createCoordinates(double lat, double lon) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(lat);
        coordinates.setLongitude(lon);
        return coordinates;
    }

    private Picture createPicture(String url, String photoReference) {
        Picture picture = new Picture();
        picture.setUrl(url);
        picture.setPhotoReference(photoReference);
        return picture;
    }
}
