package com.tupperware.biz.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RelativeLayout;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.android.dhunter.common.utils.FileUtils;
import com.tupperware.biz.entity.member.PersonalQrBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.HomePageContract;
import com.tupperware.biz.utils.BitmapUtils;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;
import com.tupperware.biz.utils.thread.HandlerThreadFactory;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/5.
 */

public class HomePagePresenter extends BasePresenter implements HomePageContract.Presenter {

    private static final String TAG = "HomePagePresenter";

//    private MainDataManager mDataManager;

//    private HomePageContract.View mHomePageView;
    private PersonalDataManager mDataManager;
    private HomePageContract.View mView;

    private Activity activity;

    @Inject
    public HomePagePresenter(PersonalDataManager mDataManager, HomePageContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getPersonQrData(String storeCode) {
        if(storeCode == null) {
            mView.toast("token过期，请重新登录");
            mView.reLogin();
            mDataManager.deleteSPData();
            return;
        }
        addDisposabe(mDataManager.getPersonalQrData(new ErrorDisposableObserver<PersonalQrBean>() {
            @Override
            public void onNext(PersonalQrBean personalQrBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(personalQrBean));
                if(!personalQrBean.isSuccess()) {
                    mView.toast(personalQrBean.message);
                    if(personalQrBean.resultCode != null && (personalQrBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || personalQrBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else if(personalQrBean.getModel() == null) {
                    mView.setEmptyView();
                }else {
                    mView.setNormalView();
                    mView.setPersonQrData(personalQrBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, storeCode));
    }

    public void saveQrImage(final RelativeLayout mRl, final Context context) {

        HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).post(new Runnable() {
            @Override
            public void run() {
                FileUtils.fileIsExistsbyType(FileUtils.PathType.APP_PHOTOS);
                String fileName = "qr_" + System.currentTimeMillis() + ".png";
                String filepath = FileUtils.DIR_PHOTO_CRASH + File.separator + fileName;
                BitmapUtils.saveBitmap(mRl, filepath);
                mView.toast("二维码保存路径" + filepath);
                /** 通知图片库更新，否则手机重启才能看到 **/
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File file = new File(filepath);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
        });
    }

/*    @Override
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
//                        mHomePageView.setNetErrorView();
                    }
                } else if(homeBean.model == null) {
                    mHomePageView.showToast("服务器数据返回为null");
//                    mHomePageView.setNetErrorView();
                } else {
//                    HomeIndexBean homeIndexBean = matchResponeData(homeBean);
//                    mHomePageView.setNormalView();
//                    mHomePageView.setHomePageData(homeIndexBean);
                    mHomePageView.setHomePageData(homeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                mHomePageView.setNetErrorView();
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

    @Override
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

    *//**
     * 解析返回的json数据适配multiItemRecycle
     * @param homeBean
     * @return
     *//*
    private HomeIndexBean matchResponeData(HomeBean homeBean) {
        HomeIndexBean homeIndexBean = new HomeIndexBean();
        homeIndexBean.success = homeBean.success;
        homeIndexBean.message = homeBean.message;
        homeIndexBean.resultCode = homeBean.resultCode;
        List<HomeIndexBean.ItemInfoListBean> itemInfoList = new ArrayList<>();

        *//**
         * 删除我的任务目标
         *//*
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
            *//*} else if(i == 3) {
                HomeIndexBean.ItemInfoListBean itemBean = new HomeIndexBean.ItemInfoListBean();
                itemBean.itemType = "targetMe";
                List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> itemContentList = new ArrayList<>();
                for(int j = 0 ; j < homeBean.model.article.size(); j++) {
                    HomeIndexBean.ItemInfoListBean.ItemContentListBean itemContent = null;
                    itemContent = ObjectUtil.modelA2B(homeBean.model.article.get(j), HomeIndexBean.ItemInfoListBean.ItemContentListBean.class);
                    itemContentList.add(itemContent);
                }
                itemBean.setItemContentList(itemContentList);
                itemInfoList.add(itemBean);*//*
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
    }*/
}
