package com.chifanhero.api.factories;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Factory to create CloseableHttpClient bean that will be managed by Spring
 * Created by shiyan on 4/27/17.
 */
@Configuration
public class HttpClientFactory {

    @Bean
    @SuppressWarnings("unused")
    public CloseableHttpClient createCloseableHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
