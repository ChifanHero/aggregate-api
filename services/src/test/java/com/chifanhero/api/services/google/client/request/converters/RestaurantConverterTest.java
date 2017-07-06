package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.Coordinates;
import com.chifanhero.api.models.google.Geometry;
import com.chifanhero.api.models.google.Photo;
import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.Source;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RestaurantConverterTest {

    @Test
    public void test() {
        Place place = new Place();
        place.setName("Hunan Impression");
        place.setFormattedAddress("5152 Moorpark Ave, San Jose, CA, 95125");
        place.setFormattedPhoneNumber("408-123-4567");
        place.setPlaceId("place-id");
        place.setPermanentlyClosed(true);
        place.setRating(4.5);
        List<Photo> photos = new ArrayList<>();
        photos.add(createPhoto("reference1"));
        photos.add(createPhoto("reference2"));
        place.setPhotos(photos);
        Geometry geometry = new Geometry();
        Coordinates coordinates = new Coordinates();
        coordinates.setLng(-121.33);
        coordinates.setLat(37.45);
        geometry.setLocation(coordinates);
        place.setGeometry(geometry);
        Restaurant restaurant = RestaurantConverter.toRestaurant(place);
        Assert.assertEquals(Source.GOOGLE, restaurant.getSource());
        Assert.assertEquals("Hunan Impression", restaurant.getEnglighName());
        Assert.assertEquals("5152 Moorpark Ave, San Jose, CA, 95125", restaurant.getAddress());
        Assert.assertEquals("408-123-4567", restaurant.getPhone());
        Assert.assertEquals("place-id", restaurant.getPlaceId());
        Assert.assertTrue(restaurant.getRating() == 4.5);
        com.chifanhero.api.models.response.Coordinates coordinates1 = restaurant.getCoordinates();
        Assert.assertEquals(new Double(-121.33), coordinates1.getLongitude());
        Assert.assertEquals(new Double(37.45), coordinates1.getLatitude());
        Assert.assertEquals(true, restaurant.getPermanentlyClosed());
        Assert.assertEquals("reference1", restaurant.getPicture().getPhotoReference());
    }

    private Photo createPhoto(String reference) {
        Photo photo = new Photo();
        photo.setPhotoReference(reference);
        return photo;
    }
}
