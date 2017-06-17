package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 6/15/17.
 */
public class TextSearchRequestConverterTest {

    @Test
    public void test() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        Location location = new Location();
        location.setLat(37.322);
        location.setLon(-121.965);
        textSearchRequest.setLocation(location);
        textSearchRequest.setRadius(500);
        textSearchRequest.setQuery("hunan impression");
        textSearchRequest.setType("restaurant");
        textSearchRequest.setOpenNow(true);
        TextSearchRequestParams textSearchRequestParams = TextSearchRequestConverter.toParams(textSearchRequest);
        Assert.assertEquals("37.322,-121.965", textSearchRequestParams.getLocation());
        Assert.assertEquals("500", textSearchRequestParams.getRadius());
        Assert.assertEquals("hunan impression", textSearchRequestParams.getQuery());
        Assert.assertEquals("restaurant", textSearchRequestParams.getType());
        Assert.assertEquals(true, textSearchRequestParams.getOpenNow());
    }
}
