package com.aplusd.houserenter.extra;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Azamat Dzhonov
 * @date 20.04.2018
 */

public class IntercepterCache implements Interceptor {

    private Context context = null;

    public IntercepterCache(Context context)
    {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (HelpFunction.isNetworkAvailable(context)) {
            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
        } else {
            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
        }
        return chain.proceed(request);
    }



}
