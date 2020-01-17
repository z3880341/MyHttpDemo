package com.yt.myhttpdemo.net;

import com.yt.myhttpdemo.http.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface HttpList {

    @FormUrlEncoded
    @POST("test/my_http_test")
    Call<Result<MyHttpTestBean>> myHttpTest(@Field("sendContent") String sendContent);

}
