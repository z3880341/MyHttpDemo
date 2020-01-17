package com.yt.myhttpdemo.app;

import android.app.Application;

import com.yt.myhttpdemo.net.HttpConfig;
import com.yt.myhttpdemo.net.HttpList;
import com.yt.myhttpdemo.ResetTokenHandler;
import com.yt.myhttpdemo.http.Http;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化http网络请求框架
        Http.getInstance().init(HttpConfig.BASE_URL, HttpList.class, true);
        //设置token失效后的处理
        Http.getInstance().setAllTokenFailureHandler(new ResetTokenHandler());
        //设置全局token
        Http.getInstance().setToken(HttpConfig.TOKEN);
    }
}
