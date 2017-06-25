package com.chifanhero.api.services.elasticsearch.query.helpers;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by shiyan on 5/21/17.
 */
public class SortHelper {

    public static SortBuilder buildGeoDistanceSort(String fieldName, double lat, double lon, DistanceUnit distanceUnit) {
        return SortBuilders
                .geoDistanceSort(fieldName, lat, lon)
                .order(org.elasticsearch.search.sort.SortOrder.ASC)
                .unit(distanceUnit);
    }

    public static SortBuilder buildSort(String fieldName, SortOrder sortOrder) {
        return SortBuilders.fieldSort(fieldName).order(sortOrder);
    }
}
