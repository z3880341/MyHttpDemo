package com.yt.myhttpdemo.http;

import androidx.annotation.NonNull;

/**
 *@content:Bean的基类,请在在接口服务里用Call<Result<Data>>的形式使用它
 *@time:2019-10-14
 *@build:zhouqiang
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private Boolean success;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
