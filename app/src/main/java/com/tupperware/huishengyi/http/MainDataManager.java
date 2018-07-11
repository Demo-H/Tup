package com.tupperware.huishengyi.http;

import com.android.dhunter.common.network.DataManager;
import com.android.dhunter.common.utils.FillUtil;
import com.google.gson.Gson;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.home.HomeBean;
import com.tupperware.huishengyi.entity.home.VersionCheckRequest;
import com.tupperware.huishengyi.entity.home.VersionUpBean;
import com.tupperware.huishengyi.entity.msg.MsgBean;
import com.tupperware.huishengyi.entity.msg.MsgItemBean;
import com.tupperware.huishengyi.entity.msg.MsgRedTip;
import com.tupperware.huishengyi.http.api.HomePageService;
import com.tupperware.huishengyi.http.api.VersionUpdateService;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/4/23.
 */

public class MainDataManager extends BaseDataManager {

    public MainDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static MainDataManager getInstance(DataManager dataManager){
        return new MainDataManager(dataManager);
    }

    /**
     *获取首页
     */
    public Disposable getHomeData(DisposableObserver<HomeBean> consumer, String mobile, String verifyCode) {
        Observable observable = getService(HomePageService.class).getDataResults();
        Observable observableCahce = providers.getHomePageTypes(observable, new DynamicKey("homepage"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<HomeBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     *获取版本升级
     */
    public Disposable getVersionUpdate(DisposableObserver<VersionUpBean> consumer, String userId, String version) {

//        JSONObject requestData = new JSONObject();
//        requestData.put("content", "userId");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "29444");   // 测试用户名29444
//        params.put("version", "1.0.0");  //
        VersionCheckRequest requestData = new VersionCheckRequest();
        requestData.setUserId(userId);
        requestData.setVersion(version);
        //如果服务器不直接接受JsonObject类型，需要采用RequestBody创建的方式解决
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        return changeIOToMainThread(getService(VersionUpdateService.class).getDataResults(requestData), consumer);
    }


//    public Disposable getMainData(int start , int count , DisposableObserver<ResponseBody> consumer){
//        Map<String,Object> map = new HashMap<>(2);
//        map.put("start",start);
//        map.put("count",count);
//        return changeIOToMainThread(getService(BaseApiService.class).executeGet("http://www.baidu.com",map),consumer);
//
//    }



    /**
     * 获取消息分类
     * @param consumer
     * @return
     */
    public Disposable getMsgData(DisposableObserver<MsgBean> consumer) {
        Observable observable = getService(HomePageService.class).getMsgData();
        Observable observableCahce = providers.getMsgTypes(observable, new DynamicKey("msg_tag"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<MsgBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 获取消息详细列表
     * @param consumer
     * @return
     */
    public Disposable getMsgItemData(DisposableObserver<MsgItemBean> consumer, long msgTagId, long timestamp) {
        Observable observable = getService(HomePageService.class).getMsgItemData(getRequest(msgTagId, timestamp));
        Observable observableCahce = providers.getMsgItemTypes(observable, new DynamicKey("msg_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<MsgItemBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     *
     * @param consumer
     * @return
     */
    public Disposable getMsgRedTipData(DisposableObserver<MsgRedTip> consumer) {
        Observable observable = getService(HomePageService.class).getMsgRedTipData();
        return changeIOToMainThread(observable, consumer);
    }

    public<S> Disposable getData(DisposableObserver<S> consumer , final Class<S> clazz , final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = getContext().getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }

    private ConditionRequest getRequest(long tagID, long timestamp) {
        ConditionRequest request = new ConditionRequest();
        request.setTagId(tagID);
        ConditionRequest.TimelineQuery timelineQuery = new ConditionRequest.TimelineQuery();
        timelineQuery.setPageSize(Constant.DEFAULT_PAGE_SIZE);
        timelineQuery.setTimestamp(timestamp);
        request.setTimelineQuery(timelineQuery);
        return request;
    }
}
