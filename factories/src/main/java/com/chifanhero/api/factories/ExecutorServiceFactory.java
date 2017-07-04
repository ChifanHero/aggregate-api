package com.chifanhero.api.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shiyan on 4/30/17.
 */
@Configuration
public class ExecutorServiceFactory {

    @Bean(name = "executorService")
    public ExecutorService createExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
