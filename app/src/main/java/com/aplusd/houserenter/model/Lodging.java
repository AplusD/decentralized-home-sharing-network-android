package com.aplusd.houserenter.model;

import android.content.Context;

import com.aplusd.houserenter.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 07.03.2018
 */

public class Lodging implements Serializable {

    @SerializedName("houseId")
    @Expose
    private Integer houseId;
    @SerializedName("houseName")
    @Expose
    private String houseName;
    @SerializedName("houseOrderType")
    @Expose
    private Integer houseOrderType;
    @SerializedName("houseGuestCount")
    @Expose
    private Integer houseGuestCount;
    @SerializedName("housePriceType")
    @Expose
    private Integer housePriceType;
    @SerializedName("houseDescription")
    @Expose
    private String houseDescription;
    @SerializedName("cityCode")
    @Expose
    private Integer cityCode;
    @SerializedName("houseMainImg")
    @Expose
    private String houseMainImg;
    @SerializedName("dayPrice")
    @Expose
    private Integer dayPrice;
    @SerializedName("houseAddress")
    @Expose
    private String houseAddress;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("isAvailable")
    @Expose
    private Integer isAvailable;


    private Double Lat = null;
    private Double Lng = null;
    private String place = null;
    private ArrayList<LodgingImg> lodgingImgs = null;
    private UserInfo userInfo = null;


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

    public Integer getHouseOrderType() {
        return houseOrderType;
    }

    public void setHouseOrderType(Integer houseOrderType) {
        this.houseOrderType = houseOrderType;
    }

    public Integer getHouseGuestCount() {
        return houseGuestCount;
    }

    public void setHouseGuestCount(Integer houseGuestCount) {
        this.houseGuestCount = houseGuestCount;
    }

    public Integer getHousePriceType() {
        return housePriceType;
    }

    public void setHousePriceType(Integer housePriceType) {
        this.housePriceType = housePriceType;
    }

    public String getHouseDescription() {
        return houseDescription;
    }

    public void setHouseDescription(String houseDescription) {
        this.houseDescription = houseDescription;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getHouseMainImg() {
        return houseMainImg;
    }

    public void setHouseMainImg(String houseMainImg) {
        this.houseMainImg = houseMainImg;
    }

    public Integer getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Integer dayPrice) {
        this.dayPrice = dayPrice;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }


    public LatLng getLatLng() {
        if(Lat == null || Lng == null)
            return null;
        return new LatLng(Lat, Lng);
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<LodgingImg> getLodgingImgs() {
        return lodgingImgs;
    }

    public void setLodgingImgs(ArrayList<LodgingImg> lodgingImgs) {
        this.lodgingImgs = lodgingImgs;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    public String getLodgingInfo(Context context)
    {
        return context.getString(R.string.lodging_for_rent)  + ": " +  getHouseName() +  "\n"
                + context.getString(R.string.pricePerDay)  + getDayPrice();
    }


    public static String getHouseType(Context context, int houseType) {
        switch (houseType) {
            case 1:
                return context.getString(R.string.flate);
            case 2:
                return context.getString(R.string.house);
            case 3:
                return context.getString(R.string.lodging_at_home_apartment);
            case 4:
                return context.getString(R.string.unique_lodging);
            case 5:
                return context.getString(R.string.b_and_b);
            case 6:
                return context.getString(R.string.hotel);
            default:
                return "";
        }
    }


}
