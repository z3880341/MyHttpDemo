package com.yt.myhttpdemo.http;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @content:token的拦截器类,全局token在这里添加
 * @time:2019-10-14
 * @build:zhouqiang
 */

 class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String token = Http.getInstance().getToken();
        builder.addHeader("token", TextUtils.isEmpty(token) ? "" : token); //增加token
        Request request = builder.build();
        return chain.proceed(request);
    }
}
