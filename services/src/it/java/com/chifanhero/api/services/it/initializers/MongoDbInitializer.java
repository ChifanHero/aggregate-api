package com.chifanhero.api.services.it.initializers;

import com.chifanhero.api.services.it.MongoClientFactory;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created by shiyan on 6/4/17.
 */
public class MongoDbInitializer implements Initializer {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDbInitializer() {
        mongoClient = MongoClientFactory.createMongoClient();
        database = mongoClient.getDatabase("chifanhero");
    }


    @Override
    public void initialize() {
        createCollection();
        mongoClient.close();
    }

    private void createCollection() {
        database.createCollection("Restaurant");
    }
}
