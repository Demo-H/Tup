package com.tupperware.huishengyi.ui.presenter;


import android.app.Activity;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.StaffManagerBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.StaffManagerContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerPresenter extends BasePresenter implements StaffManagerContract.Presenter {

    private MainDataManager mDataManager;

    private StaffManagerContract.View mStaffManagerView;

    private Activity activity;

    @Inject
    public StaffManagerPresenter(MainDataManager mDataManager, StaffManagerContract.View view) {
        this.mDataManager = mDataManager;
        this.mStaffManagerView = view;

    }

    @Override
    public void getStaffManagerData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<StaffManagerBean>() {
            @Override
            public void onNext(StaffManagerBean staffManagerBean) {
                mStaffManagerView.setStaffManagerData(staffManagerBean);
            }

            @Override
            public void onComplete() {

            }
        },StaffManagerBean.class, "store.txt"));
    }
}
