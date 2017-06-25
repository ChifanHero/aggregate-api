package com.chifanhero.api.services.elasticsearch.query.helpers;

import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;

public class SortHelperTest {

    @Test
    public void testSort() {
        SortBuilder sortBuilder = SortHelper.buildSort(FieldNames.RATING, SortOrder.DESC);
        String expectedSort = "{\n" +
                "  \"rating\" : {\n" +
                "    \"order\" : \"desc\"\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedSort, sortBuilder.toString());
    }

    @Test
    public void testGeoDistanceSort() {
        SortBuilder sortBuilder = SortHelper.buildGeoDistanceSort(FieldNames.COORDINATES, 37.30891649999999, -121.993827, DistanceUnit.MILES);
        String expectedSort = "{\n" +
                "  \"_geo_distance\" : {\n" +
                "    \"coordinates\" : [\n" +
                "      {\n" +
                "        \"lat\" : 37.30891649999999,\n" +
                "        \"lon\" : -121.993827\n" +
                "      }\n" +
                "    ],\n" +
                "    \"unit\" : \"mi\",\n" +
                "    \"distance_type\" : \"arc\",\n" +
                "    \"order\" : \"asc\",\n" +
                "    \"validation_method\" : \"STRICT\"\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedSort, sortBuilder.toString());
    }
}
