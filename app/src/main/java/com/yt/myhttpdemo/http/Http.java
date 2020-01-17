package com.yt.myhttpdemo.http;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @content:Http请求创建类
 * @time:2019-8-6
 * @build:zhouqiang
 */

public class Http {
    private static final String TAG = Http.class.getSimpleName();
    private static Http sHttp;
    private Retrofit mTokenRetrofit;
    private Retrofit mRetrofit;
    private Retrofit mNewUrlRetrofit;
    private String mBaseUrl = null;
    private String mToken = null;
    private Class mService = null;
    private TokenFailureHandler mTokenFailureHandler = null;
    private boolean isDebugMode = true;


    private Http() {

    }

    public static Http getInstance() {
        if (sHttp == null) {
            sHttp = new Http();
        }
        return sHttp;
    }

    /**
     * 初始化,只建议在App类里初始化
     *
     * @param baseUrl   全局使用的基础url
     * @param service   全局使用的接口Http列表类
     * @param debugMode 是否开启debug模式,开启debug模式会打印网络日志,true=开始 false=关闭
     */
    public void init(String baseUrl, Class service, boolean debugMode) {
        mBaseUrl = baseUrl;
        mService = service;
        isDebugMode = debugMode;

    }

    /**
     * 设置全局token
     *
     * @param token
     */
    public void setToken(String token) {
        mToken = token;

    }

    /**
     * 设置全局token失败后处理,请接口TokenFailureHandler并且重写token失败后的处理
     *
     * @param tokenFailureHandler
     * @see TokenFailureHandler
     */
    public void setAllTokenFailureHandler(@NonNull TokenFailureHandler tokenFailureHandler) {
        mTokenFailureHandler = tokenFailureHandler;

    }

    protected boolean isDebugMode() {
        return isDebugMode;
    }

    protected String getToken() {
        return mToken;
    }

    protected TokenFailureHandler getTokenFailureHandler() {
        return mTokenFailureHandler;
    }

    /**
     * 使用带token的请求
     *<p>
     *     注意！这个方法请求的接口会打印网络日志，请千万不要用于下载和上传文件
     *</p>
     * @param <T>
     * @return
     */
    public <T> T getTokenRequest() {
        if (mTokenFailureHandler == null) {
            Log.e(TAG, "TokenFailureHandler 不能为空");
            return null;
        }
        if (TextUtils.isEmpty(mBaseUrl)) {
            Log.e(TAG, "baseUrl 不能为空");
            return null;
        }
        if (mService == null) {
            Log.e(TAG, "请求服务类 不能为空");
            return null;
        }
        if (mTokenRetrofit == null) {
            mTokenRetrofit = new Retrofit.Builder()
                    .client(OkhttpClientBuild.getTokenOkHttpClient())
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
        }
        return (T) mTokenRetrofit.create(mService);

    }

    /**
     * 使用带token的请求,并且可以更换基础url和接口服务列表类
     *<p>
     *     注意！这个方法请求的接口会打印网络日志，请千万不要用于下载和上传文件
     *</p>
     * @param baseUrl 独立的Url
     * @param service 独立的接口服务
     * @param <T>
     * @return
     */
    public <T> T getTokenRequest(@NonNull String baseUrl, @NonNull Class service) {
        mNewUrlRetrofit = new Retrofit.Builder()
                .client(OkhttpClientBuild.getTokenOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        return (T) mNewUrlRetrofit.create(service);

    }

    /**
     * 使用不带token的请求
     *<p>
     *     注意！这个方法请求的接口会打印网络日志，请千万不要用于下载和上传文件
     *</p>
     * @param <T>
     * @return
     */
    public <T> T getRequest() {
        if (TextUtils.isEmpty(mBaseUrl)) {
            Log.e(TAG, "baseUrl 不能为空");
            return null;
        }
        if (mService == null) {
            Log.e(TAG, "请求服务类 不能为空");
            return null;
        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(OkhttpClientBuild.getOkHttpClient())
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
        }
        return (T) mRetrofit.create(mService);
    }

    /**
     * 使用不带token的请求,并且可以更换基础url和接口服务列表类
     *<p>
     *     注意！这个方法请求的接口会打印网络日志，请千万不要用于下载和上传文件
     *</p>
     * @param baseUrl 独立的Url
     * @param service 独立的接口服务
     * @param <T>
     * @return
     */
    public <T> T getRequest(@NonNull String baseUrl, @NonNull Class service) {
        mNewUrlRetrofit = new Retrofit.Builder()
                .client(OkhttpClientBuild.getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        return (T) mNewUrlRetrofit.create(service);
    }

    /**
     * 下载方法
     * @param url url 下载地址
     * @return 返回下载的Call<ResponseBody> 提供流的保存操作
     */
    public Call<ResponseBody> downloadFile(@NonNull String url){
        Retrofit downloadFileRetrofit = new Retrofit.Builder()
                .client(OkhttpClientBuild.getDownLoadOrUpLoadOkHttpClient())
                .baseUrl("http://download/")   //必需写一个以 / 结尾的url,所以随便写一个地址.下载的url会自动全局替代这个地址
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        return downloadFileRetrofit.create(DownloadHttp.class).download(url);

    }

    /**
     *  带token的上传文件
     * @param <T>
     * @return
     */
    public <T> T tokenUploadFile(){
        Retrofit uploadFileRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkhttpClientBuild.getTokenUploadOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        return (T) uploadFileRetrofit.create(mService);
    }

    /**
     *  不带token的上传文件
     * @param <T>
     * @return
     */
    public <T> T uploadFile(){
        Retrofit uploadFileRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkhttpClientBuild.getDownLoadOrUpLoadOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        return (T) uploadFileRetrofit.create(mService);
    }


    /*后台服务器地址s*/
    public static RequestBody getRequestBody(String string) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
    }
}
