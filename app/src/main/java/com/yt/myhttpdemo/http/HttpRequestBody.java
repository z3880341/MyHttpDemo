package com.yt.myhttpdemo.http;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *@content:请求使用的body创建类
 *@time:2019-10-14
 *@build:zhouqiang
 */

public class HttpRequestBody {
    /**
     * 创建Json类型的body
     * @param jsonContent
     * @return
     */
    public static RequestBody createJson(String jsonContent) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonContent);
    }
}
