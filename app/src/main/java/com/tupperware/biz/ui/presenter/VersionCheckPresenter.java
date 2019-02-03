package com.tupperware.biz.ui.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.home.VersionUpBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.VersionCheckContract;
import com.tupperware.biz.utils.AppUtils;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/4/25.
 */

public class VersionCheckPresenter extends BasePresenter implements VersionCheckContract.Presenter{

    private static final String TAG = "HomePagePresenter";

    private MainDataManager mDataManager;

    private VersionCheckContract.View mView;

    private Activity activity;

    @Inject
    public VersionCheckPresenter(MainDataManager mDataManager, VersionCheckContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void checkVersion(String mUserId) {
        String mCurrentVersion = AppUtils.getVersionName();
        mDataManager.getVersionUpdate(new ErrorDisposableObserver<VersionUpBean>() {
            @Override
            public void onNext(VersionUpBean verinfo) {
                String json = ObjectUtil.jsonFormatter(verinfo);
                LogF.i(TAG, json);
                if(verinfo == null || verinfo.model == null) {
                    return;
                }
                if(!verinfo.success) {
                    mView.toast(verinfo.message + " ");
                    if(verinfo.resultCode != null && (verinfo.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || verinfo.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    if(verinfo.model.isUpToDate == 0) {
                        if(verinfo.model.isForce == 0) {
                            mView.showUpdateChooseDialog(verinfo);
                        } else {
                            mView.showMustUpdateChooseDialog(verinfo);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e("TAG", "onError: "+e );
            }

            @Override
            public void onComplete() {

            }
        }, mUserId, mCurrentVersion);
    }
}
