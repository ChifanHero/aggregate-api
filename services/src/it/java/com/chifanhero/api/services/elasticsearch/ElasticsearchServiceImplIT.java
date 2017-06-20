package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.configs.ElasticConfigs;
import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
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
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

//@Ignore
public class ElasticsearchServiceImplIT {

    protected static final Logger LOGGER = ESLoggerFactory.getLogger(ElasticsearchServiceImplIT.class);
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
    private final static Double[] BAY_AREA_COORDINATES2 = {-121.994801, 37.308835};
    private final static Double[] LOS_ANGELES_COORDINATES = {-118.073659, 34.087387};
    private final static Double[] LOS_ANGELES_COORDINATES2 = {-118.074659, 34.087387};

    @BeforeClass
    public static void beforeClass() {
        createTransportClient();
        createIndex();
        putMapping();
        prepareTestData();

    }

    @Test
    public void testNearbySearchNearest() throws InterruptedException {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(LOS_ANGELES_COORDINATES[1]);
        location.setLon(LOS_ANGELES_COORDINATES[0]);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setRadius(2000);
        nearbySearchRequest.setSortOrder(SortOrder.NEAREST.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(CLIENT);
        nearbySearchRequest.validate();
        RestaurantSearchResponse response = service.nearBySearch(nearbySearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 2);
        response.getResults().forEach(result -> {
            // source, name, english_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getEnglighName());
            Assert.assertNotNull(result.getRating());
            Assert.assertNotNull(result.getPlaceId());
            Assert.assertNotNull(result.getCoordinates());
        });
        Assert.assertEquals("老酒门", response.getResults().get(0).getName());
        Assert.assertEquals("思烤吧", response.getResults().get(1).getName());
    }

    @Test
    public void testNearbySearchHottest() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(LOS_ANGELES_COORDINATES[1]);
        location.setLon(LOS_ANGELES_COORDINATES[0]);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setRadius(2000);
        nearbySearchRequest.setSortOrder(SortOrder.HOTTEST.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(CLIENT);
        nearbySearchRequest.validate();
        RestaurantSearchResponse response = service.nearBySearch(nearbySearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 2);
        response.getResults().forEach(result -> {
            // source, name, english_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getEnglighName());
            Assert.assertNotNull(result.getRating());
            Assert.assertNotNull(result.getPlaceId());
            Assert.assertNotNull(result.getCoordinates());
        });
        Assert.assertEquals("思烤吧", response.getResults().get(0).getName());
        Assert.assertEquals("老酒门", response.getResults().get(1).getName());
    }

    @Test
    public void testTextSearchNearest() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("韶山");
        Location location = new Location();
        location.setLat(BAY_AREA_COORDINATES[1]);
        location.setLon(BAY_AREA_COORDINATES[0]);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRadius(2000);
        textSearchRequest.setSortOrder(SortOrder.NEAREST.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(CLIENT);
        textSearchRequest.validate();
        RestaurantSearchResponse response = service.textSearch(textSearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 1);
        Restaurant restaurant = response.getResults().get(0);
        // source, name, english_name, rating, coordinates, google_place_id
        Assert.assertEquals(Source.CHIFANHERO, restaurant.getSource());
        Assert.assertEquals("韶山印象", restaurant.getName());
        Assert.assertEquals("Hunan Impression", restaurant.getEnglighName());
        Assert.assertTrue(3.5 == restaurant.getRating());
        Assert.assertEquals("googleplaceid", restaurant.getPlaceId());
        Coordinates coordinates = restaurant.getCoordinates();
        Assert.assertTrue(BAY_AREA_COORDINATES[1] == coordinates.getLatitude().doubleValue());
        Assert.assertTrue(BAY_AREA_COORDINATES[0] == coordinates.getLongitude().doubleValue());

    }

    @Test
    public void testTextSearchHottest() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("巴蜀风");
        Location location = new Location();
        location.setLat(BAY_AREA_COORDINATES[1]);
        location.setLon(BAY_AREA_COORDINATES[0]);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRadius(2000);
        textSearchRequest.setSortOrder(SortOrder.HOTTEST.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(CLIENT);
        textSearchRequest.validate();
        RestaurantSearchResponse response = service.textSearch(textSearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 2);
        response.getResults().forEach(result -> {
            // source, name, english_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getEnglighName());
            Assert.assertNotNull(result.getRating());
            Assert.assertNotNull(result.getPlaceId());
            Assert.assertNotNull(result.getCoordinates());
        });
        Assert.assertEquals("巴蜀风", response.getResults().get(0).getName());
        Assert.assertEquals("巴蜀风2", response.getResults().get(1).getName());
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
        XContentBuilder document1 = createDocument("韶山印象", "Hunan Impression", 3.5, BAY_AREA_COORDINATES, "googleplaceid");
        XContentBuilder document2 = createDocument("巴蜀风", "Szechuan Chili", 5.0, BAY_AREA_COORDINATES2, "googleplaceid");
        XContentBuilder document3 = createDocument("巴蜀风2", "Szechuan Chili", 4.0, BAY_AREA_COORDINATES2, "googleplaceid");
        XContentBuilder document4 = createDocument("老酒门", "Lao Jiu Men", 3.5, LOS_ANGELES_COORDINATES, "googleplaceid");
        XContentBuilder document5 = createDocument("思烤吧", "Si Kao Ba", 5.0, LOS_ANGELES_COORDINATES2, "googleplaceid");
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);
        documents.add(document5);
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static XContentBuilder createDocument(String name, String englishName, Double rating, Double[] geoPoint, String googlePlaceId) {
        try {
            return jsonBuilder()
                    .startObject()
                    .field(FieldNames.NAME, name)
                    .field(FieldNames.ENGLISH_NAME, englishName)
                    .field(FieldNames.RATING, rating)
                    .field(FieldNames.COORDINATES, geoPoint)
                    .field(FieldNames.GOOGLE_PLACE_ID, googlePlaceId)
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
