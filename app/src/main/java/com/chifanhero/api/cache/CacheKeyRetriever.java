package com.chifanhero.api.cache;

import com.chifanhero.api.models.request.Location;
import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.TextSearchRequest;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by shiyan on 6/27/17.
 */
public class CacheKeyRetriever {

    private final static int SCALE = 4;

    public static String from(NearbySearchRequest nearbySearchRequest) {
        Preconditions.checkNotNull(nearbySearchRequest);
        NearbySearchRequest clone = nearbySearchRequest.clone();
        Optional.ofNullable(clone.getLocation()).ifPresent(location -> clone.setLocation(getApproximateLocation(location)));
        return clone.toString();
    }

    public static String from(TextSearchRequest textSearchRequest) {
        Preconditions.checkNotNull(textSearchRequest);
        TextSearchRequest clone = textSearchRequest.clone();
        Optional.ofNullable(clone.getLocation()).ifPresent(location -> clone.setLocation(getApproximateLocation(location)));
        return clone.toString();
    }

    private static Location getApproximateLocation(Location location) {
        Preconditions.checkNotNull(location);
        Location truncated = new Location();
        truncated.setLat(truncateDouble(location.getLat()));
        truncated.setLon(truncateDouble(location.getLon()));
        return truncated;
    }

    private static double truncateDouble(double toBeTruncated) {
        return BigDecimal.valueOf(toBeTruncated)
                .setScale(SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
