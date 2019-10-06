package com.aplusd.houserenter.model;

/**
 * @author Azamat Dzhonov
 * @date 04.04.2018
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LodgingMarker {

    @SerializedName("houseId")
    @Expose
    private Integer houseId;
    @SerializedName("houseName")
    @Expose
    private String houseName;
    @SerializedName("dayPrice")
    @Expose
    private Integer dayPrice;
    @SerializedName("houseLat")
    @Expose
    private Double houseLat;
    @SerializedName("houseLng")
    @Expose
    private Double houseLng;

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Integer dayPrice) {
        this.dayPrice = dayPrice;
    }

    public Double getHouseLat() {
        return houseLat;
    }

    public void setHouseLat(Double houseLat) {
        this.houseLat = houseLat;
    }

    public Double getHouseLng() {
        return houseLng;
    }

    public void setHouseLng(Double houseLng) {
        this.houseLng = houseLng;
    }

    @Override
    public String toString() {
        return dayPrice + "$";
    }
}