package com.aplusd.houserenter.user.registration;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.model.Constants;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class ViewModelRegistration extends ViewModel {

    private MutableLiveData<UserInfo> userInfoMutableLiveData = null;

    public MutableLiveData<UserInfo> getUserInfoMutableLiveData() {
        if(userInfoMutableLiveData == null) {
            userInfoMutableLiveData = new MutableLiveData<>();
            userInfoMutableLiveData.setValue(new UserInfo());
        }
        return userInfoMutableLiveData;
    }

    public MutableLiveData<JSONObject> createNewUser()
    {
        final MutableLiveData<JSONObject> jsonObjectMutableLiveData = new MutableLiveData<>();
        jsonObjectMutableLiveData.setValue(null);

        UserInfo userInfo = getUserInfoMutableLiveData().getValue();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.REGISTRATION).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.USER_EMAIL, userInfo.getUserEmail());
        urlBuilder.addQueryParameter(JSONConstants.USER_NAME, userInfo.getUserName());
        urlBuilder.addQueryParameter(JSONConstants.USER_PASSWORD, userInfo.getPassword());
        urlBuilder.addQueryParameter(JSONConstants.USER_SURNAME, userInfo.getUserSurname());
        urlBuilder.addQueryParameter(JSONConstants.USER_PHONE, userInfo.getUserPhoneNumber());
        urlBuilder.addQueryParameter(JSONConstants.USER_PUBLIC_KEY, userInfo.getUserWallet());
        try {
            urlBuilder.addQueryParameter(JSONConstants.USER_BIRTH_DAY, Constants.parseDateFromServer.format(Constants.parseDateToShow.parse(userInfo.getUserBirthDay())));
        }
        catch (ParseException ps)
        {
            urlBuilder.addQueryParameter(JSONConstants.USER_BIRTH_DAY, Constants.parseDateFromServer.format(new Date()));

        }
        urlBuilder.addQueryParameter(JSONConstants.USER_DESCRIPTION, userInfo.getUserDescription());
        urlBuilder.addQueryParameter(JSONConstants.USER_AVATAR, userInfo.getUserAvatar());

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    jsonObjectMutableLiveData.postValue(new JSONObject(str));
                }
                catch (Exception ex)
                {

                }
            }
        });


        return  jsonObjectMutableLiveData;
    }


    public MutableLiveData<Boolean> checkIsPublicKeyExist(Context context, String key)
    {
        final MutableLiveData<Boolean> exist = new MutableLiveData<>();
        exist.setValue(false);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.CHECK_PUBLIC_KEY).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.USER_PUBLIC_KEY, key);
        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonObject = new JSONArray(response.body().string());
                    if(jsonObject.get(1).equals("0x0000000000000000000000000000000000000000"))
                        exist.postValue(false);
                    else
                        exist.postValue(true);
                }
                catch (Exception ex)
                {

                }
            }
        });
        return exist;

    }



}
