package com.oitsme.widgetdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oitsme.widgetdemo.model.User;
import com.oitsme.widgetdemo.net.HttpApi;
import com.oitsme.widgetdemo.net.RestApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_widget);

        HttpApi httpApi = RestApiAdapter.getHttpsRxStringInstance().create(HttpApi.class);
        Call<User> maoai_xianyu = httpApi.getMovieTop("maoai-xianyu");
        maoai_xianyu.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                LogU.showElog("成功");

                LogU.showElog(" "+response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                LogU.showElog("失败");

            }
        });
    }
}
