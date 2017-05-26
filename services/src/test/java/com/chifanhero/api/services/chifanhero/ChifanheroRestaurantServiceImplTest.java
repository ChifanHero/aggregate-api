package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.chifanhero.api.models.response.Result;
import com.google.common.collect.ImmutableMap;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChifanheroRestaurantServiceImplTest {

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
        MongoClientURI uri = new MongoClientURI(
                "mongodb://readwrite:readwrite@chifanhero-shard-00-00-qfihy.mongodb.net:27017,chifanhero-shard-00-01-qfihy.mongodb.net:27017,chifanhero-shard-00-02-qfihy.mongodb.net:27017/admin?ssl=true&replicaSet=chifanhero-shard-0&authSource=admin");
        mongoClient =  new MongoClient(uri);
        service = new ChifanheroRestaurantServiceImpl(mongoClient, null);
    }

    @Test
    public void test() throws InterruptedException {
        cleanup();
        assert service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet())).size() == 0;
        List<Result> results = createResults(PLACEID_NAME_INSERT1);
        service.bulkUpsert(results);
        Map<String, Result> returned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        Assert.assertEquals(PLACEID_NAME_INSERT1.keySet().size(), returned.size());
        for (Map.Entry<String, Result> entry : returned.entrySet()) {
            Assert.assertEquals(PLACEID_NAME_INSERT1.get(entry.getKey()), entry.getValue().getName());
        }
        List<Result> newResults = createResults(PLACEID_NAME_INSERT2);
        service.bulkUpsert(newResults);
        Map<String, Result> newReturned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        Assert.assertEquals(PLACEID_NAME_INSERT2.keySet().size(), newReturned.size());
        for (Map.Entry<String, Result> entry : newReturned.entrySet()) {
            Assert.assertEquals(PLACEID_NAME_INSERT2.get(entry.getKey()), entry.getValue().getName());
        }
    }

    private List<Result> createResults(ImmutableMap<String, String> placeidName) {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<String, String> entry : placeidName.entrySet()) {
            results.add(createResult(entry.getValue(), entry.getKey()));
        }
        return results;
    }

    private Result createResult(String name, String googlePlaceId) {
        Result result = new Result();
        result.setPlaceId(googlePlaceId);
        result.setName(name);
        return result;
    }

    @AfterClass
    public static void cleanUp() throws InterruptedException {
        cleanup();
        Map<String, Result> returned = service.batchGetByGooglePlaceId(new ArrayList<>(PLACEID_NAME_INSERT1.keySet()));
        assert returned.size() == 0;
    }

    private static void cleanup() {
        List<DeleteOneModel<Document>> deletes = PLACEID_NAME_INSERT1.keySet().stream().map(placeId -> {
            Bson filter = Filters.eq(KeyNames.GOOGLE_PLACE_ID, placeId);
            return new DeleteOneModel<Document>(filter);
        }).collect(Collectors.toList());
        mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE).getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT).bulkWrite(deletes);
    }
}
