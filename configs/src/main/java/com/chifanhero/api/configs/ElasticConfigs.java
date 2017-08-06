package com.chifanhero.api.configs;

import com.chifanhero.api.configs.annotation.Cfg;
import com.chifanhero.api.configs.helper.ConfigHelper;
import com.chifanhero.api.configs.type.Host;

import java.util.Collections;
import java.util.List;

/**
 * Created by shiyan on 5/17/17.
 */
@Cfg(prefix = "ELASTIC_")
public class ElasticConfigs {

    public final static List<Host> HOSTS;
    public final static String CLUSTER_NAME;

    static {
        String prefix = ConfigHelper.getPrefix(ElasticConfigs.class);
        HOSTS = ConfigHelper.getPropertyAsList(Host.class, prefix + "HOSTS", Collections.singletonList(new Host("ec2-34-212-245-174.us-west-2.compute.amazonaws.com", 9200)), (String str) -> {
            String[] elements = str.split(":");
            return new Host(elements[0], Integer.valueOf(elements[1]));
        });
        CLUSTER_NAME = ConfigHelper.getProperty(prefix + "CLUSTER_NAME", "chifanhero");
    }
}
