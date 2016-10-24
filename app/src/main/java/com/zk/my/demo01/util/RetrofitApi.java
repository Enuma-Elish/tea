package com.zk.my.demo01.util;

import com.zk.my.demo01.constant.URLConstants;
import com.zk.my.demo01.info.DouyuBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by My on 2016/10/24.
 */
public interface RetrofitApi {
    @GET(URLConstants.Path.path)
    Call<DouyuBean> getFace(@QueryMap() Map<String, String> params);
}
