package com.chifanhero.api.services.chifanhero;

import com.chifanhero.api.models.response.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by shiyan on 4/27/17.
 */
public interface ChifanheroRestaurantService {

    void bulkUpsert(List<Result> entities);

    Map<String, Result> batchGetByGooglePlaceId(List<String> googlePlaceIds);

    void bulkUpsertInBackground(List<Result> entities);

    void expireDocuments();
}
