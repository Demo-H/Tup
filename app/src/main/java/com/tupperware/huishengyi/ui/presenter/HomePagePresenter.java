package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.home.HomeBean;
import com.tupperware.huishengyi.entity.home.HomeIndexBean;
import com.tupperware.huishengyi.entity.msg.MsgRedTip;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.network.StateCode;
import com.tupperware.huishengyi.ui.contract.HomePageContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/5.
 */

public class HomePagePresenter extends BasePresenter implements HomePageContract.Presenter {

    private static final String TAG = "HomePagePresenter";

    private MainDataManager mDataManager;

    private HomePageContract.View mHomePageView;

    private Activity activity;

    @Inject
    public HomePagePresenter(MainDataManager mDataManager, HomePageContract.View view) {
        this.mDataManager = mDataManager;
        this.mHomePageView = view;

    }

    @Override
    public void getHomePageData() {

        addDisposabe(mDataManager.getHomeData(new ErrorDisposableObserver<HomeBean>() {
            @Override
            public void onNext(HomeBean homeBean) {
                LogF.i(TAG,ObjectUtil.jsonFormatter(homeBean));
                if(!homeBean.success) {
                    if(homeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)) {
                        mHomePageView.showToast("token过期，请重新登录");
                        mHomePageView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mHomePageView.showToast(homeBean.message);
                        mHomePageView.setNetErrorView();
                    }
                } else if(homeBean.model == null) {
                    mHomePageView.showToast("服务器数据返回为null");
                    mHomePageView.setNetErrorView();
                } else {
                    HomeIndexBean homeIndexBean = matchResponeData(homeBean);
                    mHomePageView.setNormalView();
                    mHomePageView.setHomePageData(homeIndexBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mHomePageView.setNetErrorView();
                mHomePageView.setHomePageData(null);
            }

            @Override
            public void onComplete() {
            }
        }, null, null));
    }

    @Override
    public void getMoreHomePageData() {

    }

    public void getMsgRedTip() {
        addDisposabe(mDataManager.getMsgRedTipData(new ErrorDisposableObserver<MsgRedTip>() {
            @Override
            public void onNext(MsgRedTip msgTip) {
                if(msgTip.success && msgTip.model != null) {
                    mHomePageView.setShowMsgRedTip(msgTip.model.unRead);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        }));

    }

    /**
     * 解析返回的json数据适配multiItemRecycle
     * @param homeBean
     * @return
     */
    private HomeIndexBean matchResponeData(HomeBean homeBean) {
        HomeIndexBean homeIndexBean = new HomeIndexBean();
        homeIndexBean.success = homeBean.success;
        homeIndexBean.message = homeBean.message;
        homeIndexBean.resultCode = homeBean.resultCode;
        List<HomeIndexBean.ItemInfoListBean> itemInfoList = new ArrayList<>();

        /**
         * 删除我的任务目标
         */
        for(int i = 0; i < 4; i++) {
            if(i == 0) {//今日头条
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.setItemType("topBanner");
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                if(homeBean.model.headline != null) {
                    for (int j = 0; j < homeBean.model.headline.size(); j++) {
                        HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                        itemContent = ObjectUtil.modelA2B(homeBean.model.headline.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                        itemContentList.add(itemContent);
                    }
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);
            } else if(i == 1) {//精准推送
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.itemType = "preciseRecommendation";
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                for(int j = 0 ; j < homeBean.model.article.size(); j++) {
                    HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                    itemContent = ObjectUtil.modelA2B(homeBean.model.article.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                    itemContentList.add(itemContent);
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);
            } else if(i == 2) {
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.itemType = "iconList";
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                for(int j = 0 ; j < homeBean.model.article.size(); j++) {
                    HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                    itemContent = ObjectUtil.modelA2B(homeBean.model.article.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                    itemContentList.add(itemContent);
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);
            /*} else if(i == 3) {
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.itemType = "targetMe";
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                for(int j = 0 ; j < homeBean.model.article.size(); j++) {
                    HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                    itemContent = ObjectUtil.modelA2B(homeBean.model.article.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                    itemContentList.add(itemContent);
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);*/
            } else if(i == 3) {//首页商学院
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.itemType = "marketInfo";
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                for(int j = 0 ; j < homeBean.model.school.size(); j++) {
                    HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                    itemContent = ObjectUtil.modelA2B(homeBean.model.school.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                    itemContentList.add(itemContent);
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);
            }
        }
        homeIndexBean.setItemInfoList(itemInfoList);
        LogF.i(TAG, ObjectUtil.jsonFormatter(homeIndexBean));
        return homeIndexBean;
    }
}
