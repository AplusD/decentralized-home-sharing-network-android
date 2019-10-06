package com.aplusd.houserenter.inbox.model;

import android.content.Context;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.extra.HelpFunction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.aplusd.houserenter.model.Constants.SIMPLE_DATE_FORMAT;
import static com.aplusd.houserenter.model.Constants.SIMPLE_DATE_FORMAT_TO_SERVER;

/**
 * @author Azamat Dzhonov
 * @date 24.04.2018
 */

public class Offering  implements Serializable {

    private static SimpleDateFormat simpleDataFormatFromServer = new SimpleDateFormat(SIMPLE_DATE_FORMAT_TO_SERVER);
    private static SimpleDateFormat simpleDataFormatToServer = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

    @SerializedName("contractId")
    @Expose
    private Integer contractId;
    @SerializedName("guestId")
    @Expose
    private Integer guestId;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("contractStatus")
    @Expose
    private Integer contractStatus;
    @SerializedName("partnerId")
    @Expose
    private Integer partnerId;
    @SerializedName("partnerName")
    @Expose
    private String partnerName;
    @SerializedName("partnerSurname")
    @Expose
    private String partnerSurname;
    @SerializedName("partnerBirthDay")
    @Expose
    private String partnerBirthDay;
    @SerializedName("partnerEmail")
    @Expose
    private String partnerEmail;
    @SerializedName("partnerPhoneNumber")
    @Expose
    private String partnerPhoneNumber;
    @SerializedName("partnerRegistartion")
    @Expose
    private String partnerRegistartion;
    @SerializedName("partnerWallet")
    @Expose
    private String partnerWallet;
    @SerializedName("partnerDescription")
    @Expose
    private String partnerDescription;
    @SerializedName("partnerAvatar")
    @Expose
    private String partnerAvatar;
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
    @SerializedName("houseLat")
    @Expose
    private Double houseLat;
    @SerializedName("houseLng")
    @Expose
    private Double houseLng;
    @SerializedName("priceWei")
    @Expose
    private Double contractPriceEther = 0.0;

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerSurname() {
        return partnerSurname;
    }

    public void setPartnerSurname(String partnerSurname) {
        this.partnerSurname = partnerSurname;
    }

    public String getPartnerBirthDay() {
        return partnerBirthDay;
    }

    public void setPartnerBirthDay(String partnerBirthDay) {
        this.partnerBirthDay = partnerBirthDay;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public String getPartnerPhoneNumber() {
        return partnerPhoneNumber;
    }

    public void setPartnerPhoneNumber(String partnerPhoneNumber) {
        this.partnerPhoneNumber = partnerPhoneNumber;
    }

    public String getPartnerRegistartion() {
        return partnerRegistartion;
    }

    public void setPartnerRegistartion(String partnerRegistartion) {
        this.partnerRegistartion = partnerRegistartion;
    }

    public String getPartnerWallet() {
        return partnerWallet;
    }

    public void setPartnerWallet(String partnerWallet) {
        this.partnerWallet = partnerWallet;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public String getPartnerAvatar() {
        return partnerAvatar;
    }

    public void setPartnerAvatar(String partnerAvatar) {
        this.partnerAvatar = partnerAvatar;
    }

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

    public String getDates(Context context)
    {
        try {
            Date from = simpleDataFormatFromServer.parse(getDateFrom());
            Date to = simpleDataFormatFromServer.parse(getDateTo());

            return simpleDataFormatToServer.format(from)
                + " - " +
            simpleDataFormatToServer.format(to)  +  " ( " + HelpFunction.getDateDiff(from, to, TimeUnit.DAYS) + " "
                    + context.getString(R.string.days) + ")";
        }
        catch (Exception ex)
        {
            return getDateFrom() + " - " + getDateTo();
        }
    }

    public Double getContractPriceEther() {
        return contractPriceEther;
    }

    public void setContractPriceEther(Double contractPriceEther) {
        this.contractPriceEther = contractPriceEther;
    }

    public static String parseStatus(int status, Context context)
    {
        switch (status)
        {
            case 1:
                return context.getString(R.string.status_1);
            case 2:
                return context.getString(R.string.status_2);
            case 3:
                return context.getString(R.string.status_3);
            case 5:
                return context.getString(R.string.status_5);
            case 6:
                return context.getString(R.string.status_6);
            case 7:
                return context.getString(R.string.status_7);
            case 8:
                return context.getString(R.string.status_8);
            default:
                return context.getString(R.string.status_1);
        }
    }
}