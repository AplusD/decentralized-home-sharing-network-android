package com.aplusd.houserenter.model;

import android.content.Context;

import com.aplusd.houserenter.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class UserInfo implements Serializable {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSurname")
    @Expose
    private String userSurname;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPhoneNumber")
    @Expose
    private String userPhoneNumber;
    @SerializedName("userBirthDay")
    @Expose
    private String userBirthDay;
    @SerializedName("userRegistrationDay")
    @Expose
    private String userRegistrationDay;
    @SerializedName("userWallet")
    @Expose
    private String userWallet;
    @SerializedName("userDescription")
    @Expose
    private String userDescription;
    @SerializedName("userAvatar")
    @Expose
    private String userAvatar;
    @SerializedName("userToken")
    @Expose
    private String userToken;


    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(String userBirthDay) {
        this.userBirthDay = userBirthDay;
    }

    public String getUserRegistrationDay() {
        return userRegistrationDay;
    }

    public void setUserRegistrationDay(String userRegistrationDay) {
        this.userRegistrationDay = userRegistrationDay;
    }

    public String getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(String userWallet) {
        this.userWallet = userWallet;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUnfilledFields()
    {
        if(getUserName() == null || getPassword() == null   || getUserBirthDay() == null
                || getUserEmail() == null || getUserSurname() == null)
            return true;
        return  false;
    }

    public String getUnfilledFields(Context context)
    {
        String unfilled = "";
        if(getUserName() == null)
            unfilled += context.getString(R.string.point) + context.getString(R.string.name) + "\n";
        if(getPassword() == null)
            unfilled += context.getString(R.string.point) + context.getString(R.string.password) + "\n";
        if(getUserBirthDay() == null)
            unfilled += context.getString(R.string.point) + context.getString(R.string.birthday) + "\n";
        if(getUserEmail() == null)
            unfilled += context.getString(R.string.point) + context.getString(R.string.email) + "\n";
        if(getUserSurname() == null)
            unfilled += context.getString(R.string.point) + context.getString(R.string.surname) +"\n";
        return unfilled;
    }

}
