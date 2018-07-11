package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.home.VersionUpBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.VersionCheckContract;
import com.tupperware.huishengyi.utils.AppUtils;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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
                LogF.i(TAG, ObjectUtil.jsonFormatter(verinfo));
                if(!verinfo.success) {
                    mView.toast(verinfo.message);
                } else {
                    if(verinfo.model.isUpToDate == 0) {
                        mView.showUpdateChooseDialog(verinfo.model.version, verinfo.model.installUrl);
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
