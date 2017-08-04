package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.common.exceptions.ChifanheroException;
import com.chifanhero.api.configs.ChifanheroConfigs;
import com.chifanhero.api.models.response.Coordinates;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.UserInfo;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.chifanhero.api.services.it.MongoClientFactory;
import com.chifanhero.api.utils.DateUtil;
import com.google.common.collect.ImmutableMap;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ChifanheroRestaurantServiceImplIT {

    private static MongoClient mongoClient;
    private static ChifanheroRestaurantServiceImpl service;
    private final static ImmutableMap<String, String> PLACEID_NAME_INSERT1 = new ImmutableMap.Builder<String, String>()
            .put("place_id1", "name1")
            .put("place_id2", "name2")
            .put("place_id3", "name3")
            .build();
    private final static ImmutableMap<String, String> PLACEID_NAME_INSERT2 = new ImmutableMap.Builder<String, String>()
            .put("place_id1", "new_name1")
            .put("place_id2", "new_name2")
            .put("place_id3", "new_name3")
            .build();

    @BeforeClass
    public static void prepare() {
        mongoClient = MongoClientFactory.createMongoClient();
        service = new ChifanheroRestaurantServiceImpl(mongoClient, null);
    }

    @Test
    public void test() throws InterruptedException {
        cleanup();
        assert service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet())).size() == 0;
        List<Restaurant> restaurants = createResults(PLACEID_NAME_INSERT1);
        service.bulkUpsert(restaurants);
        Map<String, Restaurant> returned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        Assert.assertEquals(PLACEID_NAME_INSERT1.keySet().size(), returned.size());
        for (Map.Entry<String, Restaurant> entry : returned.entrySet()) {
            Assert.assertEquals(PLACEID_NAME_INSERT1.get(entry.getKey()), entry.getValue().getGoogleName());
        }
        List<Restaurant> newRestaurants = createResults(PLACEID_NAME_INSERT2);
        service.bulkUpsert(newRestaurants);
        Map<String, Restaurant> newReturned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT2.keySet()));
        Assert.assertEquals(PLACEID_NAME_INSERT2.keySet().size(), newReturned.size());
        for (Map.Entry<String, Restaurant> entry : newReturned.entrySet()) {
            Assert.assertEquals(PLACEID_NAME_INSERT2.get(entry.getKey()), entry.getValue().getGoogleName());
        }
    }

    @Test
    public void testSetOnInsert() {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId("testSetOnInsert");
        restaurant.setId("123456");
        restaurant.setRating(4.5);
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        service.bulkUpsert(restaurants);
        restaurant = new Restaurant();
        restaurant.setPlaceId("testSetOnInsert");
        restaurant.setId("1234567");
        restaurant.setRating(3.5);
        restaurants = Collections.singletonList(restaurant);
        service.bulkUpsert(restaurants);
        Map<String, Restaurant> results = service.batchGetByGooglePlaceId(Collections.singletonList("testSetOnInsert"));
        Restaurant result = results.get("testSetOnInsert");
        Assert.assertTrue(result.getRating() == 4.5);
        Assert.assertEquals("123456", result.getId());
    }

    @Test
    public void testExpirationDateSetCorrectly() {
        String googlePlaceId = "1";
        cleanup();
        assert service.batchGetByGooglePlaceId(Collections.singletonList(googlePlaceId)).size() == 0;
        Restaurant restaurant = createResult("name", googlePlaceId);
        service.bulkUpsert(Collections.singletonList(restaurant));
        mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE).getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT)
                .find(Filters.eq(KeyNames.GOOGLE_PLACE_ID, googlePlaceId)).forEach((Block<? super Document>) document -> {
            Date expirationDate = document.getDate(KeyNames.EXPIRE_AT);
            Assert.assertNotNull(expirationDate);
            long diff = expirationDate.getTime() - new Date().getTime();
            Assert.assertTrue(diff < 1209600000);
            Assert.assertTrue(diff > (1209600000) - 10000);
        });
    }

    @Test
    public void testDataShouldExpire() {
        String placeId = "testDataShouldExpire";
        Restaurant toExpire = new Restaurant();
        toExpire.setPlaceId(placeId);
        toExpire.setId("testDataShouldExpire");
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(37.308835);
        coordinates.setLongitude(-121.99);
        toExpire.setCoordinates(coordinates);
        toExpire.setGoogleName("googlename1");
        service.bulkUpsert(Collections.singletonList(toExpire), new Date());
        service.expireData();
        Map<String, Restaurant> restaurants = service.batchGetByGooglePlaceId(Collections.singletonList(placeId));
        Assert.assertNotNull(restaurants);
        Assert.assertTrue(restaurants.size() == 1);
        Restaurant restaurant = restaurants.get(placeId);
        Assert.assertEquals(placeId, restaurant.getPlaceId());
        Assert.assertNull(restaurant.getGoogleName());
        Assert.assertNull(restaurant.getCoordinates());
    }

    @Test
    public void testDataShouldNotExpire() {
        String placeId = "testDataShouldNotExpire";
        String name = "name2";
        String googleName2 = "googlename2";
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(37.308835);
        coordinates.setLongitude(-121.99);
        Restaurant notToExpire = new Restaurant();
        notToExpire.setPlaceId(placeId);
        notToExpire.setId("testDataShouldNotExpire");
        notToExpire.setName(name);
        notToExpire.setCoordinates(coordinates);
        notToExpire.setGoogleName(googleName2);
        service.bulkUpsert(Collections.singletonList(notToExpire), DateUtil.addDays(new Date(), 10));
        service.expireData();
        Map<String, Restaurant> restaurants = service.batchGetByGooglePlaceId(Collections.singletonList(placeId));
        Assert.assertNotNull(restaurants);
        Assert.assertTrue(restaurants.size() == 1);
        Restaurant restaurant = restaurants.get(placeId);
        Assert.assertEquals(placeId, restaurant.getPlaceId());
        Assert.assertEquals(googleName2, restaurant.getGoogleName());
        Assert.assertNotNull(restaurant.getCoordinates());
    }

    @Test
    public void testMarkRecommendation() {
        String placeId = "testMarkRecommendation";
        String googleName1 = "googlename1";
        Restaurant restaurant = new Restaurant();
        restaurant.setId("testMarkRecommendation");
        restaurant.setPlaceId(placeId);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(37.308835);
        coordinates.setLongitude(-121.99);
        restaurant.setCoordinates(coordinates);
        restaurant.setGoogleName(googleName1);
        service.bulkUpsert(Collections.singletonList(restaurant), new Date());
        restaurant.setRating(4.5);
        service.markRecommendations(Collections.singletonList(restaurant));
        Map<String, Restaurant> restaurants = service.batchGetByGooglePlaceId(Collections.singletonList(placeId));
        Assert.assertTrue(restaurants.size() == 1);
        Restaurant rest = restaurants.get(placeId);
        Assert.assertEquals(placeId, rest.getPlaceId());
        Assert.assertEquals(googleName1, rest.getGoogleName());
        Assert.assertNotNull(rest.getCoordinates());
        Assert.assertEquals(true, rest.getRecommendationCandidate());
    }

    @Test
    public void testCreateNewUser() throws ChifanheroException {
        String id = "testCreateNewUser" + System.currentTimeMillis();
        UserInfo newUser = service.createNewUser(id);
        Assert.assertEquals(id, newUser.getUserId());
    }

    @Test(expected = ChifanheroException.class)
    public void testCreateNewUserExisting() throws ChifanheroException {
        String id = "testCreateNewUserExisting" + System.currentTimeMillis();
        service.createNewUser(id);
        service.createNewUser(id);
    }

    @Test
    public void testTrackViewCount() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("testTrackViewCount");
        restaurant.setPlaceId("testTrackViewCount");
        service.bulkUpsert(Collections.singletonList(restaurant));
        service.trackViewCount("testTrackViewCount", "uid1");
        service.trackViewCount("testTrackViewCount", "uid2");
        service.trackViewCount("testTrackViewCount", "uid3");
        service.trackViewCount("testTrackViewCount", "uid3");
        Document document = getRestaurant("testTrackViewCount");
        Assert.assertEquals(4, document.getInteger(KeyNames.VIEW_COUNT).intValue());
        List views = document.get(KeyNames.VIEWS_N, List.class);
        Assert.assertEquals(3, views.size());
    }

    @Test
    public void testTryPublishRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("testTryPublishRestaurant");
        restaurant.setPlaceId("testTryPublishRestaurant");
        restaurant.setOnHold(true);
        service.bulkUpsert(Collections.singletonList(restaurant));
        Document restaurantDocument = getRestaurant("testTryPublishRestaurant");
        assert restaurantDocument.getBoolean(KeyNames.ON_HOLD);
        service.trackViewCount("testTryPublishRestaurant", "uid1");
        service.trackViewCount("testTryPublishRestaurant", "uid2");
        service.trackViewCount("testTryPublishRestaurant", "uid3");
        service.tryPublishRestaurant("testTryPublishRestaurant");
        restaurantDocument = getRestaurant("testTryPublishRestaurant");
        Assert.assertFalse(restaurantDocument.getBoolean(KeyNames.ON_HOLD));
    }

    private Document getRestaurant(String id) {
        MongoCollection<Document> restaurantCollection =
                mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE)
                        .getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT);
        Bson filter = Filters.eq(KeyNames.ID, id);
        FindIterable<Document> documents = restaurantCollection.find(filter);
        return documents.first();
    }

    private List<Restaurant> createResults(ImmutableMap<String, String> placeidName) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (Map.Entry<String, String> entry : placeidName.entrySet()) {
            restaurants.add(createResult(entry.getValue(), entry.getKey()));
        }
        return restaurants;
    }

    private Restaurant createResult(String googleName, String googlePlaceId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId(googlePlaceId);
        restaurant.setGoogleName(googleName);
        restaurant.setId(IdGenerator.getNewObjectId());
        return restaurant;
    }

    @AfterClass
    public static void cleanUp() throws InterruptedException {
        cleanup();
        Map<String, Restaurant> returned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        assert returned.size() == 0;
        mongoClient.close();
    }

    private static void cleanup() {
        List<DeleteOneModel<Document>> deletes = PLACEID_NAME_INSERT1.keySet().stream().map(placeId -> {
            Bson filter = Filters.eq(KeyNames.GOOGLE_PLACE_ID, placeId);
            return new DeleteOneModel<Document>(filter);
        }).collect(Collectors.toList());
        mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE).getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT).bulkWrite(deletes);
    }
}
