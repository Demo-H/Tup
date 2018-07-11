package com.tupperware.huishengyi.ui.presenter;

import android.net.Uri;
import android.webkit.CookieManager;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.LikeBean;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.saleenter.ImportantBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.network.ConfigURL;
import com.tupperware.huishengyi.ui.contract.WebViewContract;
import com.tupperware.huishengyi.utils.OkHttpClientManager;
import com.tupperware.huishengyi.utils.StringUtils;
import com.tupperware.huishengyi.utils.logutils.LogF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by dhunter on 2018/7/9.
 */

public class WebViewPresenter extends BasePresenter implements WebViewContract.Presenter {

    private static final String TAG = "WebViewPresenter";

    private CollegeDataManager mDataManager;
    private WebViewContract.View mView;
    private OkHttpClientManager mOkhttpInstance;

    @Inject
    public WebViewPresenter(CollegeDataManager dataManager, WebViewContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
        initClientManager();
    }

    public void initClientManager() {
        mOkhttpInstance = OkHttpClientManager.getInstance();
    }

    @Override
    public void asynceImportSaleUrl() {
        String json = mDataManager.getSPData(GlobalConfig.KEY_DATA_LOGIN_INFO);
        if(json == null) {
            return;
        }
        try {
            JSONObject login_info = new JSONObject(json);
            JSONObject login_extra = login_info.getJSONObject("extra");
            String storeid = "", mStoreInfo = "", mStoreFromOrgan = "", url = "";
            if (login_extra != null) {
                Iterator<String> iterator = login_extra.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key.equals("storeId")) {
                        storeid = login_extra.getString(key);
                    } else if (key.equals("store_info")) {
                        mStoreInfo = login_extra.getString(key);
                    } else if (key.equals("store_from_organ")) {
                        mStoreFromOrgan = login_extra.getString(key);
                    }
                }
            }
            long timestamp = System.currentTimeMillis();
            String token = StringUtils.MD5(StringUtils.MD5(storeid + timestamp) + "tupperware");
            Uri uri = Uri.parse(ConfigURL.IMPORTANT_PRODUCT_SALE_TEST);
            uri.buildUpon();
            Uri.Builder builder = uri.buildUpon();
            builder.appendQueryParameter("storeid", storeid);
            builder.appendQueryParameter("timestamp", timestamp + "");
            builder.appendQueryParameter("token", token);
            url = builder.build().toString();

            Map<String, String> params = new HashMap<>();
            params.put("platform_referer", "Android");
            params.put("store_info", mStoreInfo);
            params.put("store_from_organ", mStoreFromOrgan);

            mOkhttpInstance.doPost(url, params, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Headers headers = response.headers();
                    String sessid = headers.get("Set-Cookie");
                    String url = response.request().url().toString();
                    CookieManager.getInstance().setCookie(url, sessid);
                    String json = response.body().string();
                    ImportantBean importantBean = ImportantBean.createInstanceByJson(json);
                    mView.hideDialog();
                    mView.jumpToImportSalePage(importantBean);
                    LogF.i(TAG, "" + json);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getIsLike(long answerId) {
        addDisposabe(mDataManager.getIsLike(new ErrorDisposableObserver<LikeBean>() {
            @Override
            public void onNext(LikeBean likeBean) {
                if(likeBean != null) {
                    if(likeBean.isSuccess()) {
                        mView.setIsLike(likeBean);
                    } else {
                        mView.toast(likeBean.getMessage());
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        }, answerId));
    }

    @Override
    public void setLike(long answerId) {
        addDisposabe(mDataManager.setLike(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                if(responseBean != null) {
                    if(!responseBean.isSuccess()) {
                        mView.toast(responseBean.getMessage());
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        }, answerId));
    }

    @Override
    public void cancelLike(long answerId) {
        addDisposabe(mDataManager.cancelLike(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                if(responseBean != null) {
                    if(!responseBean.isSuccess()) {
                        mView.toast(responseBean.getMessage());
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        }, answerId));
    }
}
