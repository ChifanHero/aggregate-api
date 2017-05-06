package com.chifanhero.api.common;

import com.chifanhero.api.common.annotations.ParamKey;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class GetRequestParamsTest {

    @Test
    public void test() {
        TestRequestParams testRequestParams = new TestRequestParams();
        testRequestParams.field3 = "f3Value";
        testRequestParams.field2 = "f2Value";
        testRequestParams.field1 = 5;
        Map<String, Object> params = testRequestParams.getParams();
        Assert.assertEquals("f2Value", params.get("f2"));
        Assert.assertEquals(5, params.get("field1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidation() {
        TestRequestParams testRequestParams = new TestRequestParams();
        testRequestParams.getParams();
    }

    private class TestRequestParams extends GetRequestParams {

        private int field1;

        @ParamKey("f2")
        private String field2;

        @NotNull
        private String field3;
    }
}
