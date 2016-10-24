package com.zk.my.demo01.util;


import com.zk.my.demo01.constant.URLConstants;
import com.zk.my.demo01.info.DouyuBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by My on 2016/10/24.
 */
public class Httputils {
    private Retrofit mRetrofit;
    private  RetrofitApi mApi;

    //私有构造器 私有静态对象 公有静态方法
    private static Httputils httputils;
    private Httputils(){
        //retrofit初始化
        mRetrofit=new Retrofit.Builder()
                .baseUrl(URLConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(RetrofitApi.class);
    }
    public static Httputils newInstance(){
        if(httputils==null){
            httputils= new Httputils();
        }
        return httputils;
    }
    //???封装的方法
    public void getFace(Map<String, String> params, Callback<DouyuBean> callback){
        Call<DouyuBean> call = mApi.getFace(params);
        call.enqueue(callback);
    }

}
