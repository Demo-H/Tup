package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.VideoAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.entity.VideoBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerVideoFragmentComponent;
import com.tupperware.huishengyi.ui.module.VideoPresenterModule;
import com.tupperware.huishengyi.ui.contract.VideoContract;
import com.tupperware.huishengyi.ui.presenter.VideoPresenter;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/5.
 */

public class VideoFragment extends BaseFragment implements VideoContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener   {

    private static final String TAG = "VideoFragment";
    @Inject
    VideoPresenter mPresenter;
    private RecyclerView mVideoRecyclerview;
    private PullHeaderView videoPullRefreshHeader;
    private VideoAdapter mAdapter;

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mVideoRecyclerview = (RecyclerView) view.findViewById(R.id.zixun_recyclerview);
        videoPullRefreshHeader = (PullHeaderView) view.findViewById(R.id.find_pull_refresh_header);
        initLayout();
        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerVideoFragmentComponent.builder()
                .appComponent(getAppComponent())
                .videoPresenterModule(new VideoPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        videoPullRefreshHeader.setPtrHandler(this);
        mVideoRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new VideoAdapter(R.layout.item_video_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mVideoRecyclerview.setAdapter(mAdapter);

    }

    @Override
    public void requestData() {
        mPresenter.getVideoData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixun;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setVideoData(VideoBean video) {
        mAdapter.addData(video.content);
    }

    @Override
    public void setMoreVideoData(VideoBean video) {
        mAdapter.getData().addAll(video.content);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mVideoRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= 90){
                    mAdapter.loadMoreEnd(false);
                }
                else{
                    mPresenter.getMoreVideoData();
                }
            }
        },1000);

    }
}
