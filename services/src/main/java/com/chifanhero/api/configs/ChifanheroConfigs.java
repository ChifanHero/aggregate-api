package com.chifanhero.api.configs;

import com.chifanhero.api.common.annotations.Cfg;
import com.chifanhero.api.configs.helper.ConfigHelper;

@Cfg(prefix = "CHIFANHERO_")
public class ChifanheroConfigs {

    public final static String MONGO_URI;
    public final static String MONGO_DATABASE;
    public final static String MONGO_COLLECTION_RESTAURANT;

    static {
        String prefix = ConfigHelper.getPrefix(ChifanheroConfigs.class);

        MONGO_URI = ConfigHelper.getProperty(prefix + "MONGO_URI", "mongodb://readwrite:readwrite@chifanhero-shard-00-00-qfihy.mongodb.net:27017,chifanhero-shard-00-01-qfihy.mongodb.net:27017,chifanhero-shard-00-02-qfihy.mongodb.net:27017/admin?ssl=true&replicaSet=chifanhero-shard-0&authSource=admin");
        MONGO_DATABASE = ConfigHelper.getProperty(prefix + "MONGO_DATABASE", "chifanhero");
        MONGO_COLLECTION_RESTAURANT = ConfigHelper.getProperty(prefix + "MONGO_COLLECTION_RESTAURANT", "Restaurant");
    }

}
