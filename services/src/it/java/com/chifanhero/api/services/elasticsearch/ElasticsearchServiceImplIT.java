package com.chifanhero.api.services.elasticsearch;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.chifanhero.api.services.elasticsearch.client.ElasticsearchRestClient;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

//@Ignore
public class ElasticsearchServiceImplIT {

    private static AsyncHttpClient HTTP_CLIENT;
    private static ElasticsearchRestClient ELASTIC_REST_CLIENT;
    private final static List<String> DOCUMENT_IDS = new ArrayList<>();
    private final static String MAPPING = "{\n" +
            "    \"properties\" : {\n" +
            "            \"coordinates\" : {\"type\" : \"geo_point\", \"store\": \"true\"},\n" +
            "            \"_created_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"},\n" +
            "            \"name\" : {\"type\" : \"string\", \"analyzer\": \"smartcn\", \"store\" : \"true\"},\n" +
            "            \"google_name\" : {\"type\" : \"string\", \"store\" : \"true\"},\n" +
            "            \"rating\": {\"type\" : \"double\", \"store\": \"true\"},\n" +
            "            \"google_place_id\": {\"type\" : \"string\", \"index\" : \"not_analyzed\", \"store\": \"true\"},\n" +
            "            \"blacklisted\":{\"type\":\"boolean\", \"index\" : \"not_analyzed\", \"store\": \"true\"},\n" +
            "            \"_updated_at\" : {\"type\" : \"date\", \"format\" : \"date_time\", \"index\" : \"no\"},\n" +
            "            \"on_hold\" : {\"type\" : \"boolean\", \"index\" : \"not_analyzed\", \"store\": \"true\"}\n" +
            "        }\n" +
            "}";
    private final static String SETTINGS = "{\n" +
            "    \"settings\" : {\n" +
            "        \"index\" : {\n" +
            "            \"number_of_shards\" : 3, \n" +
            "            \"number_of_replicas\" : 2 \n" +
            "        }\n" +
            "    }\n" +
            "}";
    private final static Double[] BAY_AREA_COORDINATES = {-121.993801, 37.308835};
    private final static Double[] BAY_AREA_COORDINATES2 = {-121.994801, 37.308835};
    private final static Double[] LOS_ANGELES_COORDINATES = {-118.073659, 34.087387};
    private final static Double[] LOS_ANGELES_COORDINATES2 = {-118.074659, 34.087387};

    @BeforeClass
    public static void beforeClass() {
        createAsyncHttpClient();
        createElasticRestClient();
        createIndex();
        putMapping();
        prepareTestData();

    }

    private static void createElasticRestClient() {
        ELASTIC_REST_CLIENT = new ElasticsearchRestClient(HTTP_CLIENT, "localhost", "9200");
    }

    private static void createAsyncHttpClient() {
        HTTP_CLIENT = asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setRequestTimeout(2000));
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
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(ELASTIC_REST_CLIENT);
        nearbySearchRequest.validate();
        RestaurantSearchResponse response = service.nearBySearch(nearbySearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 2);
        response.getResults().forEach(result -> {
            // source, name, google_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getGoogleName());
            Assert.assertNotNull(result.getRating());
            Assert.assertNotNull(result.getPlaceId());
            Assert.assertNotNull(result.getCoordinates());
        });
        Assert.assertEquals("老酒门", response.getResults().get(0).getName());
        Assert.assertEquals("思烤吧", response.getResults().get(1).getName());
    }

    @Test
    public void testNearbySearchRating() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        Location location = new Location();
        location.setLat(LOS_ANGELES_COORDINATES[1]);
        location.setLon(LOS_ANGELES_COORDINATES[0]);
        nearbySearchRequest.setLocation(location);
        nearbySearchRequest.setRadius(2000);
        nearbySearchRequest.setSortOrder(SortOrder.RATING.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(ELASTIC_REST_CLIENT);
        nearbySearchRequest.validate();
        RestaurantSearchResponse response = service.nearBySearch(nearbySearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 2);
        response.getResults().forEach(result -> {
            // source, name, google_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getGoogleName());
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
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(ELASTIC_REST_CLIENT);
        textSearchRequest.validate();
        RestaurantSearchResponse response = service.textSearch(textSearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 1);
        Restaurant restaurant = response.getResults().get(0);
        // source, name, google_name, rating, coordinates, google_place_id
        Assert.assertEquals(Source.CHIFANHERO, restaurant.getSource());
        Assert.assertEquals("韶山印象", restaurant.getName());
        Assert.assertEquals("Hunan Impression", restaurant.getGoogleName());
        Assert.assertTrue(3.5 == restaurant.getRating());
        Assert.assertEquals("googleplaceid", restaurant.getPlaceId());
        Coordinates coordinates = restaurant.getCoordinates();
        Assert.assertTrue(BAY_AREA_COORDINATES[1] == coordinates.getLatitude().doubleValue());
        Assert.assertTrue(BAY_AREA_COORDINATES[0] == coordinates.getLongitude().doubleValue());

    }

    @Test
    public void testTextSearchRating() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("巴蜀风");
        Location location = new Location();
        location.setLat(BAY_AREA_COORDINATES[1]);
        location.setLon(BAY_AREA_COORDINATES[0]);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRadius(2000);
        textSearchRequest.setSortOrder(SortOrder.RATING.name());
        ElasticsearchServiceImpl service = new ElasticsearchServiceImpl(ELASTIC_REST_CLIENT);
        textSearchRequest.validate();
        RestaurantSearchResponse response = service.textSearch(textSearchRequest);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getResults().size() == 1);
        response.getResults().forEach(result -> {
            // source, name, google_name, rating, coordinates, google_place_id
            Assert.assertEquals(Source.CHIFANHERO, result.getSource());
            Assert.assertNotNull(result.getName());
            Assert.assertNotNull(result.getGoogleName());
            Assert.assertNotNull(result.getRating());
            Assert.assertNotNull(result.getPlaceId());
            Assert.assertNotNull(result.getCoordinates());
        });
        Assert.assertEquals("巴蜀风", response.getResults().get(0).getName());
    }

    private static void putMapping() {
        ELASTIC_REST_CLIENT.createMapping(ElasticsearchServiceImpl.INDEX, ElasticsearchServiceImpl.TYPE, MAPPING);
    }

    private static void createIndex() {
        ELASTIC_REST_CLIENT.createIndex(ElasticsearchServiceImpl.INDEX, SETTINGS);
    }

    private static void prepareTestData() {
        List<XContentBuilder> documents = new ArrayList<>();
        XContentBuilder document1 = createDocument("韶山印象", "Hunan Impression", 3.5, BAY_AREA_COORDINATES, "googleplaceid", false);
        XContentBuilder document2 = createDocument("巴蜀风", "Szechuan Chili", 5.0, BAY_AREA_COORDINATES2, "googleplaceid", false);
        XContentBuilder document3 = createDocument("巴蜀风2", "Szechuan Chili", 4.0, BAY_AREA_COORDINATES2, "googleplaceid", true);
        XContentBuilder document4 = createDocument("老酒门", "Lao Jiu Men", 3.5, LOS_ANGELES_COORDINATES, "googleplaceid", false);
        XContentBuilder document5 = createDocument("思烤吧", "Si Kao Ba", 5.0, LOS_ANGELES_COORDINATES2, "googleplaceid", false);
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);
        documents.add(document5);
        documents.forEach(document -> {
            String documentId = IdGenerator.getNewObjectId();
            DOCUMENT_IDS.add(documentId);
            try {
                ELASTIC_REST_CLIENT.indexDocument(ElasticsearchServiceImpl.INDEX, ElasticsearchServiceImpl.TYPE, documentId, document.string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static XContentBuilder createDocument(String name, String englishName, Double rating, Double[] geoPoint, String googlePlaceId, boolean onHold) {
        try {
            return jsonBuilder()
                    .startObject()
                    .field(FieldNames.NAME, name)
                    .field(FieldNames.GOOGLE_NAME, englishName)
                    .field(FieldNames.RATING, rating)
                    .field(FieldNames.COORDINATES, geoPoint)
                    .field(FieldNames.GOOGLE_PLACE_ID, googlePlaceId)
                    .field(FieldNames.ON_HOLD, onHold)
                    .endObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void afterClass() {
    }
}

