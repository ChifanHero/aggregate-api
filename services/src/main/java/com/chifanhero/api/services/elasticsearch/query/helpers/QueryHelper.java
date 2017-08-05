package com.chifanhero.api.services.elasticsearch.query.helpers;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;

/**
 * Created by shiyan on 5/20/17.
 */
public class QueryHelper {

    public static org.elasticsearch.index.query.QueryBuilder buildNearbySearchQuery(NearbySearchRequest nearbySearchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (nearbySearchRequest.getRating() != null) {
            boolQueryBuilder.filter(buildRatingFilterQuery(nearbySearchRequest.getRating()));
        }
        boolQueryBuilder.filter(buildGeoDistanceQuery(
                nearbySearchRequest.getLocation().getLat(),
                nearbySearchRequest.getLocation().getLon(),
                nearbySearchRequest.getRadius(),
                DistanceUnit.METERS
        )).filter(buildOnHolderQuery());
        return boolQueryBuilder;
    }

    public static org.elasticsearch.index.query.QueryBuilder buildTextSearchQuery(TextSearchRequest textSearchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(buildTextQuery(textSearchRequest.getQuery()));
        if (textSearchRequest.getRating() != null) {
            boolQueryBuilder.filter(buildRatingFilterQuery(textSearchRequest.getRating()));
        }
        boolQueryBuilder.filter(buildGeoDistanceQuery(
                textSearchRequest.getLocation().getLat(),
                textSearchRequest.getLocation().getLon(),
                textSearchRequest.getRadius(),
                DistanceUnit.METERS
        )).filter(buildOnHolderQuery());
        return boolQueryBuilder;
    }

    private static org.elasticsearch.index.query.QueryBuilder buildOnHolderQuery() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery(FieldNames.ON_HOLD, true));
        return boolQueryBuilder;
    }

    private static GeoDistanceQueryBuilder buildGeoDistanceQuery(double lat, double lon, double distance, DistanceUnit distanceUnit) {
        return QueryBuilders.geoDistanceQuery(FieldNames.COORDINATES)
                .point(lat, lon)
                .distance(distance, distanceUnit);
    }

    private static RangeQueryBuilder buildRatingFilterQuery(double minScore) {
        return QueryBuilders.rangeQuery(FieldNames.RATING).gte(minScore);
    }

    private static org.elasticsearch.index.query.QueryBuilder buildTextQuery(String keyword) {
        return QueryBuilders.disMaxQuery()
                .add(QueryBuilders.matchQuery(FieldNames.NAME, keyword))
                .add(QueryBuilders.matchQuery(FieldNames.GOOGLE_NAME, keyword));
    }
}
