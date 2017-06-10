package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.services.it.MongoClientFactory;
import com.google.common.collect.ImmutableMap;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
            Assert.assertEquals(PLACEID_NAME_INSERT1.get(entry.getKey()), entry.getValue().getName());
        }
        List<Restaurant> newRestaurants = createResults(PLACEID_NAME_INSERT2);
        service.bulkUpsert(newRestaurants);
        Map<String, Restaurant> newReturned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        Assert.assertEquals(PLACEID_NAME_INSERT2.keySet().size(), newReturned.size());
        for (Map.Entry<String, Restaurant> entry : newReturned.entrySet()) {
            Assert.assertEquals(PLACEID_NAME_INSERT2.get(entry.getKey()), entry.getValue().getName());
        }
    }

    @Test
    public void testExpirationDate() {
        String googlePlaceId = "1";
        cleanup();
        assert service.batchGetByGooglePlaceId(Collections.singletonList(googlePlaceId)).size() == 0;
        Restaurant restaurant = createResult("name", googlePlaceId);
        service.bulkUpsert(Collections.singletonList(restaurant));
        mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE).getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT)
                .find(Filters.eq(KeyNames.GOOGLE_PLACE_ID, googlePlaceId)).forEach((Block<? super Document>) document -> {
            Assert.assertNotNull(document.getDate(KeyNames.EXPIRE_AT));
        });
    }

    private List<Restaurant> createResults(ImmutableMap<String, String> placeidName) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (Map.Entry<String, String> entry : placeidName.entrySet()) {
            restaurants.add(createResult(entry.getValue(), entry.getKey()));
        }
        return restaurants;
    }

    private Restaurant createResult(String name, String googlePlaceId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId(googlePlaceId);
        restaurant.setName(name);
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
