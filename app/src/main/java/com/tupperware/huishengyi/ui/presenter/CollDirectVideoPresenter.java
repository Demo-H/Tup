package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.CollDirectVideoContract;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollDirectVideoPresenter extends BasePresenter implements CollDirectVideoContract.Presenter{
    private MainDataManager mDataManager;

    private CollDirectVideoContract.View mView;

    private Activity activity;

    @Inject
    public CollDirectVideoPresenter(MainDataManager mDataManager, CollDirectVideoContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getDirectVideoData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean mBean) {
                mView.setDirectVideoData(mBean);
            }

            @Override
            public void onComplete() {

            }
        },CollegeBean.class, "find.txt"));
    }

    @Override
    public void getMoreDirectVideoData() {
        addDisposabe(mDataManager.getData(new DisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean mBean) {
                mView.setMoreDirectVideoData(mBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },CollegeBean.class, "find.txt"));
    }
}