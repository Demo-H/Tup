package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CollArticleContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZMediaManager.TAG;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollArticlePresenter extends BasePresenter implements CollArticleContract.Presenter{
    private CollegeDataManager mDataManager;

    private CollArticleContract.View mView;

    private Activity activity;

    @Inject
    public CollArticlePresenter(CollegeDataManager mDataManager, CollArticleContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getArticleData(int tagId) {
        addDisposabe(mDataManager.getArticleData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else {
                    mView.setArticleData(collegeBean);
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
        }, tagId));

    }

    @Override
    public void getMoreArticleData(int tagId, int pageIndex) {
        addDisposabe(mDataManager.getMoreArticleData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else {
                    mView.setMoreArticleData(collegeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: "+e );
            }

            @Override
            public void onComplete() {

            }
        }, tagId, pageIndex));
    }
}