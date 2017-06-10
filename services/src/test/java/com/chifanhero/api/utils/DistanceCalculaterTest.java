package com.chifanhero.api.utils;

import org.junit.Assert;
import org.junit.Test;

public class DistanceCalculaterTest {

    @Test
    public void test() {
        Double distance = DistanceCalculator.getDistanceInMi(37.295448, -121.927500, 37.242312, -121.764887, 2);
        Assert.assertEquals(new Double(9.67), distance);
    }
}
