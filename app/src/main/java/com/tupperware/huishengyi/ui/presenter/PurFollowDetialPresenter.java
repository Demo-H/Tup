package com.tupperware.huishengyi.ui.presenter;


import android.app.Activity;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.PurFollowDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialPresenter extends BasePresenter implements PurFollowDetialContract.Presenter {

    private MainDataManager mDataManager;

    private PurFollowDetialContract.View mView;

    private Activity activity;

    @Inject
    public PurFollowDetialPresenter(MainDataManager mDataManager, PurFollowDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getPurFollowDetialData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<PurFollowDetialBean>() {
            @Override
            public void onNext(PurFollowDetialBean purFollowDetialBean) {
                mView.setPurFollowDetialData(purFollowDetialBean);
            }

            @Override
            public void onComplete() {

            }
        },PurFollowDetialBean.class, "member.txt"));
    }

}
