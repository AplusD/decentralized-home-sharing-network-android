package com.aplusd.houserenter.rentlodging.availablelodgings;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.extra.IntercepterCache;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.LodgingImg;
import com.aplusd.houserenter.model.LodgingMarker;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.rentlodging.model.LiveDataLodgings;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.aplusd.houserenter.model.JSONConstants.CITY;
import static com.aplusd.houserenter.model.JSONConstants.COUNTRY;
import static com.aplusd.houserenter.model.JSONConstants.HOST_INFO;
import static com.aplusd.houserenter.model.JSONConstants.LAT;
import static com.aplusd.houserenter.model.JSONConstants.LNG;
import static com.aplusd.houserenter.model.JSONConstants.LOCATION;
import static com.aplusd.houserenter.model.JSONConstants.LODGING_PHOTOS;
import static com.aplusd.houserenter.model.JSONConstants.PLACE;

/**
 * @author Azamat Dzhonov
 * @date 07.03.2018
 */

public class ViewModelLodgingInformation extends ViewModel {

    private LiveDataLodgings availableLodgings = null;

    private MutableLiveData<ArrayList<LodgingMarker>> leaseMarker = null;

    private OkHttpClient client = new OkHttpClient();


    public LiveDataLodgings getAvailableLeases(Context context)
    {
        if(availableLodgings == null)
            availableLodgings = new LiveDataLodgings(context);
        return availableLodgings;
    }


    public MutableLiveData<Lodging> getLodgingInfo(final Lodging lodging)
    {
        final MutableLiveData<Lodging> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(lodging);

        HttpUrl url = HttpUrl.parse(URLConstants.GET_LODGINGINFO);
        HttpUrl.Builder urlBuilder = url.newBuilder();

        urlBuilder.addQueryParameter("cityCode", String.valueOf(lodging.getCityCode()));
        urlBuilder.addQueryParameter("houseId", String.valueOf(lodging.getHouseId()));
        urlBuilder.addQueryParameter("userId", String.valueOf(lodging.getUserId()));


        client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Gson gson = new Gson();

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.has(LOCATION)) {
                        JSONObject location = jsonObject.getJSONObject(LOCATION);
                        lodging.setLat(location.getDouble(LAT));
                        lodging.setLng(location.getDouble(LNG));

                    }
                    if(jsonObject.has(PLACE))
                    {
                        JSONObject place = jsonObject.getJSONObject(PLACE);
                        lodging.setPlace(place.getString(CITY) +  ", " + place.getString(COUNTRY));
                    }
                    if(jsonObject.has(LODGING_PHOTOS))
                    {
                        ArrayList<LodgingImg> lodgingImgs = new ArrayList<>();
                        JSONArray photos = jsonObject.getJSONArray(LODGING_PHOTOS);
                        for(int i = 0; i < photos.length(); i++)
                        {
                            lodgingImgs.add(gson.fromJson(photos.getString(i), LodgingImg.class));
                        }
                        lodging.setLodgingImgs(lodgingImgs);
                    }
                    if(jsonObject.has(HOST_INFO))
                    {
                        UserInfo userInfo = gson.fromJson(jsonObject.getString(HOST_INFO), UserInfo.class);
                        lodging.setUserInfo(userInfo);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }

               mutableLiveData.postValue(lodging);
            }
        });

        return mutableLiveData;
    }


    public MutableLiveData<ArrayList<LodgingMarker>> getMarkers(Context context)
    {
        if(leaseMarker == null)
        {
            leaseMarker = new MutableLiveData<>();
            File cacheFile = new File(context.getCacheDir(), "pointsCache");

            if(!cacheFile.isDirectory())
                cacheFile.mkdirs();

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().readTimeout(5, TimeUnit.SECONDS)
                    .cache(new Cache(cacheFile, 10*1000*1000))
                    .addInterceptor(new IntercepterCache(context)).build();

            Request request = new Request.Builder().url(URLConstants.GET_LEASES_WITH_LOCATION).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    try {
                        ArrayList<LodgingMarker> markers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            markers.add(gson.fromJson(jsonArray.getString(i), LodgingMarker.class));
                        }
                        leaseMarker.postValue(markers);
                    }
                    catch (JSONException ex)
                    {

                    }
                }
            });
        }
        return leaseMarker;
    }


    public MutableLiveData<Boolean> checkLodgingAvailability(Context context, int houseID)
    {
        final MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
        booleanMutableLiveData.setValue(null);
        HttpUrl url = HttpUrl.parse(URLConstants.CHECK_LODGING_AVAILABILITY);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ID, String.valueOf(houseID));
        client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                booleanMutableLiveData.postValue(true);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    JSONObject jsonArray = new JSONObject(str);
                    if(jsonArray.has("isAvailable"))
                        booleanMutableLiveData.postValue(jsonArray.getBoolean("isAvailable"));
                }
                catch (JSONException s)
                {
                    booleanMutableLiveData.postValue(true);
                }
            }
        });
        return booleanMutableLiveData;
    }


}
