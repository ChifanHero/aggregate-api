package com.chifanhero.api.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by shiyan on 6/10/17.
 */
public class DateUtilTest {

    @Test
    public void test() {
        Date current = new Date();
        Date after = DateUtil.addDays(current, 1);
        Assert.assertEquals(86400000, after.getTime() - current.getTime());
    }
}
