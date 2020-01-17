package com.yt.myhttpdemo.http;


import android.os.Handler;
import android.os.Looper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @content:http请求回调处理类,用来替代Callback<T>
 * @time:2019-10-14
 * @build:zhouqiang
 */

public abstract class HttpCallback<T> implements Callback<T> {

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (response.code() == 401) {
                    //token为空或者失效处理
                    onResultFailure(call, response);
                    if (Http.getInstance().getTokenFailureHandler() != null) {
                        Http.getInstance().getTokenFailureHandler().runHandler();
                    }
                    return;
                }
                if (response.code() >= 500 && response.code() <= 600){
                    onNetworkFailure(call, new Throwable("服务器不在线 code="+response.code()));
                    return;
                }
                if (response.code() != 0 && response.code() != 200) {
                    onResultFailure(call, response);
                    return;
                }

                onResult(call, response);
            }
        });

    }

    @Override
    public void onFailure(final Call<T> call, final Throwable t) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onNetworkFailure(call, t);
            }
        });
    }

    public abstract void onResult(Call<T> call, Response<T> response);          //返回结果

    public abstract void onResultFailure(Call<T> call, Response<T> response); //服务器返回的失败结果

    public abstract void onNetworkFailure(Call<T> call, Throwable t);           //网络失败

}
