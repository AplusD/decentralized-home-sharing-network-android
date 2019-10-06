package com.aplusd.houserenter.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.UserInfo;

/**
 * @author Azamat Dzhonov
 * @date 20.04.2018
 */

public class SPHelper {

    private final static String USER_PACKAGE = "com.aplusd.houserenter";

    public static int getUserId(Context context)
    {
        return context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE)
                .getInt(JSONConstants.USER_ID, -1);
    }

    public static String getToken(Context context)
    {
        return context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE)
                .getString(JSONConstants.USER_TOKEN, "none");
    }

    public static String getPrivateKey(Context context)
    {
        return context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE)
                .getString(JSONConstants.USER_PRIVATE_KEY, "none");
    }

    public static void setPrivateKey(Context context, String privateKey)
    {
        SharedPreferences prefs = context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(JSONConstants.USER_PRIVATE_KEY, privateKey);
        editor.apply();
    }

    public static String getPublicKey(Context context)
    {
        return context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE)
                .getString(JSONConstants.USER_PUBLIC_KEY, "none");
    }

    public static void setPublicKey(Context context, String publicKey)
    {
        SharedPreferences prefs = context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(JSONConstants.USER_PUBLIC_KEY, publicKey);
        editor.apply();
    }

    public static void setUserInfo(Context context, UserInfo userInfo)
    {
        SharedPreferences prefs = context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(JSONConstants.USER_ID, userInfo.getUserId());
        editor.putString(JSONConstants.USER_TOKEN, userInfo.getUserToken());
        editor.putString(JSONConstants.USER_PUBLIC_KEY, userInfo.getUserWallet());
        editor.apply();
    }

    public static void clearUserInformation(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(USER_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(JSONConstants.USER_ID, -1);
        editor.putString(JSONConstants.USER_TOKEN, "none");
        editor.putString(JSONConstants.USER_PUBLIC_KEY, "none");
        editor.apply();
    }



}
