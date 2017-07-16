package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.configs.ChifanheroConfigs;
import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.services.chifanhero.document.DocumentConverter;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import com.chifanhero.api.utils.DateUtil;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public ChifanheroRestaurantServiceImpl(MongoClient mongoClient, @Qualifier("executorService") ExecutorService executorService) {
        this.mongoClient = mongoClient;
        this.executorService = executorService;
    }

    @Override
    public void bulkUpsert(List<Restaurant> entities) {
        bulkUpsert(entities, DateUtil.addDays(new Date(), 14));
    }

    protected void bulkUpsert(List<Restaurant> entities, Date expireAt) {
        MongoCollection<Document> collection = getRestaurantCollection();
        List<UpdateOneModel<Document>> upserts = entities.stream().map(entity -> {
            Bson filter = Filters.eq(KeyNames.GOOGLE_PLACE_ID, entity.getPlaceId());
            Document setOnInsertDocument = new Document(KeyNames.ID, entity.getId());
            setOnInsertDocument.append(KeyNames.CREATED_AT, new Date());
            // set rating on insert just to get initial rating
            Optional.ofNullable(entity.getRating()).ifPresent(rating -> setOnInsertDocument.append(KeyNames.RATING, rating));
            Document updateDocument = new Document("$setOnInsert", setOnInsertDocument);
            Document setDocument = DocumentConverter.toDocument(entity);
            setDocument.append(KeyNames.UPDATED_AT, new Date());
            setDocument.append(KeyNames.EXPIRE_AT, expireAt);
            updateDocument.append("$set", setDocument);
            UpdateOptions options = new UpdateOptions().upsert(true);
            return new UpdateOneModel<Document>(filter, updateDocument, options);
        }).collect(Collectors.toList());
        if (!upserts.isEmpty()) {
            collection.bulkWrite(upserts);
        }

    }

    @Override
    public Map<String, Restaurant> batchGetByGooglePlaceId(List<String> googlePlaceIds) {
        ImmutableMap.Builder<String, Restaurant> results = new ImmutableMap.Builder<>();
        MongoCollection<Document> collection = getRestaurantCollection();
        collection.find(Filters.in(KeyNames.GOOGLE_PLACE_ID, googlePlaceIds)).forEach((Block<? super Document>) document -> {
            Restaurant restaurant = DocumentConverter.toResult(document);
            results.put(restaurant.getPlaceId(), restaurant);
        });
        return results.build();
    }

    @Override
    public void bulkUpsertInBackground(List<Restaurant> entities) {
        executorService.submit(() -> bulkUpsert(entities));
    }

    @Override
    public void expireData() {
        MongoCollection<Document> collection = getRestaurantCollection();
        Bson filter = Filters.lte(KeyNames.EXPIRE_AT, new Date());
        Document unsetDocument = new Document();
        unsetDocument.append(KeyNames.GOOGLE_NAME, "");
        unsetDocument.append(KeyNames.COORDINATES, "");
        unsetDocument.append(KeyNames.EXPIRE_AT, "");
        Document updateDocument = new Document("$unset", unsetDocument);
        collection.updateOne(filter, updateDocument);
    }

    @Override
    public void markRecommendations(List<Restaurant> restaurants) {
        // for each restaurant, if rating >= 4.0
        if (restaurants != null) {
            MongoCollection<Document> collection = getRestaurantCollection();
            List<UpdateOneModel<Document>> upserts = restaurants.stream().filter(
                    restaurant -> restaurant.getRating() != null && restaurant.getRating() > 4.0
            ).map(restaurant -> {
                Bson filter = Filters.eq(KeyNames.GOOGLE_PLACE_ID, restaurant.getPlaceId());
                Document updateDocument = new Document();
                Document setDocument = new Document();
                setDocument.append(KeyNames.IS_RECOMMENDATION_CANDIDATE, true);
                updateDocument.append("$set", setDocument);
                UpdateOptions options = new UpdateOptions().upsert(false);
                return new UpdateOneModel<Document>(filter, updateDocument, options);
            }).collect(Collectors.toList());
            if (!upserts.isEmpty()) {
                collection.bulkWrite(upserts);
            }
        }
    }

    private MongoCollection<Document> getRestaurantCollection() {
        MongoDatabase database = mongoClient.getDatabase(ChifanheroConfigs.MONGO_DATABASE);
        return database.getCollection(ChifanheroConfigs.MONGO_COLLECTION_RESTAURANT);
    }
}
