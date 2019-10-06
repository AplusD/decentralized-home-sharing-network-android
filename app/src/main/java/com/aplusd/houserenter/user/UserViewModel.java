package com.aplusd.houserenter.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.user.model.UserInfoLiveData;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 20.04.2018
 */

public class UserViewModel extends ViewModel {

    public interface CallBackAuth
    {
         void authSucced();
         void authFailed();
    }

    private UserInfoLiveData userInfo = null;




   public void auth(final Context context, final String userEmail, final String userPassword, final CallBackAuth auth)
   {
       final MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
       userInfoLiveData.setValue(null);

       HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.LOGIN).newBuilder();
       urlBuilder.addQueryParameter(JSONConstants.USER_EMAIL, userEmail);
       urlBuilder.addQueryParameter(JSONConstants.USER_PASSWORD, userPassword);

       OkHttpClient okHttpClient = new OkHttpClient();
       okHttpClient.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {
                    URLConstants.switchServer();
                    auth.authFailed();
               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
                   try {
                       String str = response.body().string();
                       UserInfo userInfo = new Gson().fromJson(str, UserInfo.class);
                       SPHelper.setUserInfo(context, userInfo);
                       auth.authSucced();
                   }
                   catch (Exception ex)
                   {
                       auth.authFailed();
                   }
               }
           });
   }


    public UserInfoLiveData getUserInfo(Context context)
    {
        if(userInfo == null)
            userInfo = new UserInfoLiveData(context);
        return userInfo;
    }

}
