package com.chifanhero.api.factories;

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
    public Cache buildCache() {
        return CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(24, TimeUnit.HOURS).build();
    }
}
