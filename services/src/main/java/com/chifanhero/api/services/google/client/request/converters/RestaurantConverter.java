package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.Geometry;
import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.response.Picture;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.Source;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 6/13/17.
 */
public class RestaurantConverter {

    public static Restaurant toRestaurant(Place place) {
        if (place == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(place.getFormattedAddress());
        restaurant.setPhone(place.getFormattedPhoneNumber());
        restaurant.setPlaceId(place.getPlaceId());
        restaurant.setEnglighName(place.getName());
        restaurant.setPermanentlyClosed(place.getPermanentlyClosed());
        restaurant.setSource(Source.GOOGLE);
        Optional.ofNullable(place.getGeometry())
                .map(Geometry::getLocation)
                .ifPresent(location ->
                        restaurant.setCoordinates(CoordinatesConverter.toCommonCoordinates(location))
                );
        Optional.ofNullable(place.getPhotos()).ifPresent(photos -> photos.stream().findFirst().map(PictureConverter::toPicture).ifPresent(restaurant::setPicture));
        return restaurant;
    }
}
