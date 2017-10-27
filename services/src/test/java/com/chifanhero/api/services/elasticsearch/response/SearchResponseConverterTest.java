package com.chifanhero.api.services.elasticsearch.response;


import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SearchResponseConverterTest {

    @Test
    public void test() {
        String raw = "{\n" +
                "  \"took\": 4,\n" +
                "  \"timed_out\": false,\n" +
                "  \"_shards\": {\n" +
                "    \"total\": 3,\n" +
                "    \"successful\": 3,\n" +
                "    \"failed\": 0\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"total\": 1,\n" +
                "    \"max_score\": 6.8208194,\n" +
                "    \"hits\": [\n" +
                "      {\n" +
                "        \"_index\": \"chifanhero\",\n" +
                "        \"_type\": \"Restaurant\",\n" +
                "        \"_id\": \"CRtOfsEDHc\",\n" +
                "        \"_score\": 6.8208194,\n" +
                "        \"_source\": {\n" +
                "          \"google_name\": \"Hunan Impression\",\n" +
                "          \"google_place_id\": \"1234\",\n" +
                "          \"rating\": 4.8,\n" +
                "          \"name\": \"韶山印象\",\n" +
                "          \"coordinates\": [\n" +
                "            -121.9938008,\n" +
                "            37.3088354\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject response = new JSONObject(raw);
        RestaurantSearchResponse restaurantSearchResponse = SearchResponseConverter.toLocalSearchResponse(response);
        Assert.assertNotNull(restaurantSearchResponse);
        List<Restaurant> results = restaurantSearchResponse.getResults();
        Assert.assertEquals(1, results.size());
        Restaurant restaurant = results.get(0);
        Assert.assertEquals("韶山印象", restaurant.getName());
        Assert.assertEquals("Hunan Impression", restaurant.getGoogleName());
        Assert.assertEquals("1234", restaurant.getPlaceId());
        Assert.assertEquals("CRtOfsEDHc", restaurant.getId());
        Assert.assertEquals(new Double(4.8), restaurant.getRating());
        Assert.assertEquals(new Double(37.3088354), restaurant.getCoordinates().getLatitude());
        Assert.assertEquals(new Double(-121.9938008), restaurant.getCoordinates().getLongitude());
    }

    @Test
    public void testGoogleRating() {
        String raw = "{\n" +
                "  \"took\": 4,\n" +
                "  \"timed_out\": false,\n" +
                "  \"_shards\": {\n" +
                "    \"total\": 3,\n" +
                "    \"successful\": 3,\n" +
                "    \"failed\": 0\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"total\": 1,\n" +
                "    \"max_score\": 6.8208194,\n" +
                "    \"hits\": [\n" +
                "      {\n" +
                "        \"_index\": \"chifanhero\",\n" +
                "        \"_type\": \"Restaurant\",\n" +
                "        \"_id\": \"CRtOfsEDHc\",\n" +
                "        \"_score\": 6.8208194,\n" +
                "        \"_source\": {\n" +
                "          \"google_name\": \"Hunan Impression\",\n" +
                "          \"google_place_id\": \"1234\",\n" +
                "          \"google_rating\": 4.8,\n" +
                "          \"name\": \"韶山印象\",\n" +
                "          \"coordinates\": [\n" +
                "            -121.9938008,\n" +
                "            37.3088354\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject response = new JSONObject(raw);
        RestaurantSearchResponse restaurantSearchResponse = SearchResponseConverter.toLocalSearchResponse(response);
        Assert.assertNotNull(restaurantSearchResponse);
        List<Restaurant> results = restaurantSearchResponse.getResults();
        Assert.assertEquals(1, results.size());
        Restaurant restaurant = results.get(0);
        Assert.assertEquals(new Double(4.8), restaurant.getRating());
    }

    @Test
    public void testRatingOverrideGoogleRating() {
        String raw = "{\n" +
                "  \"took\": 4,\n" +
                "  \"timed_out\": false,\n" +
                "  \"_shards\": {\n" +
                "    \"total\": 3,\n" +
                "    \"successful\": 3,\n" +
                "    \"failed\": 0\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"total\": 1,\n" +
                "    \"max_score\": 6.8208194,\n" +
                "    \"hits\": [\n" +
                "      {\n" +
                "        \"_index\": \"chifanhero\",\n" +
                "        \"_type\": \"Restaurant\",\n" +
                "        \"_id\": \"CRtOfsEDHc\",\n" +
                "        \"_score\": 6.8208194,\n" +
                "        \"_source\": {\n" +
                "          \"google_name\": \"Hunan Impression\",\n" +
                "          \"google_place_id\": \"1234\",\n" +
                "          \"google_rating\": 4.8,\n" +
                "          \"rating\": 4.6,\n" +
                "          \"name\": \"韶山印象\",\n" +
                "          \"coordinates\": [\n" +
                "            -121.9938008,\n" +
                "            37.3088354\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject response = new JSONObject(raw);
        RestaurantSearchResponse restaurantSearchResponse = SearchResponseConverter.toLocalSearchResponse(response);
        Assert.assertNotNull(restaurantSearchResponse);
        List<Restaurant> results = restaurantSearchResponse.getResults();
        Assert.assertEquals(1, results.size());
        Restaurant restaurant = results.get(0);
        Assert.assertEquals(new Double(4.6), restaurant.getRating());
    }
}
