package com.chifanhero.api.common;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by shiyan on 4/29/17.
 */
public class TestGetRequest {

    @Test
    public void testHappyCase() {
        String baseUrl = "http://chifanhero.com";
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("param1", 1);
        params.put("param2", true);
        params.put("param3", "foo");
        params.put("param4", new BigDecimal("1.2"));
        GetRequest getRequest = new GetRequest(baseUrl, params);
        String url = getRequest.getUrl();
        assertEquals("http://chifanhero.com?param1=1&param2=true&param3=foo&param4=1.2", url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBaseUrl() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("param1", 1);
        params.put("param2", true);
        params.put("param3", "foo");
        params.put("param4", new BigDecimal("1.2"));
        new GetRequest(null, params);
    }
}
