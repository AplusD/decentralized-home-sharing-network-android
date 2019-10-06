package com.aplusd.houserenter.wallet;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aplusd.houserenter.wallet.model.CurrencyExchange;
import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.model.URLConstants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 15.05.2018
 */
public class ViewModelWallet extends ViewModel {

    public MutableLiveData<String> getAddressEthers(String address)
    {
        final MutableLiveData<String> valueOnWallet = new MutableLiveData<>();
        valueOnWallet.setValue(null);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLConstants.URL_ROPSTEN_NETWORK_API).newBuilder();
        urlBuilder.addQueryParameter("module", "account");
        urlBuilder.addQueryParameter("action", "balance");
        urlBuilder.addQueryParameter("address", address);
        urlBuilder.addQueryParameter("tag", "latest");
        urlBuilder.addQueryParameter("apikey", "YourApiKeyToken");

        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    if(jsonObject.has("result"))
                        valueOnWallet.postValue(jsonObject.getString("result"));
                }
                catch (Exception ex)
                {

                }
            }
        });
        return   valueOnWallet;
    }

    public MutableLiveData<CurrencyExchange> getCurrencyExchange()
    {
        final MutableLiveData<CurrencyExchange> currencyExchangeMutableLiveData = new MutableLiveData<>();
        currencyExchangeMutableLiveData.setValue(null);
        HelpFunction.client.newCall(new Request.Builder().url(URLConstants.URL_GET_ETHER_CURRENCY).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                CurrencyExchange currencyExchange = gson.fromJson(response.body().string(), CurrencyExchange.class);
                currencyExchangeMutableLiveData.postValue(currencyExchange);
            }
        });
        return currencyExchangeMutableLiveData;
    }
}
