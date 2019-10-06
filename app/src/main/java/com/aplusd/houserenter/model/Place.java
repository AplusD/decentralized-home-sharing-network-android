package com.aplusd.houserenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class Place implements Serializable  {

    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("countryName")
    @Expose
    private String countryName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}