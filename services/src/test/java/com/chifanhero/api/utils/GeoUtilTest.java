package com.chifanhero.api.utils;

import org.junit.Assert;
import org.junit.Test;

public class GeoUtilTest {

    @Test
    public void test() {
        double lat = 37.242312;
        double lon = -121.764887;
        double[][] coordinatesGroup = GeoUtil.getCoordinatesGroup(lat, lon, 2);
        Assert.assertTrue(coordinatesGroup.length == 4);
        for (double[] coordinates : coordinatesGroup) {
            Double distanceInMi = DistanceCalculator.getDistanceInMi(lat, lon, coordinates[0], coordinates[1], 2);
            Assert.assertTrue(isWithinRange(2.0, distanceInMi));
        }
        for (int i = 0; i < coordinatesGroup.length; i++) {
            for (int j = 0; j < coordinatesGroup.length; j++) {
                if (i != j) {
                    double[] coordinates1 = coordinatesGroup[i];
                    double[] coordinates2 = coordinatesGroup[j];
                    double expected;
                    if (Math.abs(i - j) == 2) {
                        expected = 4.0;
                    } else {
                        expected = 2.8;
                    }
                    Double distanceInMi = DistanceCalculator.getDistanceInMi(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1], 2);
                    Assert.assertTrue(isWithinRange(expected, distanceInMi));
                }
            }
        }
    }

    private boolean isWithinRange(double expected, double actual) {
        return actual >= expected - 0.1 && actual <= expected + 0.1;
    }
}
