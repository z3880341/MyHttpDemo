package com.yt.myhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yt.myhttpdemo.http.Http;
import com.yt.myhttpdemo.http.HttpCallback;
import com.yt.myhttpdemo.http.Result;
import com.yt.myhttpdemo.net.HttpList;
import com.yt.myhttpdemo.net.MyHttpTestBean;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button mPostTestHttpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPostTestHttpBtn = findViewById(R.id.post_test_http);

        mPostTestHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTestHttp();

            }
        });
    }


    private void postTestHttp(){
        HttpList httpList = Http.getInstance().getRequest();
        Call<Result<MyHttpTestBean>> call = httpList.myHttpTest("测试");
        call.enqueue(new HttpCallback<Result<MyHttpTestBean>>() {
            @Override
            public void onResult(Call<Result<MyHttpTestBean>> call, Response<Result<MyHttpTestBean>> response) {
                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResultFailure(Call<Result<MyHttpTestBean>> call, Response<Result<MyHttpTestBean>> response) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNetworkFailure(Call<Result<MyHttpTestBean>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
