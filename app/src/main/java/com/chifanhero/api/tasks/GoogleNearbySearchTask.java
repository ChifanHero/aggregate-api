package com.chifanhero.api.tasks;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.services.google.GooglePlacesService;
import com.chifanhero.api.services.google.client.request.converters.CoordinatesConverter;
import com.chifanhero.api.utils.GeoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by shiyan on 6/25/17.
 */
public class GoogleNearbySearchTask implements Callable<RestaurantSearchResponse> {

    private NearbySearchRequest nearbySearchRequest;
    private final GooglePlacesService googlePlacesService;

    public GoogleNearbySearchTask(NearbySearchRequest nearbySearchRequest, GooglePlacesService googlePlacesService) {
        this.nearbySearchRequest = nearbySearchRequest;
        this.googlePlacesService = googlePlacesService;
    }

    @Override
    public RestaurantSearchResponse call() throws Exception {
        if (nearbySearchRequest.getRadius() != null && nearbySearchRequest.getRadius() >= 2000) {
            double[][] coordinatesGroup = GeoUtil.getCoordinatesGroup(nearbySearchRequest.getLocation().getLat(), nearbySearchRequest.getLocation().getLon(), 2.0);
            List<Location> locations = new ArrayList<>();
            for (double[] coordinates : coordinatesGroup) {
                Location location = new Location();
                location.setLat(coordinates[0]);
                location.setLon(coordinates[1]);
                locations.add(location);
            }
            return googlePlacesService.nearBySearch(nearbySearchRequest, locations);
//            if (restaurantSearchResponse != null && (restaurantSearchResponse.getResults() == null || restaurantSearchResponse.getResults().isEmpty())) {
//                nearbySearchRequest.setKeyword("chinese+food");
//                RestaurantSearchResponse backupResponse = googlePlacesService.nearBySearch(nearbySearchRequest, locations);
//                markBackupResponse(backupResponse);
//                return backupResponse;
//            } else {
//                return restaurantSearchResponse;
//            }
        } else {
            return googlePlacesService.nearBySearch(nearbySearchRequest);
//            if (restaurantSearchResponse != null && (restaurantSearchResponse.getResults() == null || restaurantSearchResponse.getResults().isEmpty())) {
//                nearbySearchRequest.setKeyword("chinese+food");
//                RestaurantSearchResponse backupResponse = googlePlacesService.nearBySearch(nearbySearchRequest);
//                markBackupResponse(backupResponse);
//                return backupResponse;
//            } else {
//                return restaurantSearchResponse;
//            }
        }

    }

    private void markBackupResponse(RestaurantSearchResponse backupResponse) {
        Optional.ofNullable(backupResponse).map(RestaurantSearchResponse::getResults).ifPresent(restaurants -> restaurants.forEach(restaurant -> restaurant.setOnHold(true)));
    }
}
