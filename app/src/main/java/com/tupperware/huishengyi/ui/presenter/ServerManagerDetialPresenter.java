package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.ServerManagerDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ServerManagerDetialPresenter extends BasePresenter implements ServerManagerDetialContract.Presenter {

    private MainDataManager mDataManager;

    private ServerManagerDetialContract.View mView;

    private Activity activity;

    @Inject
    public ServerManagerDetialPresenter(MainDataManager mDataManager, ServerManagerDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getServerManagerDetialData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<PurFollowDetialBean>() {
            @Override
            public void onNext(PurFollowDetialBean purFollowDetialBean) {
                mView.setServerManagerDetialData(purFollowDetialBean);
            }

            @Override
            public void onComplete() {

            }
        },PurFollowDetialBean.class, "member.txt"));
    }

}