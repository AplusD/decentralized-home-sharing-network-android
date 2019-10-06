package com.aplusd.houserenter.rentlodging.contract;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.model.Constants;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.rentlodging.model.BookedDates;
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.aplusd.houserenter.user.SPHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.aplusd.houserenter.model.JSONConstants.DATE_FROM;
import static com.aplusd.houserenter.model.JSONConstants.DATE_TO;
import static com.aplusd.houserenter.model.JSONConstants.GUEST_COUNT;
import static com.aplusd.houserenter.model.JSONConstants.GUEST_ID;
import static com.aplusd.houserenter.model.JSONConstants.HOUSE_ID;
import static com.aplusd.houserenter.model.JSONConstants.MSG;
import static com.aplusd.houserenter.model.JSONConstants.PRICE;
import static com.aplusd.houserenter.model.JSONConstants.PRICE_WEI;
import static com.aplusd.houserenter.model.JSONConstants.USER_PUBLIC_KEY;

/**
 * @author Azamat Dzhonov
 * @date 24.04.2018
 */

public class ViewModelContract extends ViewModel {

    private Lodging lodging = null;
    private OkHttpClient client = new OkHttpClient();

    private MutableLiveData<Contract> contract = null;

    public interface CallBackStartContract
    {
        void success();
        void failed();
    }

    public void startContract(Context context, final CallBackStartContract callBackStartContract)
    {

        HttpUrl url = HttpUrl.parse(URLConstants.START_CONTRACT);
        HttpUrl.Builder urlBuilder = url.newBuilder();

        urlBuilder.addQueryParameter(HOUSE_ID, String.valueOf(lodging.getHouseId()));
        urlBuilder.addQueryParameter(GUEST_ID, String.valueOf(SPHelper.getUserId(context)));
        urlBuilder.addQueryParameter(GUEST_COUNT, String.valueOf(contract.getValue().getPeopleCount()));

        urlBuilder.addQueryParameter(DATE_FROM,
                new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_TO_SERVER).format(contract.getValue().getFromDate()));

        urlBuilder.addQueryParameter(DATE_TO,
                new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_TO_SERVER).format(contract.getValue().getToDate()));

        urlBuilder.addQueryParameter(PRICE_WEI, String.valueOf(contract.getValue().getPriceWei()));
        urlBuilder.addQueryParameter(PRICE, String.valueOf(lodging.getDayPrice() * contract.getValue().getDates().size()));
        urlBuilder.addQueryParameter(MSG, String.valueOf(contract.getValue().getHostMsg()));
        urlBuilder.addQueryParameter(USER_PUBLIC_KEY, SPHelper.getPublicKey(context));

        client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt(JSONConstants.RESULT) == 1)
                        callBackStartContract.success();
                    else
                        callBackStartContract.failed();
                }
                catch (JSONException js)
                {
                    js.printStackTrace();
                }

            }
        });

    }


    public MutableLiveData<Contract> getNewContract()
    {
        if(contract == null) {
            contract = new MutableLiveData<>();
            contract.setValue(new Contract());
        }
        return contract;
    }

    public void setLodging(Lodging lodging)
    {
        this.lodging = lodging;
    }

    public Lodging getLodging()
    {
        return lodging;
    }


    public MutableLiveData<ArrayList<BookedDates>> getLodgingNotAvailableDate(Context context){
        final MutableLiveData<ArrayList<BookedDates>> data = new MutableLiveData<>();
        data.setValue(null);

        HttpUrl url = HttpUrl.parse(URLConstants.CHECK_LODGING_AVAILABILITY);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(JSONConstants.HOUSE_ID, String.valueOf(lodging.getHouseId()));
        client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ArrayList<BookedDates> dates = new ArrayList<BookedDates>();
                    String str = response.body().string();
                    JSONArray jsonArray = new JSONArray(str);
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        try {
                            BookedDates bookedDates = new BookedDates();
                            bookedDates.setFrom(Constants.parseDateFromServer.parse(jsonArray.getJSONObject(i).getString("dateFrom")));
                            bookedDates.setTo(Constants.parseDateFromServer.parse(jsonArray.getJSONObject(i).getString("dateTo")));
                            dates.add(bookedDates);
                        }
                        catch (ParseException js)
                        {

                        }
                    }
                    data.postValue(dates);

                }
                catch (JSONException s)
                {

                }
            }
        });

        return data;
    }



}
