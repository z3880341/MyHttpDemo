package com.yt.myhttpdemo;

import android.text.TextUtils;


import com.yt.myhttpdemo.http.Http;
import com.yt.myhttpdemo.http.HttpRequestBody;
import com.yt.myhttpdemo.http.TokenFailureHandler;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author zhouqiang
 * @content 重置token处理类, 用于token失效后的重新获取
 * @time 2019-10-14
 */

public class ResetTokenHandler implements TokenFailureHandler {
    @Override
    public void runHandler() {
        //处理token获取失败

    }
}
