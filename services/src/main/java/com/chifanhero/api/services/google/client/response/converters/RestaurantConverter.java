package com.chifanhero.api.services.google.client.response.converters;

import com.chifanhero.api.models.google.Geometry;
import com.chifanhero.api.models.google.Place;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.google.client.request.converters.CoordinatesConverter;
import com.chifanhero.api.utils.StringUtil;

import java.util.Optional;

/**
 * Created by shiyan on 6/13/17.
 */
public class RestaurantConverter {

    public static Restaurant toRestaurant(Place place) {
        if (place == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(place.getFormattedAddress() == null? place.getVicinity(): place.getFormattedAddress());
        restaurant.setPhone(place.getFormattedPhoneNumber());
        restaurant.setPlaceId(place.getPlaceId());
        restaurant.setGoogleName(StringUtil.removeSpaceBetweenChineseCharacters(place.getName()));
        restaurant.setPermanentlyClosed(place.getPermanentlyClosed());
        restaurant.setSource(Source.GOOGLE);
        restaurant.setRating(place.getRating());
        Optional.ofNullable(place.getGeometry())
                .map(Geometry::getLocation)
                .ifPresent(location ->
                        restaurant.setCoordinates(CoordinatesConverter.toCommonCoordinates(location))
                );
        Optional.ofNullable(place.getPhotos()).ifPresent(photos -> photos.stream().findFirst().map(PictureConverter::toPicture).ifPresent(restaurant::setPicture));
        return restaurant;
    }
}
