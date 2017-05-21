package com.chifanhero.api.services.elasticsearch.query.helpers;

import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.sort.SortBuilder;
import org.junit.Assert;
import org.junit.Test;

public class SortHelperTest {

    @Test
    public void test() {
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
