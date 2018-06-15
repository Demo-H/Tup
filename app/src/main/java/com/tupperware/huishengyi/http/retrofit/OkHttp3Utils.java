package com.tupperware.huishengyi.http.retrofit;

import com.tupperware.huishengyi.app.BaseApplication;
import com.android.dhunter.common.utils.FileUtils;
import com.tupperware.huishengyi.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dhunter on 2018/4/20.
 * okHttp的配置
 * 缓存已经添加  目前只支持GET请求的缓存
 * 也可根据自己的需要进行相关的修改
 */

public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(FileUtils.DIR_CACHE, "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 100 * 1024 * 1024);


    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置一个自动管理cookies的管理器
//                    .cookieJar(new CookiesManager())
                    //添加拦截器
                    .addInterceptor(mTokenInterceptor)
                    .addNetworkInterceptor(TestInterceptor)
                    .addInterceptor(TestInterceptor)
                    //添加网络连接器
//                    .addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    // 错误重连
                    .retryOnConnectionFailure(true)
                    .cache(cache)
                    .build();

        }

        return mOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor TestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetworkAvailable(BaseApplication.getInstance().getApplicationContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetworkAvailable(BaseApplication.getInstance().getApplicationContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 365; // 无网络时间
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
    /**
     * 云端响应头拦截器
     * 用于添加统一请求头  请按照自己的需求添加
     */
    private static final Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .header("x_request_platform", "PC")
                    .header("x_auth_token", "pEUe4uFJzww=")
                    .header("Content-x_user_id", "29444")
                    .build();
            return chain.proceed(authorised);
        }
    };


    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }



}