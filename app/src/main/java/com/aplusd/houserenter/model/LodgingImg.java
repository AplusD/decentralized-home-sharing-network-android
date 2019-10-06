package com.aplusd.houserenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class LodgingImg implements Serializable {

    @SerializedName("imgId")
    @Expose
    private Integer imgId;
    @SerializedName("houseId")
    @Expose
    private Integer houseId;
    @SerializedName("imgPath")
    @Expose
    private String imgPath;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
