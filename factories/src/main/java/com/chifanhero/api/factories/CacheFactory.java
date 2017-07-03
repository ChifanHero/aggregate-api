package com.chifanhero.api.factories;

import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by shiyan on 5/5/17.
 */
@Configuration
public class CacheFactory {

    @Bean
    public Cache<String, RestaurantSearchResponse> buildCache() {
        return CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1, TimeUnit.DAYS).build();
    }
}
