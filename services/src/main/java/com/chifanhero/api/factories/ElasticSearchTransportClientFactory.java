package com.chifanhero.api.factories;

import com.chifanhero.api.configs.ElasticConfigs;
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
    public TransportClient createTransportClient() {
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

//    public static void main(String[] args) {
//        TransportClient client = createTransportClient();
//        SearchResponse response = client.prepareSearch("chifanhero")
//                .setTypes("Restaurant")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.matchQuery("name", "韶山"))                 // Query
////                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
//                .setFrom(0).setSize(20).setExplain(false)
//                .get();
//        System.out.println(response.getHits());
//    }
}
