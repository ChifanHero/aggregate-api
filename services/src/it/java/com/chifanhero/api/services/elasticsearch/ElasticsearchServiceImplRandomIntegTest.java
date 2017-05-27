package com.chifanhero.api.services.elasticsearch;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import com.chifanhero.api.configs.ElasticConfigs;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shiyan on 5/26/17.
 */
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
public class ElasticsearchServiceImplRandomIntegTest extends ESTestCase {

    protected static final Logger LOGGER = ESLoggerFactory.getLogger(ElasticsearchServiceImplRandomIntegTest.class);
    protected static TransportClient CLIENT;

    @BeforeClass
    public static void beforeElasticsearchServiceImplIT() {
        createTransportClient();
    }

    private static void createTransportClient() {
        Settings settings = Settings.builder().put("cluster.name", ElasticConfigs.CLUSTER_NAME).build();
        CLIENT = new PreBuiltTransportClient(settings);
        ElasticConfigs.HOSTS.forEach(host -> {
            try {
                CLIENT.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void shutdownTransportClient() {
        CLIENT.close();
    }

    @Test
    public void test() {

    }

    @AfterClass
    public static void afterElasticsearchServiceImplIT() {

    }
}
