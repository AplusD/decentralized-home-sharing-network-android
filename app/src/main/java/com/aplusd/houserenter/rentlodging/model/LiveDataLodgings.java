package com.aplusd.houserenter.rentlodging.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.aplusd.houserenter.extra.IntercepterCache;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.URLConstants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 23.04.2018
 */

public class LiveDataLodgings extends LiveData<ArrayList<Lodging>> {

    public LiveDataLodgings(Context context)
    {
        requestData(context);
    }

    public void requestData(Context context)
    {
        File cacheFile = new File(context.getCacheDir(), "lodgingCache");

        if(!cacheFile.isDirectory())
            cacheFile.mkdirs();

        Request request = new Request.Builder().url(URLConstants.GET_AVAILABLE_LEASES).build();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().readTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(cacheFile, 10*1000*1000)).addInterceptor(new IntercepterCache(context)).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Gson gson = new Gson();
                    ArrayList<Lodging> lodgings = new ArrayList<>();
                    JSONArray jsonObject = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonObject.length(); i++)
                        lodgings.add(gson.fromJson(jsonObject.getString(i), Lodging.class));

                    postValue(lodgings);
                }
                catch (JSONException ex)
                {

                }
            }
        });
    }
}
