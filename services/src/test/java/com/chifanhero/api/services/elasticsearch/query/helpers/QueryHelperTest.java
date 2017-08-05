package com.chifanhero.api.services.elasticsearch.query.helpers;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.services.elasticsearch.query.helpers.QueryHelper;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryHelperTest {

    private static Location location;


    @BeforeClass
    public static void beforeClass() {
        location = new Location();
        location.setLat(37.30891649999999);
        location.setLon(-121.993827);
    }

    @Test
    public void testBuildNearbySearchQueryWithRating() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRating(3.0);
        nearbySearchRequest.setRadius(5000);
        nearbySearchRequest.setLocation(location);
        QueryBuilder queryBuilder = QueryHelper.buildNearbySearchQuery(nearbySearchRequest);
        String expectedQuery = "{\n" +
                "  \"bool\" : {\n" +
                "    \"filter\" : [\n" +
                "      {\n" +
                "        \"range\" : {\n" +
                "          \"rating\" : {\n" +
                "            \"from\" : 3.0,\n" +
                "            \"to\" : null,\n" +
                "            \"include_lower\" : true,\n" +
                "            \"include_upper\" : true,\n" +
                "            \"boost\" : 1.0\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"geo_distance\" : {\n" +
                "          \"coordinates\" : [\n" +
                "            -121.993827,\n" +
                "            37.30891649999999\n" +
                "          ],\n" +
                "          \"distance\" : 5000.0,\n" +
                "          \"distance_type\" : \"arc\",\n" +
                "          \"validation_method\" : \"STRICT\",\n" +
                "          \"ignore_unmapped\" : false,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"bool\" : {\n" +
                "          \"must_not\" : [\n" +
                "            {\n" +
                "              \"term\" : {\n" +
                "                \"on_hold\" : {\n" +
                "                  \"value\" : true,\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"disable_coord\" : false,\n" +
                "          \"adjust_pure_negative\" : true,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"disable_coord\" : false,\n" +
                "    \"adjust_pure_negative\" : true,\n" +
                "    \"boost\" : 1.0\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedQuery, queryBuilder.toString());
    }

    @Test
    public void testBuildNearbySearchQueryWithoutRating() {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRadius(5000);
        nearbySearchRequest.setLocation(location);
        QueryBuilder queryBuilder = QueryHelper.buildNearbySearchQuery(nearbySearchRequest);
        String expectedQuery = "{\n" +
                "  \"bool\" : {\n" +
                "    \"filter\" : [\n" +
                "      {\n" +
                "        \"geo_distance\" : {\n" +
                "          \"coordinates\" : [\n" +
                "            -121.993827,\n" +
                "            37.30891649999999\n" +
                "          ],\n" +
                "          \"distance\" : 5000.0,\n" +
                "          \"distance_type\" : \"arc\",\n" +
                "          \"validation_method\" : \"STRICT\",\n" +
                "          \"ignore_unmapped\" : false,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"bool\" : {\n" +
                "          \"must_not\" : [\n" +
                "            {\n" +
                "              \"term\" : {\n" +
                "                \"on_hold\" : {\n" +
                "                  \"value\" : true,\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"disable_coord\" : false,\n" +
                "          \"adjust_pure_negative\" : true,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"disable_coord\" : false,\n" +
                "    \"adjust_pure_negative\" : true,\n" +
                "    \"boost\" : 1.0\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedQuery, queryBuilder.toString());
    }

    @Test
    public void testBuildTextSearchQueryWithRating() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("韶山冲");
        textSearchRequest.setLocation(location);
        textSearchRequest.setRating(3.0);
        textSearchRequest.setRadius(5000);
        QueryBuilder queryBuilder = QueryHelper.buildTextSearchQuery(textSearchRequest);
        String expectedQuery = "{\n" +
                "  \"bool\" : {\n" +
                "    \"must\" : [\n" +
                "      {\n" +
                "        \"dis_max\" : {\n" +
                "          \"tie_breaker\" : 0.0,\n" +
                "          \"queries\" : [\n" +
                "            {\n" +
                "              \"match\" : {\n" +
                "                \"name\" : {\n" +
                "                  \"query\" : \"韶山冲\",\n" +
                "                  \"operator\" : \"OR\",\n" +
                "                  \"prefix_length\" : 0,\n" +
                "                  \"max_expansions\" : 50,\n" +
                "                  \"fuzzy_transpositions\" : true,\n" +
                "                  \"lenient\" : false,\n" +
                "                  \"zero_terms_query\" : \"NONE\",\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"match\" : {\n" +
                "                \"google_name\" : {\n" +
                "                  \"query\" : \"韶山冲\",\n" +
                "                  \"operator\" : \"OR\",\n" +
                "                  \"prefix_length\" : 0,\n" +
                "                  \"max_expansions\" : 50,\n" +
                "                  \"fuzzy_transpositions\" : true,\n" +
                "                  \"lenient\" : false,\n" +
                "                  \"zero_terms_query\" : \"NONE\",\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"filter\" : [\n" +
                "      {\n" +
                "        \"range\" : {\n" +
                "          \"rating\" : {\n" +
                "            \"from\" : 3.0,\n" +
                "            \"to\" : null,\n" +
                "            \"include_lower\" : true,\n" +
                "            \"include_upper\" : true,\n" +
                "            \"boost\" : 1.0\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"geo_distance\" : {\n" +
                "          \"coordinates\" : [\n" +
                "            -121.993827,\n" +
                "            37.30891649999999\n" +
                "          ],\n" +
                "          \"distance\" : 5000.0,\n" +
                "          \"distance_type\" : \"arc\",\n" +
                "          \"validation_method\" : \"STRICT\",\n" +
                "          \"ignore_unmapped\" : false,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"bool\" : {\n" +
                "          \"must_not\" : [\n" +
                "            {\n" +
                "              \"term\" : {\n" +
                "                \"on_hold\" : {\n" +
                "                  \"value\" : true,\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"disable_coord\" : false,\n" +
                "          \"adjust_pure_negative\" : true,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"disable_coord\" : false,\n" +
                "    \"adjust_pure_negative\" : true,\n" +
                "    \"boost\" : 1.0\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedQuery, queryBuilder.toString());
    }

    @Test
    public void testBuildTextSearchQueryWithoutRating() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery("韶山冲");
        textSearchRequest.setLocation(location);
        textSearchRequest.setRadius(5000);
        QueryBuilder queryBuilder = QueryHelper.buildTextSearchQuery(textSearchRequest);
        String expectedQuery = "{\n" +
                "  \"bool\" : {\n" +
                "    \"must\" : [\n" +
                "      {\n" +
                "        \"dis_max\" : {\n" +
                "          \"tie_breaker\" : 0.0,\n" +
                "          \"queries\" : [\n" +
                "            {\n" +
                "              \"match\" : {\n" +
                "                \"name\" : {\n" +
                "                  \"query\" : \"韶山冲\",\n" +
                "                  \"operator\" : \"OR\",\n" +
                "                  \"prefix_length\" : 0,\n" +
                "                  \"max_expansions\" : 50,\n" +
                "                  \"fuzzy_transpositions\" : true,\n" +
                "                  \"lenient\" : false,\n" +
                "                  \"zero_terms_query\" : \"NONE\",\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"match\" : {\n" +
                "                \"google_name\" : {\n" +
                "                  \"query\" : \"韶山冲\",\n" +
                "                  \"operator\" : \"OR\",\n" +
                "                  \"prefix_length\" : 0,\n" +
                "                  \"max_expansions\" : 50,\n" +
                "                  \"fuzzy_transpositions\" : true,\n" +
                "                  \"lenient\" : false,\n" +
                "                  \"zero_terms_query\" : \"NONE\",\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"filter\" : [\n" +
                "      {\n" +
                "        \"geo_distance\" : {\n" +
                "          \"coordinates\" : [\n" +
                "            -121.993827,\n" +
                "            37.30891649999999\n" +
                "          ],\n" +
                "          \"distance\" : 5000.0,\n" +
                "          \"distance_type\" : \"arc\",\n" +
                "          \"validation_method\" : \"STRICT\",\n" +
                "          \"ignore_unmapped\" : false,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"bool\" : {\n" +
                "          \"must_not\" : [\n" +
                "            {\n" +
                "              \"term\" : {\n" +
                "                \"on_hold\" : {\n" +
                "                  \"value\" : true,\n" +
                "                  \"boost\" : 1.0\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"disable_coord\" : false,\n" +
                "          \"adjust_pure_negative\" : true,\n" +
                "          \"boost\" : 1.0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"disable_coord\" : false,\n" +
                "    \"adjust_pure_negative\" : true,\n" +
                "    \"boost\" : 1.0\n" +
                "  }\n" +
                "}";
        Assert.assertEquals(expectedQuery, queryBuilder.toString());
    }
}
