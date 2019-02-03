package com.tupperware.biz.utils;

import com.android.dhunter.common.config.GlobalConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dhunter on 2018/7/9.
 * 单独封装一个接口做重点热卖的7剑服务器的网络请求
 */

public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;


    private OkHttpClientManager() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(GlobalConfig.HTTP_READ_TIME_OUT, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(GlobalConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(GlobalConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);
        mOkHttpClient = clientBuilder.build();
    }

    public static OkHttpClientManager getInstance() {
        if(null == mInstance) {
            synchronized(OkHttpClientManager.class) {
                if(null == mInstance) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }

        RequestBody formBody = FormBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), addFormField(mapParams));
        Request request = new Request.Builder()
                .url(url)
//                .post(builder.build())
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送JSON数据
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public void doPost(String url, String jsonParams, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


    private byte[] addFormField(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String cval = entry.getValue();
            if (cval.startsWith("[") && cval.endsWith("]")) {
                String[] arr = cval.substring(1, cval.length() - 1).split(",");
                for (int i = 0, l = arr.length; i < l; i++) {
                    if(arr[i].isEmpty() || arr[i].equals("{}")) {
                        continue;
                    }
                    String[] vmap = arr[i].substring(1, arr[i].length() - 1).split(":"); //去掉大括号
                    stringBuilder.append(entry.getKey() + "[][" + vmap[0].replaceAll("\"", "") + "]").append("=").append(vmap[1].replaceAll("\"","") ).append("&");
                }
            } else {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return  stringBuilder.toString().getBytes();
    }

}
