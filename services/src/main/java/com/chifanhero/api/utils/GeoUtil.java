package com.chifanhero.api.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * Created by shiyan on 6/17/17.
 */
public class GeoUtil {

    private final static GeodeticCalculator geoCalc = new GeodeticCalculator();
    private final static Ellipsoid reference = Ellipsoid.WGS84;

    public static double[][] getCoordinatesGroup(double lat, double lon, double radiusInMiles) {
        GlobalCoordinates center = new GlobalCoordinates(lat, lon);
        double[][] results = new double[4][2];
        results[0] = getDestination(center, 0, radiusInMiles);
        results[1] = getDestination(center, 90, radiusInMiles);
        results[2] = getDestination(center, 180, radiusInMiles);
        results[3] = getDestination(center, 270, radiusInMiles);
        return results;
    }

    private static double[] getDestination(GlobalCoordinates center, double startBearing, double distanceInMiles) {
        double distanceInMeters = distanceInMiles * 1.6 * 1000;
        double[] endBearing = new double[1];
        double[] latlon = new double[2];
        GlobalCoordinates dest = geoCalc.calculateEndingGlobalCoordinates(reference, center, startBearing, distanceInMeters, endBearing);
        latlon[0] = dest.getLatitude();
        latlon[1] = dest.getLongitude();
        return latlon;
    }
}
