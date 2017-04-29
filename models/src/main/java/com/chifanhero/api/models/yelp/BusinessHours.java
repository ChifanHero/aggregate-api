package com.chifanhero.api.models.yelp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by shiyan on 4/27/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessHours {

    private List<BusinessHour> open;

    @JsonProperty("hours_type")
    private HoursType hoursType;

    @JsonProperty("is_open_now")
    private boolean isOpenNow;

    public List<BusinessHour> getOpen() {
        return open;
    }

    public void setOpen(List<BusinessHour> open) {
        this.open = open;
    }

    public HoursType getHoursType() {
        return hoursType;
    }

    public void setHoursType(HoursType hoursType) {
        this.hoursType = hoursType;
    }

    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }
}
