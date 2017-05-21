//package com.chifanhero.api.services.elasticsearch;
//
//import com.chifanhero.api.configs.ElasticConfigs;
//import javafx.scene.NodeBuilder;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.node.NodeValidationException;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
///**
// * Created by shiyan on 5/22/17.
// */
//public class ElasticsearchServiceImplTest {
//
//    private static Node node;
//    private static Client client;
//    private final static String MAPPING = "{\n" +
//            "    \"properties\" : {\n" +
//            "            \"coordinates\" : {\"type\" : \"geo_point\", \"store\": \"true\"},\n" +
//            "            \"_created_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"},\n" +
//            "            \"name\" : {\"type\" : \"string\", \"analyzer\": \"smartcn\", \"store\" : \"true\"},\n" +
//            "            \"english_name\" : {\"type\" : \"string\", \"store\" : \"true\"},\n" +
//            "            \"rating\": {\"type\" : \"double\", \"store\": \"true\"},\n" +
//            "            \"google_place_id\": {\"type\" : \"string\", \"index\" : \"not_analyzed\", \"store\": \"true\"},\n" +
//            "            \"_updated_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"}\n" +
//            "        }\n" +
//            "}";
//
//    @BeforeClass
//    public static void beforeClass() throws NodeValidationException {
//        node = createNode();
//        client = node.client();
//        createIndex();
//        putMapping();
//    }
//
//    private static void putMapping() {
//        client.admin().indices().preparePutMapping(ElasticsearchServiceImpl.INDEX)
//                .setType(ElasticsearchServiceImpl.TYPE)
//                .setSource(MAPPING, XContentType.JSON)
//                .get();
//    }
//
//    private static void createIndex() {
//        client.admin().indices().prepareCreate(ElasticsearchServiceImpl.INDEX).get();
//    }
//
//    private static Node createNode() throws NodeValidationException {
//
//        Settings.Builder settingsBuilder =
//                Settings.builder();
//
////        settingsBuilder.put("node.name", ElasticSearchConfig.NODE_NAME);
//        settingsBuilder.put("path.home", getCurrentPath());
//        settingsBuilder.put("cluster.name", ElasticConfigs.CLUSTER_NAME);
//        settingsBuilder.put("http.enabled", String.valueOf(false));
//        settingsBuilder.put("local", String.valueOf(false));
//
//        Settings settings = settingsBuilder.build();
//
//        node = new Node(settings);
////        node = NodeBuilder.nodeBuilder()
////                .settings(settings)
////                .clusterName(ElasticSearchConfig.CLUSTER_NAME)
////                .data(true).local(true).node();
////        node = new Node(settings).start();
////        node = new NodeBuiler()
////        node.
//        return node;
//    }
//
//    @Test
//    public void test() {
//        String json = "{" +
//                "\"name\":\"kimchy\"" +
//                "}";
//        client.prepareIndex(ElasticsearchServiceImpl.INDEX, ElasticsearchServiceImpl.TYPE).setSource(json, XContentType.JSON).get();
//    }
//
//    private static String getCurrentPath() {
//        return ElasticsearchServiceImplTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//    }
//
//    @AfterClass
//    public static void afterClass() {
//
//    }
//}
