package com.chifanhero.api.controllers;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RestaurantsController {

    // http://localhost:8080/nearBy?radius=500&location.lat=123.4&location.lon=234.5&rating=4.5
    @RequestMapping("/nearBy")
    public RestaurantSearchResponse nearBySearch(NearbySearchRequest searchRequest, HttpServletResponse response) {
        return new RestaurantSearchResponse();
    }
}
