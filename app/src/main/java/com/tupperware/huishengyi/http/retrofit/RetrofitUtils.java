package com.tupperware.huishengyi.http.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhunter on 2018/4/20.
 * 封装一个retrofit集成0kHttp3的抽象基类
 */

public abstract class RetrofitUtils {

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected static Retrofit getRetrofit(String baseurl) {

//        if (null == mRetrofit) {

        if (null == mOkHttpClient) {
            mOkHttpClient = OkHttp3Utils.getOkHttpClient();
        }

        //Retrofit2后使用build设计模式
        mRetrofit = new Retrofit.Builder()
                //设置服务器路径
                .baseUrl(baseurl)
                //添加转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create())
                //添加回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置使用okhttp网络请求
                .client(mOkHttpClient)
                .build();
//        }

        return mRetrofit;
    }


}
