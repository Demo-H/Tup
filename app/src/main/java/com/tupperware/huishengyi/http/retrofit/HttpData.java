package com.tupperware.huishengyi.http.retrofit;

import com.tupperware.huishengyi.http.api.CacheProviders;
import com.tupperware.huishengyi.http.api.HomePageService;
import com.tupperware.huishengyi.network.ConfigURL;
import com.android.dhunter.common.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by dhunter on 2018/4/20.
 * 所有的请求数据的方法集中地
 * 根据对应Service的定义编写合适的方法
 * 其中observable是获取API数据
 * observableCahce获取缓存数据
 * new EvictDynamicKey(false) false使用缓存  true 加载数据不使用缓存
 */

@Singleton
public class HttpData extends RetrofitUtils {
    private static File cacheDirectory = FileUtils.getcacheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory, new GsonTSpeaker())
            .using(CacheProviders.class);
    protected HomePageService homePageService = getRetrofit(ConfigURL.HOME_TOUTIAO_URL).create(HomePageService.class);

    private static HttpData mInstance;

//    private static class SingletonHolder {
//        private static final HttpData INSTANCE = new HttpData();
//    }

    @Inject
    public HttpData() {}

    public static HttpData getInstance() {
        if(mInstance == null) {
            synchronized (HttpData.class) {
                mInstance = new HttpData();
            }
        }
        return mInstance;
    }

    public void getHomeInfo(Observer<ResponseBody> observer, boolean isUseCache, String date) {
        Observable observable = homePageService.getDataResults();
//        Observable observable = homePageService.getDataTestResults("20180420");
//        Observable observableCahce = providers.getHomePageTypes(observable, new DynamicKey("首页"), new EvictDynamicKey(true)).map(new HttpResultFuncCcche<List<ResponseBody>>());
//        setSubscribe(observableCahce, observer);
    }


    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
//                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 用来统一处理RxCacha的结果
     */
    private class HttpResultFuncCcche<T> implements Func1<Reply<T>, T> {

        @Override
        public T call(Reply<T> httpResult) {
            return httpResult.getData();
        }
    }
}
