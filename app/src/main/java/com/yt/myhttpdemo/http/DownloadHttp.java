package com.yt.myhttpdemo.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

interface DownloadHttp {

    @Streaming //注解这个请求将获取数据流,此后将不会这些获取的请求数据保存到内存中,将交与你操作.
    @GET
    Call<ResponseBody> download(@Url String url);
}
