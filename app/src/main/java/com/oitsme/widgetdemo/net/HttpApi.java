// +----------------------------------------------------------------------
// | FileName:   ${file_name}  
// +----------------------------------------------------------------------
// | CreateTime: 15/6/28  @下午7:39
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.oitsme.widgetdemo.net;

import com.oitsme.widgetdemo.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author:  zhangkun .
 * date:    on 2017/8/1.
 */

public interface HttpApi {

    @GET("users/{login}")
    Call<User> getMovieTop(@Path("login") String start);
}
