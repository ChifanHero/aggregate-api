package com.chifanhero.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Created by shiyan on 7/3/17.
 */
@Configuration
public class RequestContextListenerFactory {

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
