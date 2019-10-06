package com.aplusd.houserenter.user.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.aplusd.houserenter.extra.IntercepterCache;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.user.SPHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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

public class UserInfoLiveData extends LiveData<UserInfo> {

    public UserInfoLiveData(Context context)
    {
        File cacheFile = new File(context.getCacheDir(), "userInfoCache");
        if(!cacheFile.isDirectory())
            cacheFile.mkdirs();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(2, TimeUnit.SECONDS)
                .cache(new Cache(cacheFile, 10*1000*1000)).addInterceptor(new IntercepterCache(context)).build();


        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.GET_USER_INFO).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.USER_ID, String.valueOf(SPHelper.getUserId(context)));

        okHttpClient.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                postValue(new Gson().fromJson(response.body().string(), UserInfo.class));
            }
        });
    }
}
