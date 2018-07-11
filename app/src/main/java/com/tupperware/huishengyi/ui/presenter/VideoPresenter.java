package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.VideoBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.VideoContract;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/3/6.
 */

public class VideoPresenter  extends BasePresenter implements VideoContract.Presenter {
    private MainDataManager mDataManager;

    private VideoContract.View mVideoView;

    private Activity activity;

    @Inject
    public VideoPresenter(MainDataManager mDataManager, VideoContract.View view) {
        this.mDataManager = mDataManager;
        this.mVideoView = view;
    }

    @Override
    public void getVideoData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<VideoBean>() {
            @Override
            public void onNext(VideoBean videoBean) {
                mVideoView.setVideoData(videoBean);
            }

            @Override
            public void onComplete() {

            }
        },VideoBean.class, "find.txt"));
    }

    @Override
    public void getMoreVideoData() {
        addDisposabe(mDataManager.getData(new DisposableObserver<VideoBean>() {
            @Override
            public void onNext(VideoBean videoBean) {
                mVideoView.setMoreVideoData(videoBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },VideoBean.class, "find.txt"));
    }

}
