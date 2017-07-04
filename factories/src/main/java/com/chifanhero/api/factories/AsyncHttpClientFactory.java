package com.chifanhero.api.factories;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import static org.asynchttpclient.Dsl.asyncHttpClient;

/**
 * Created by shiyan on 4/29/17.
 */
@Configuration
public class AsyncHttpClientFactory {

    @Bean
    public AsyncHttpClient createAsyncHttpClient() {
        return asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setRequestTimeout(2000));
    }
}
