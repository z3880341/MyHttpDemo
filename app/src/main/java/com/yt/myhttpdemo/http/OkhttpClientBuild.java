package com.yt.myhttpdemo.http;

import android.annotation.SuppressLint;
import android.util.Log;


import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

class OkhttpClientBuild {
    private static final String TAG = "OkhttpClientBuild";
    private static OkHttpClient sTokenOkHttpClient;
    private static OkHttpClient sOkHttpClient;
    private static OkHttpClient sOkHttpDownloadClient;
    private static OkHttpClient sTokenUploadOkHttpClient;

    private static long CONNECT_TIME = 10;
    private static long READ_TIME = 10;
    private static long WRITE_TIME = 10;

    /**
     * 带token的okhttpClient
     * @return
     */
    protected static OkHttpClient getTokenOkHttpClient() {
        if (sTokenOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true);             //失败是否重连
            builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS); //连接超时
            builder.readTimeout(READ_TIME, TimeUnit.SECONDS);       //读取超时
            builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);     //写入超时
            builder.addInterceptor(new TokenInterceptor());         //设置Token拦截器
            builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
            if (Http.getInstance().isDebugMode()) {                 //设置网络日志拦截器
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(TAG,"网络日志: "+message);

                    }
                });
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(httpLoggingInterceptor);
            }
            sTokenOkHttpClient = builder.build();
        }
        return sTokenOkHttpClient;

    }

    /**
     * 不带token的okhttpClient
     * @return
     */
    protected static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true);             //失败是否重连
            builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS); //连接超时
            builder.readTimeout(READ_TIME, TimeUnit.SECONDS);       //读取超时
            builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);     //写入超时
            builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
            if (Http.getInstance().isDebugMode()) {                 //设置网络日志拦截器
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(TAG,"网络日志: "+message);

                    }
                });
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(httpLoggingInterceptor);
            }
            sOkHttpClient = builder.build();
        }
        return sOkHttpClient;

    }

    /**
     * 专门用于下载或上传文件使用的OkHttpClient
     * @return
     */
    protected static OkHttpClient getDownLoadOrUpLoadOkHttpClient() {
        if (sOkHttpDownloadClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true);                 //失败是否重连
            builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS); //连接超时
            builder.readTimeout(READ_TIME, TimeUnit.SECONDS);       //读取超时
            builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);     //写入超时
            sOkHttpDownloadClient = builder.build();
        }
        return sOkHttpDownloadClient;

    }

    /**
     * 带token的上传文件使用的okhttpClient
     * @return
     */
    protected static OkHttpClient getTokenUploadOkHttpClient() {
        if (sTokenUploadOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true);             //失败是否重连
            builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS); //连接超时
            builder.readTimeout(READ_TIME, TimeUnit.SECONDS);       //读取超时
            builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);     //写入超时
            builder.addInterceptor(new TokenInterceptor());         //设置Token拦截器
            builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
            sTokenUploadOkHttpClient = builder.build();
        }
        return sTokenUploadOkHttpClient;

    }

    private static class TrustAllCerts implements X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{
                    new TrustAllCerts()
            }, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

}
