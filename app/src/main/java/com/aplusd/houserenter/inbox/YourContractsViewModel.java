package com.aplusd.houserenter.inbox;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.inbox.model.Offering;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.user.SPHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static com.aplusd.houserenter.model.JSONConstants.CONTRACT_ID;
import static com.aplusd.houserenter.model.JSONConstants.CONTRACT_PRICE;
import static com.aplusd.houserenter.model.JSONConstants.CONTRACT_STATUS;
import static com.aplusd.houserenter.model.JSONConstants.DATE_FROM;
import static com.aplusd.houserenter.model.JSONConstants.DATE_TO;
import static com.aplusd.houserenter.model.JSONConstants.HOUSE_ID;
import static com.aplusd.houserenter.model.JSONConstants.PARTNER_WALLET;
import static com.aplusd.houserenter.model.JSONConstants.PRICE_WEI;
import static com.aplusd.houserenter.model.JSONConstants.USER_ID;
import static com.aplusd.houserenter.model.JSONConstants.USER_PRIVATE_KEY;
import static com.aplusd.houserenter.model.JSONConstants.USER_PUBLIC_KEY;
import static com.aplusd.houserenter.model.JSONConstants.USER_TOKEN;

/**
 * @author Azamat Dzhonov
 * @date 24.04.2018
 */

public class YourContractsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Offering>> offeringMutableLiveData = null;
    private MutableLiveData<ArrayList<Offering>> trips = null;

    public MutableLiveData<ArrayList<Offering>> getTrips(Context context) {

        if (trips == null) {
            trips = new MutableLiveData<>();
            trips.setValue(null);
        }

            HttpUrl url = HttpUrl.parse(URLConstants.GET_YOUR_TRIPS);
            HttpUrl.Builder urlBuilder = url.newBuilder();
            urlBuilder.addQueryParameter(USER_ID, String.valueOf(SPHelper.getUserId(context)));
            urlBuilder.addQueryParameter(USER_TOKEN, String.valueOf(SPHelper.getToken(context)));

            HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        Gson gson = new Gson();
                        ArrayList<Offering> markers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            markers.add(gson.fromJson(jsonArray.getString(i), Offering.class));
                        }
                        trips.postValue(markers);
                    } catch (JSONException js) {
                        js.printStackTrace();
                    }

                }
            });

        return trips;
    }


    public MutableLiveData<ArrayList<Offering>> getOfferings(Context context)
    {
        if(offeringMutableLiveData == null) {
            offeringMutableLiveData = new MutableLiveData<>();
            offeringMutableLiveData.setValue(null);
        }

        HttpUrl url = HttpUrl.parse(URLConstants.GET_YOUR_OFFERINGS);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(USER_ID, String.valueOf(SPHelper.getUserId(context)));
        urlBuilder.addQueryParameter(USER_TOKEN, String.valueOf(SPHelper.getToken(context)));
        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                        Gson gson = new Gson();
                        ArrayList<Offering> markers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for(int i = 0; i < jsonArray.length(); i++)
                            markers.add(gson.fromJson(jsonArray.getString(i), Offering.class));

                        offeringMutableLiveData.postValue(markers);
                    }
                    catch (JSONException js)
                    {
                        js.printStackTrace();
                    }

                }
             });

        return offeringMutableLiveData;
    }


    public MutableLiveData<Integer> finishContract(Context context, Offering offering)
    {
        final MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        HttpUrl url = HttpUrl.parse(URLConstants.FINISH_CONTRACT);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(CONTRACT_ID, String.valueOf(offering.getContractId()));
        urlBuilder.addQueryParameter(HOUSE_ID, String.valueOf(offering.getHouseId()));
        urlBuilder.addQueryParameter(PARTNER_WALLET, String.valueOf(offering.getPartnerWallet()));
        urlBuilder.addQueryParameter(USER_PRIVATE_KEY, String.valueOf(SPHelper.getPrivateKey(context)));
        urlBuilder.addQueryParameter(USER_PUBLIC_KEY, String.valueOf(SPHelper.getPublicKey(context)));
        urlBuilder.addQueryParameter(CONTRACT_PRICE, String.valueOf(offering.getContractPriceEther()));

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mutableLiveData.postValue(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.has("RESULT"))
                        mutableLiveData.postValue(jsonObject.getInt("RESULT"));
                }
                catch (JSONException js)
                {
                    mutableLiveData.postValue(0);
                }

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> cancelContract(Context context, int contractId)
    {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        HttpUrl url = HttpUrl.parse(URLConstants.CANCEL_TRIP);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(CONTRACT_ID, String.valueOf(contractId));

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mutableLiveData.postValue(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.has("RESULT"))
                        mutableLiveData.postValue(jsonObject.getInt("RESULT") == 1);
                }
                catch (JSONException js)
                {
                    mutableLiveData.postValue(false);
                }

            }
        });
        return mutableLiveData;
    }



    public MutableLiveData<Boolean> updateContractStatus(Context context, Offering offering, int contractStatus)
    {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        HttpUrl url = HttpUrl.parse(URLConstants.UPDATE_CONTRACT_STATUS);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(HOUSE_ID, String.valueOf(offering.getHouseId()));
        urlBuilder.addQueryParameter(CONTRACT_ID, String.valueOf(offering.getContractId()));
        urlBuilder.addQueryParameter(CONTRACT_STATUS, String.valueOf(contractStatus));
        urlBuilder.addQueryParameter(DATE_FROM, offering.getDateFrom());
        urlBuilder.addQueryParameter(DATE_TO, offering.getDateTo());
        urlBuilder.addQueryParameter(PARTNER_WALLET, offering.getPartnerWallet());
        urlBuilder.addQueryParameter(PRICE_WEI, String.valueOf(offering.getContractPriceEther()));

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mutableLiveData.postValue(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.has("RESULT"))
                        mutableLiveData.postValue(jsonObject.getInt("RESULT") == 1);
                }
                catch (JSONException js)
                {
                    mutableLiveData.postValue(false);
                }

            }
        });
        return mutableLiveData;
    }


}
