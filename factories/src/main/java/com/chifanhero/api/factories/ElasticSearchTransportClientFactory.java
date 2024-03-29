package com.chifanhero.api.factories;

import com.chifanhero.api.configs.ElasticConfigs;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shiyan on 5/17/17.
 */
@Configuration
public class ElasticSearchTransportClientFactory {

    @Bean
    public static TransportClient createTransportClient() {
        Settings settings = Settings.builder().put("cluster.name", ElasticConfigs.CLUSTER_NAME).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        ElasticConfigs.HOSTS.forEach(host -> {
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host.getHost()), host.getPort()));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        });
        return client;
    }
}
