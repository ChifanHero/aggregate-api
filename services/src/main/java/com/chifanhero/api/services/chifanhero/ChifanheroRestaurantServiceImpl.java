package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.chifanhero.api.models.response.Result;
import com.chifanhero.api.services.chifanhero.document.DocumentConverter;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.google.common.collect.ImmutableMap;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 5/13/17.
 */
@Service
public class ChifanheroRestaurantServiceImpl implements ChifanheroRestaurantService {

    private final MongoClient mongoClient;
    private final ExecutorService executorService;

    @Autowired
    public ChifanheroRestaurantServiceImpl(MongoClient mongoClient, ExecutorService executorService) {
        this.mongoClient = mongoClient;
        this.executorService = executorService;
    }

    @Override
    public void bulkUpsert(List<Result> entities) {
        MongoCollection<Document> collection = getRestaurantCollection();
        List<UpdateOneModel<Document>> upserts = entities.stream().map(entity -> {
            Bson filter = Filters.eq(KeyNames.GOOGLE_PLACE_ID, entity.getPlaceId());
            Document updateDocument = new Document("$setOnInsert", new Document(KeyNames.ID, IdGenerator.getNewObjectId()));
            Document document = DocumentConverter.toDocument(entity);
            updateDocument.append("$set", document);
            //TODO - setOnInsert creation date
            //TODO - set update date
            //TODO - set expiration date (14 days)
            //TODO - update unit tests
            UpdateOptions options = new UpdateOptions().upsert(true);
            assert document != null;
            return new UpdateOneModel<Document>(filter, updateDocument, options);
        }).collect(Collectors.toList());
        collection.bulkWrite(upserts);
    }

    @Override
    public Map<String, Result> batchGetByGooglePlaceId(List<String> googlePlaceIds) {
        ImmutableMap.Builder<String, Result> results = new ImmutableMap.Builder<>();
        MongoCollection<Document> collection = getRestaurantCollection();
        collection.find(Filters.in(KeyNames.GOOGLE_PLACE_ID, googlePlaceIds)).forEach((Block<? super Document>) document -> {
            Result result = DocumentConverter.toResult(document);
            results.put(result.getPlaceId(), result);
        });
        return results.build();
    }

    @Override
    public void bulkUpsertInBackground(List<Result> entities) {
        executorService.submit(() -> bulkUpsert(entities));
    }

    @Override
    public void expireDocuments() {
        // TODO - for expired documents (expiration date <= now), unset english_name/coordinates
    }

    private MongoCollection<Document> getRestaurantCollection() {
        MongoDatabase database = mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE);
        return database.getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT);
    }
}
