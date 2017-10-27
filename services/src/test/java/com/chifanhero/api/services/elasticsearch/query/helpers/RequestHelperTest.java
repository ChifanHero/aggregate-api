package com.chifanhero.api.services.elasticsearch.query.helpers;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.services.elasticsearch.query.FieldNames;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 6/24/17.
 */
public class RequestHelperTest {

    @Test
    public void test() {
        Location location = new Location();
        location.setLat(37.30891649999999);
        location.setLon(-121.993827);
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();
        nearbySearchRequest.setRating(3.0);
        nearbySearchRequest.setRadius(5000);
        nearbySearchRequest.setLocation(location);
        String searchRequest = RequestHelper.buildSearchRequest(
                QueryHelper.buildNearbySearchQuery(nearbySearchRequest)
                , SortHelper.buildSort(FieldNames.RATING, SortOrder.DESC));
        String expected = "{\"size\":20,\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"adjust_pure_negative\":true,\"should\":[{\"bool\":{\"adjust_pure_negative\":true,\"must\":[{\"range\":{\"rating\":{\"include_lower\":true,\"include_upper\":true,\"from\":3,\"boost\":1,\"to\":null}}}],\"boost\":1,\"disable_coord\":false}},{\"bool\":{\"adjust_pure_negative\":true,\"must_not\":[{\"exists\":{\"field\":\"rating\",\"boost\":1}}],\"must\":[{\"range\":{\"google_rating\":{\"include_lower\":true,\"include_upper\":true,\"from\":3,\"boost\":1,\"to\":null}}}],\"boost\":1,\"disable_coord\":false}}],\"boost\":1,\"disable_coord\":false}},{\"geo_distance\":{\"distance\":5000,\"distance_type\":\"arc\",\"coordinates\":[-121.993827,37.30891649999999],\"boost\":1,\"validation_method\":\"STRICT\",\"ignore_unmapped\":false}},{\"bool\":{\"adjust_pure_negative\":true,\"must_not\":[{\"term\":{\"on_hold\":{\"boost\":1,\"value\":true}}}],\"boost\":1,\"disable_coord\":false}}],\"adjust_pure_negative\":true,\"boost\":1,\"disable_coord\":false}},\"from\":0,\"sort\":[{\"rating\":{\"order\":\"desc\"}}]}";
        Assert.assertEquals(expected, searchRequest);
    }
}
