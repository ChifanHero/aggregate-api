package com.chifanhero.api.controllers;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shiyan on 7/3/17.
 */
@RestController
public class CacheController {

    private final Cache<String, RestaurantSearchResponse> cache;

    @Autowired
    public CacheController(Cache<String, RestaurantSearchResponse> cache) {
        this.cache = cache;
    }

    @RequestMapping("/cache")
    public long check() {
        return cache.size();
    }
}
