package com.chifanhero.api.configs;

import com.chifanhero.api.configs.annotation.Cfg;
import com.chifanhero.api.configs.helper.ConfigHelper;

@Cfg(prefix = "CHIFANHERO_")
public class ChifanheroConfigs {

    public final static String MONGO_URI;
    public final static String MONGO_DATABASE;
    public final static String MONGO_COLLECTION_RESTAURANT;
    public final static String MONGO_COLLECTION_USERINFO;

    static {
        String prefix = ConfigHelper.getPrefix(ChifanheroConfigs.class);

        MONGO_URI = ConfigHelper.getProperty(prefix + "MONGO_URI", "mongodb://readwrite:readwrite@ec2-34-212-245-174.us-west-2.compute.amazonaws.com:27017/admin?authSource=admin");
        MONGO_DATABASE = ConfigHelper.getProperty(prefix + "MONGO_DATABASE", "chifanhero");
        MONGO_COLLECTION_RESTAURANT = ConfigHelper.getProperty(prefix + "MONGO_COLLECTION_RESTAURANT", "Restaurant");
        MONGO_COLLECTION_USERINFO = ConfigHelper.getProperty(prefix + "MONGO_COLLECTION_USERINFO", "UserInfo");
    }

}
