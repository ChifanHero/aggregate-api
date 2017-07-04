package com.chifanhero.api.factories;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * Created by shiyan on 6/24/17.
 */
@Configuration
public class ListenableExecutorServiceFactory {

    @Bean(name = "listenableExecutorService")
    public ListeningExecutorService createListenableExecutorService() {
        return MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    }
}
