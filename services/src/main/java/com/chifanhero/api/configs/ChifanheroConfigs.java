package com.chifanhero.api.configs;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shiyan on 5/13/17.
 */
public class ChifanheroConfigs {

    public final static List<String> MONGO_URLS = Arrays.asList(
            "chifanhero-shard-00-00-qfihy.mongodb.net",
            "chifanhero-shard-00-01-qfihy.mongodb.net",
            "chifanhero-shard-00-02-qfihy.mongodb.net"
    );

    public final static int PORT = 27017;
    public final static String USER_NAME = "readwrite";
    public final static String PASSWORD = "readwrite";
    public final static String DATABASE = "chifanhero";
    public final static String COLLECTION_RESTAURANT = "Restaurant";
    public final static String AUTH_SOURCE = "admin";


    public static List<String> getMongoUrls() {
        return MONGO_URLS;
    }

    public static int getPORT() {
        return PORT;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getDATABASE() {
        return DATABASE;
    }

    public static String getCollectionRestaurant() {
        return COLLECTION_RESTAURANT;
    }

    public static String getAuthSource() {
        return AUTH_SOURCE;
    }
}
