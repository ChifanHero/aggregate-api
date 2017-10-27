package com.chifanhero.api.services.elasticsearch.query.helpers;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shiyan on 6/24/17.
 */
public class RequestHelper {

    public static String buildSearchRequest(QueryBuilder query, SortBuilder sort) {
        JSONObject request = new JSONObject();
        JSONObject jsonQuery = new JSONObject(query.toString());
        request.put("from", 0);
        request.put("size", 20);
        request.put("query", jsonQuery);
        if (sort != null) {
            JSONObject jsonSort = new JSONObject(sort.toString());
            JSONArray sortArray = new JSONArray();
            sortArray.put(jsonSort);
            request.put("sort", sortArray);
        }
        return request.toString();
    }
}
