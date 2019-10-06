package com.aplusd.houserenter.leaselodging;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.extra.IntercepterCache;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.LodgingType;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.user.SPHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 04.04.2018
 */

public class ViewModelNewLodging extends android.arch.lifecycle.ViewModel {


    private MutableLiveData<ArrayList<LodgingType>> leaseTypes = null;
    private MutableLiveData<Lodging> newLodging = null;


    public MutableLiveData<ArrayList<LodgingType>> getTypesHouse(Context context)
    {
        if(leaseTypes == null)
        {
            leaseTypes = new MutableLiveData<>();

            File cacheFile = new File(context.getCacheDir(), "houseTypesCache");
            if(!cacheFile.isDirectory())
                cacheFile.mkdirs();

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .cache(new Cache(cacheFile, 10*1000*1000)).addInterceptor(new IntercepterCache(context)).build();

            Request request = new Request.Builder().url(URLConstants.GET_HOUSE_TYPES).build();
            HelpFunction.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    try {
                        ArrayList<LodgingType> types = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for(int i = 0; i < jsonArray.length(); i++)
                            types.add(gson.fromJson(jsonArray.getString(i), LodgingType.class));

                        leaseTypes.postValue(types);
                    }
                    catch (JSONException ex)
                    {

                    }

                }
            });

        }
        return leaseTypes;

    }

    public MutableLiveData<Boolean> addLodgingImg(int houseId, String path)
    {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.ADD_LODGING_IMG).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ID, String.valueOf(houseId));
        urlBuilder.addQueryParameter(JSONConstants.IMG_PATH, String.valueOf(path));
        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt("RESULT") == 1)
                        mutableLiveData.postValue(true);
                    else
                        mutableLiveData.postValue(false);
                }
                catch (Exception ex)
                {

                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Lodging> getNewLodging()
    {
        if(newLodging == null) {
            newLodging = new MutableLiveData<Lodging>();
            newLodging.setValue(new Lodging());
        }
        return newLodging;
    }

    public MutableLiveData<Integer> uploadLodging(Context context)
    {
        final MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.ADD_LEASE).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.USER_ID, String.valueOf(SPHelper.getUserId(context)));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_NAME, String.valueOf(newLodging.getValue().getHouseName()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ORDER_TYPE, String.valueOf(newLodging.getValue().getHouseOrderType()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_GUEST_COUNT, String.valueOf(newLodging.getValue().getHouseGuestCount()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_BIO, String.valueOf(newLodging.getValue().getHouseDescription()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ADDRESS, String.valueOf(newLodging.getValue().getHouseAddress()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_MAIN_IMAGE, String.valueOf(newLodging.getValue().getHouseMainImg()));
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_PRICE, String.valueOf(newLodging.getValue().getDayPrice()));


        if(newLodging.getValue().getLat() != null)
            urlBuilder.addQueryParameter(JSONConstants.HOUSE_LAT, String.valueOf(newLodging.getValue().getLat()));
        else
            urlBuilder.addQueryParameter(JSONConstants.HOUSE_LAT, String.valueOf(55.75324));

        if(newLodging.getValue().getLng() != null)
             urlBuilder.addQueryParameter(JSONConstants.HOUSE_LNG, String.valueOf(newLodging.getValue().getLng()));
        else
            urlBuilder.addQueryParameter(JSONConstants.HOUSE_LNG, String.valueOf(37.615468));

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt("RESULT") == 1)
                        mutableLiveData.postValue(jsonObject.getInt("houseId"));
                    else
                        mutableLiveData.postValue(-1);
                }
                catch (Exception ex)
                {
                    mutableLiveData.postValue(-1);
                }
            }
        });
        return mutableLiveData;

    }

    public MutableLiveData<Boolean> setMainImg(int houseId, String path)
    {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.SET_MAIN_IMG).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ID, String.valueOf(houseId));
        urlBuilder.addQueryParameter(JSONConstants.IMG_PATH, path);

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt("RESULT") == 1)
                        mutableLiveData.postValue(true);
                    else
                        mutableLiveData.postValue(false);
                }
                catch (Exception ex)
                {

                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> publishHouse(int houseId)
    {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.PUBLISH_LODGING).newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ID, String.valueOf(houseId));

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt("RESULT") == 1)
                        mutableLiveData.postValue(true);
                    else
                        mutableLiveData.postValue(false);
                }
                catch (Exception ex)
                {

                }
            }
        });
        return mutableLiveData;
    }

}
