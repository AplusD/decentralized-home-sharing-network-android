package com.aplusd.houserenter.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aplusd.houserenter.extra.HelpFunction;
import com.aplusd.houserenter.mainactivityui.MainActivity;
import com.aplusd.houserenter.model.JSONConstants;
import com.aplusd.houserenter.model.URLConstants;
import com.aplusd.houserenter.user.auth.AuthActivity;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
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

public class SplashActivity extends AppCompatActivity implements Callback{

    private int connectionAttempt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        auth();
    }

    public void auth()
    {
        if(SPHelper.getUserId(getBaseContext()) == -1 || SPHelper.getToken(getBaseContext()).equals("none"))
        {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        else if(!HelpFunction.isNetworkAvailable(getBaseContext()))
        {
            startActivity(new Intent (this, MainActivity.class));
            finish();
        }
        else {
            ++connectionAttempt;
            OkHttpClient client = new OkHttpClient();
            HttpUrl url = HttpUrl.parse(URLConstants.AUTH);
            HttpUrl.Builder urlBuilder = url.newBuilder();
            urlBuilder.addQueryParameter(JSONConstants.USER_ID, String.valueOf(SPHelper.getUserId(getBaseContext())));
            urlBuilder.addQueryParameter(JSONConstants.USER_TOKEN, SPHelper.getToken(getBaseContext()));
            client.newCall(new Request.Builder().url(urlBuilder.build()).build()).enqueue(this);
        }
    }


    @Override
    public void onFailure(Call call, IOException e) {
        if(connectionAttempt <= 1) {
            URLConstants.switchServer();
            auth();
        }
        else{
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try{
            JSONObject jsonObject = new JSONObject(response.body().string());
            if(jsonObject.getInt(JSONConstants.AUTH) == 0)
                startActivity(new Intent(this, AuthActivity.class));
            else
                startActivity(new Intent (this, MainActivity.class));
            finish();

        }
        catch (JSONException ex)
        {
            startActivity(new Intent(this, AuthActivity.class));
            ex.printStackTrace();
        }

    }
}
