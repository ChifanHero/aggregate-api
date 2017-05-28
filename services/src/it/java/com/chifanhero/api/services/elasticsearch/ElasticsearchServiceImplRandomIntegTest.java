package com.chifanhero.api.services.elasticsearch;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import com.chifanhero.api.configs.ElasticConfigs;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by shiyan on 5/26/17.
 */
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
public class ElasticsearchServiceImplRandomIntegTest extends ESTestCase {

    protected static final Logger LOGGER = ESLoggerFactory.getLogger(ElasticsearchServiceImplRandomIntegTest.class);
    protected static TransportClient CLIENT;
    private final static List<String> DOCUMENT_IDS = new ArrayList<>();
    private final static String MAPPING = "{\n" +
            "    \"properties\" : {\n" +
            "            \"coordinates\" : {\"type\" : \"geo_point\", \"store\": \"true\"},\n" +
            "            \"_created_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"},\n" +
            "            \"name\" : {\"type\" : \"string\", \"analyzer\": \"smartcn\", \"store\" : \"true\"},\n" +
            "            \"english_name\" : {\"type\" : \"string\", \"store\" : \"true\"},\n" +
            "            \"rating\": {\"type\" : \"double\", \"store\": \"true\"},\n" +
            "            \"google_place_id\": {\"type\" : \"string\", \"index\" : \"not_analyzed\", \"store\": \"true\"},\n" +
            "            \"_updated_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"}\n" +
            "        }\n" +
            "}";
    private final static Double[] BAY_AREA_COORDINATES = {-121.993801, 37.308835};
    private final static Double[] LOS_ANGELES_COORDINATES = {-118.073659, 34.087387};

    @BeforeClass
    public static void beforeClass() {
        createTransportClient();
        createIndex();
        putMapping();
        prepareTestData();

    }

    private static void putMapping() {
        PutMappingResponse putMappingResponse = CLIENT.admin().indices().preparePutMapping(ElasticsearchServiceImpl.INDEX)
                .setType(ElasticsearchServiceImpl.TYPE)
                .setSource(MAPPING, XContentType.JSON)
                .get();
        assert putMappingResponse.isAcknowledged();
    }

    private static void createIndex() {
        CreateIndexResponse createIndexResponse = CLIENT.admin().indices().prepareCreate(ElasticsearchServiceImpl.INDEX).get();
        assert createIndexResponse.isAcknowledged();
    }

    private static void prepareTestData() {
        BulkRequestBuilder bulkRequest = CLIENT.prepareBulk();
        List<XContentBuilder> documents = new ArrayList<>();
        XContentBuilder document1 = createDocument("韶山印象", "Hunan Impression", 5.0, BAY_AREA_COORDINATES);
        XContentBuilder document2 = createDocument("巴蜀风", "Szechuan Chili", 3.5, BAY_AREA_COORDINATES);
        XContentBuilder document3 = createDocument("老酒门", "Lao Jiu Men", 5.0, LOS_ANGELES_COORDINATES);
        XContentBuilder document4 = createDocument("思烤吧", "Si Kao Ba", 3.5, LOS_ANGELES_COORDINATES);
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);
        documents.forEach(document -> {
            String documentId = IdGenerator.getNewObjectId();
            DOCUMENT_IDS.add(documentId);
            bulkRequest.add(
                    CLIENT.prepareIndex(ElasticsearchServiceImpl.INDEX, ElasticsearchServiceImpl.TYPE, documentId)
                            .setSource(document)
            );
        });
        BulkResponse bulkItemResponses = bulkRequest.get();
        if (bulkItemResponses.hasFailures()) {
            throw new RuntimeException(bulkItemResponses.buildFailureMessage());
        }
    }

    private static XContentBuilder createDocument(String name, String englishName, Double rating, Double[] geoPoint) {
        try {
            return jsonBuilder()
                    .startObject()
                    .field(FieldNames.NAME, name)
                    .field(FieldNames.ENGLISH_NAME, englishName)
                    .field(FieldNames.RATING, rating)
                    .field(FieldNames.COORDINATES, geoPoint)
                    .endObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

//    private static void shutdownTransportClient() {
//        CLIENT.close();
//    }

    @Test
    public void test() {

    }

    @AfterClass
    public static void afterClass() {
//        shutdownTransportClient();
        deleteTestData();
    }

    private static void deleteTestData() {
        BulkRequestBuilder bulkRequest = CLIENT.prepareBulk();
        DOCUMENT_IDS.forEach(id -> bulkRequest.add(CLIENT.prepareDelete(ElasticsearchServiceImpl.INDEX, ElasticsearchServiceImpl.TYPE, id)));
        BulkResponse bulkItemResponses = bulkRequest.get();
        if (bulkItemResponses.hasFailures()) {
            throw new RuntimeException(bulkItemResponses.buildFailureMessage());
        }
    }
}
