// +----------------------------------------------------------------------
// | FileName:   ${file_name}  
// +----------------------------------------------------------------------
// | CreateTime: 15/6/28  @下午7:36
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.oitsme.widgetdemo.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * zhangkun
 */
public class RestApiAdapter {

    private static Retrofit rxHttpsStringInstance;

    private static OkHttpClient client;


    public static Retrofit getHttpsRxStringInstance() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }

        if (rxHttpsStringInstance == null) {
            rxHttpsStringInstance = new Builder().baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return rxHttpsStringInstance;
    }

    public static void clean() {
        rxHttpsStringInstance = null;
        client = null;
    }

}
