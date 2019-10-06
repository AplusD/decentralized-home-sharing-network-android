package com.aplusd.houserenter.chat;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.aplusd.houserenter.chat.model.Message;
import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.user.SPHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static com.aplusd.houserenter.model.JSONConstants.CONTRACT_ID;
import static com.aplusd.houserenter.model.JSONConstants.MSG;
import static com.aplusd.houserenter.model.JSONConstants.USER_ID;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class ViewModelChat extends ViewModel {

    private MutableLiveData<ArrayList<Message>> messageMutableLiveData = null;

    public void sendMsg(int contractId, String msg, Context context)
    {
        HttpUrl url = HttpUrl.parse(URLConstants.SEND_MSG);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter(CONTRACT_ID, String.valueOf(contractId));
        urlBuilder.addQueryParameter(USER_ID, String.valueOf(SPHelper.getUserId(context)));
        urlBuilder.addQueryParameter(MSG, msg);
        HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    public MutableLiveData<ArrayList<Message>> getMessageMutableLiveData(int contractId, Context context) {

        if(messageMutableLiveData == null) {
            messageMutableLiveData = new MutableLiveData<>();
            messageMutableLiveData.setValue(null);


            HttpUrl url = HttpUrl.parse(URLConstants.GET_CHAT);
            HttpUrl.Builder urlBuilder = url.newBuilder();
            urlBuilder.addQueryParameter(CONTRACT_ID, String.valueOf(contractId));
            HelpFunction.client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        Gson gson = new Gson();
                        ArrayList<Message> markers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++)
                            markers.add(gson.fromJson(jsonArray.getString(i), Message.class));

                        messageMutableLiveData.postValue(markers);
                    } catch (JSONException js) {
                        js.printStackTrace();
                    }

                }
            });

        }
        return messageMutableLiveData;
    }
}
