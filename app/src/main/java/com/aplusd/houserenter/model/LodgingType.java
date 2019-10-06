package com.aplusd.houserenter.model;

/**
 * @author Azamat Dzhonov
 * @date 03.04.2018
 */

import android.content.Context;

import com.aplusd.houserenter.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LodgingType {

    @SerializedName("houseOrderType")
    @Expose
    private Integer houseOrderType;
    @SerializedName("houseOrderTypeDescription")
    @Expose
    private String houseOrderTypeDescription;

    public String getHouseOrderTypeDescription() {
        return houseOrderTypeDescription;
    }

    public void setHouseOrderTypeDescription(String houseOrderTypeDescription) {
        this.houseOrderTypeDescription = houseOrderTypeDescription;
    }

    public Integer getHouseOrderType() {
        return houseOrderType;
    }

    public void setHouseOrderType(Integer houseOrderType) {
        this.houseOrderType = houseOrderType;
    }

    public String getHouseOrderTypeDescription(Context context) {
        switch (houseOrderType) {
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