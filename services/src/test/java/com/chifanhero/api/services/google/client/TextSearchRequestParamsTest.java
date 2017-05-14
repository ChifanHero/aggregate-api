package com.chifanhero.api.services.google.client;

import com.chifanhero.api.services.google.client.request.TextSearchRequestParams;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TextSearchRequestParamsTest {

    @Test
    public void test() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setKey("key1qaz234e");
        textSearchRequestParams.setType("chinese");
        textSearchRequestParams.setPageToken("p0op9iol");
        textSearchRequestParams.setRadius("500");
        textSearchRequestParams.setLanguage("en_US");
        textSearchRequestParams.setQuery("Burger King");
        textSearchRequestParams.setOpenNow(true);
        textSearchRequestParams.setLocation("37.242312,-121.764886");
        Map<String, Object> params = textSearchRequestParams.getParams();
        Assert.assertEquals("key1qaz234e", params.get("key"));
        Assert.assertEquals("chinese", params.get("type"));
        Assert.assertEquals("p0op9iol", params.get("pagetoken"));
        Assert.assertEquals("500", params.get("radius"));
        Assert.assertEquals("en_US", params.get("language"));
        Assert.assertEquals("Burger King", params.get("query"));
        Assert.assertEquals(true, params.get("opennow"));
        Assert.assertEquals("37.242312,-121.764886", params.get("location"));
    }

    @Test
    public void testQueryOnly() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setQuery("Burger King");
        textSearchRequestParams.setKey("key1qaz234e");
        textSearchRequestParams.getParams();
    }

    @Test
    public void testTypeOnly() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setType("chinese");
        textSearchRequestParams.setKey("key1qaz234e");
        textSearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryTypeBothMissing() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setKey("key1qaz234e");
        textSearchRequestParams.getParams();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyRequired() {
        TextSearchRequestParams textSearchRequestParams = new TextSearchRequestParams();
        textSearchRequestParams.setType("chinese");
        textSearchRequestParams.getParams();
    }
}
