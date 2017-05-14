package com.chifanhero.api.factories;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 5/13/17.
 */
@Configuration
public class ChifanheroMongoClientFactory {

    @Bean
    public MongoClient createMongoClient() {
        MongoCredential credential = MongoCredential.createCredential(ChifanheroConfigs.USER_NAME, ChifanheroConfigs.AUTH_SOURCE, ChifanheroConfigs.PASSWORD.toCharArray());
        List<ServerAddress> serverAddresses = ChifanheroConfigs.MONGO_URLS.stream().map(url -> new ServerAddress(url, ChifanheroConfigs.PORT)).collect(Collectors.toList());
        return new MongoClient(serverAddresses, Collections.singletonList(credential));
    }
}
