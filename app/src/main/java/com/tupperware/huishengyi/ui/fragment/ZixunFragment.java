package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ZixunAdapter;
import com.tupperware.huishengyi.entity.ZixunBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.component.DaggerZixunFragmentComponent;
import com.tupperware.huishengyi.module.ZixunPresenterModule;
import com.tupperware.huishengyi.ui.contract.ZixunContract;
import com.tupperware.huishengyi.ui.presenter.ZixunPresenter;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/5.
 */

public class ZixunFragment extends BaseFragment implements ZixunContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener   {

    private static final String TAG = "ZixunFragment";
    @Inject
    ZixunPresenter mPresenter;
    private RecyclerView mZixunRecyclerview;
    private PullHeaderView zixunPullRefreshHeader;
    private ZixunAdapter mAdapter;

    public static ZixunFragment newInstance() {
        ZixunFragment fragment = new ZixunFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mZixunRecyclerview = (RecyclerView) view.findViewById(R.id.zixun_recyclerview);
        zixunPullRefreshHeader = (PullHeaderView) view.findViewById(R.id.find_pull_refresh_header);
        initLayout();
        initLayoutData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerZixunFragmentComponent.builder()
                .appComponent(getAppComponent())
                .zixunPresenterModule(new ZixunPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        zixunPullRefreshHeader.setPtrHandler(this);
        mZixunRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ZixunAdapter(R.layout.item_zixun_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mZixunRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void initLayoutData() {
        mPresenter.getZixunData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixun;
    }

    @Override
    public void setZixunData(ZixunBean zixun) {
        mAdapter.addData(zixun.content);
    }

    @Override
    public void setMoreZixunData(ZixunBean zixun) {
        mAdapter.getData().addAll(zixun.content);
        mAdapter.loadMoreComplete();
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
    public void onLoadMoreRequested() {
        mZixunRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= 90){
                    mAdapter.loadMoreEnd(false);
                }
                else{
                    mPresenter.getMoreZixunData();
                }
            }
        },1000);

    }

}
