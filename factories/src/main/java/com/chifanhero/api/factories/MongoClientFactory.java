package com.chifanhero.api.factories;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shiyan on 5/13/17.
 */
@Configuration
public class MongoClientFactory {

    @Bean
    public MongoClient createMongoClient() {
        MongoClientURI uri = new MongoClientURI(ChifanheroConfigs.MONGO_URI);
        return new MongoClient(uri);
    }
}
