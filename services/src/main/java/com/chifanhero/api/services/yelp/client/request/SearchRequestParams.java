package com.chifanhero.api.services.yelp.client.request;

import com.chifanhero.api.common.GetRequestParams;
import com.chifanhero.api.common.annotations.*;

import java.math.BigDecimal;

/**
 * Yelp Search API request params
 * Created by shiyan on 4/29/17.
 */
@Unions({
        @Union(field1 = "location", field2 = "longitude"),
        @Union(field1 = "location", field2 = "latitude")
})
@Bonds({
        @Bond({"longitude", "latitude"})
})
public class SearchRequestParams extends GetRequestParams {

    private String term;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
    private String categories;
    private String locale;
    private Integer limit;
    private Integer offset;

    @ParamKey("sort_by")
    private String sortBy;
    private String price;

    @ParamKey("open_now")
    private Boolean openNow;

    @ParamKey("open_at")
    private Integer openAt;
    private String attributes;

    public SearchRequestParams setTerm(String term) {
//        addParam(ParamNames.TERM, term);
        return this;
    }

//    private class ParamNames {
//        private final static String TERM = "term";
//        private final static String LOCATION = "location";
//        private final static String LATITUDE = "latitude";
//        private final static String LONGITUDE = "longitude";
////        private final static String RADIUS =
//    }
}
