package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.request.NearbySearchRequest;
import com.chifanhero.api.models.request.SortOrder;
import com.chifanhero.api.services.google.client.request.NearBySearchRequestParams;
import com.chifanhero.api.services.google.client.request.RankBy;

public class NearBySearchRequestConverter {

    public static NearBySearchRequestParams toParams(NearbySearchRequest nearbySearchRequest) {
        NearBySearchRequestParams nearBySearchRequestParams = new NearBySearchRequestParams();
        nearBySearchRequestParams.setLocation(LocationConverter.toLocationParam(nearbySearchRequest.getLocation()));
        nearBySearchRequestParams.setRadius(String.valueOf(nearbySearchRequest.getRadius()));
        nearBySearchRequestParams.setOpenNow(nearbySearchRequest.getOpenNow());
        nearBySearchRequestParams.setType(nearbySearchRequest.getType());
        nearBySearchRequestParams.setKeyword(nearbySearchRequest.getKeyword());
        if (nearbySearchRequest.getSortOrder().equals(SortOrder.NEAREST.name())) {
            nearBySearchRequestParams.setRankBy(RankBy.distance);
            nearBySearchRequestParams.setRadius(null);
        }
        //TODO - page token?
        return nearBySearchRequestParams;
    }
}
