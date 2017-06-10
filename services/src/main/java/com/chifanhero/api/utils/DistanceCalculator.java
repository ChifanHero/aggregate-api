package com.chifanhero.api.utils;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by shiyan on 5/21/17.
 */
public class DistanceCalculator {

    private static final double EARTH_RADIUS_MI = 3958.75;
//    private static final double EARTH_RADIUS_KM = 6371.0;

    public static Double getDistanceInMi(double lat1, double lon1, double lat2, double lon2, int scale) {
        Double distance =  getDistance(EARTH_RADIUS_MI, lat1, lon1, lat2, lon2);
        return round(distance, scale);
    }

    private static Double getDistance(double earthRadius, double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2)
                + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private static double round(double value, int scale) {
        Preconditions.checkArgument(scale >= 0);
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
