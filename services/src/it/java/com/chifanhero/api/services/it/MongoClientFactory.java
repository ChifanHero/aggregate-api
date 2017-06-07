package com.chifanhero.api.services.it;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Created by shiyan on 6/6/17.
 */
public class MongoClientFactory {

    public static MongoClient createMongoClient() {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
        return new MongoClient(uri);
    }
}
