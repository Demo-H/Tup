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
import com.tupperware.huishengyi.adapter.ResOrderServerAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.ResOrderPendingBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerResOrderServerFragmentComponent;
import com.tupperware.huishengyi.ui.module.ResOrderServerPresenterModule;
import com.tupperware.huishengyi.ui.contract.ResOrderServerContract;
import com.tupperware.huishengyi.ui.presenter.ResOrderServerPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/8.
 */

public class ResOrderServerFragment extends BaseFragment implements ResOrderServerContract.View , PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ResOrderServerFragment";

    @BindView(R.id.pending_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullRefreshHeader;

    @Inject
    ResOrderServerPresenter mPresenter;

    private ResOrderServerAdapter mAdapter;
    private int mTabPosition;

    public static ResOrderServerFragment newInstance(Bundle bundle) {
        ResOrderServerFragment fragment = new ResOrderServerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerResOrderServerFragmentComponent.builder()
                .appComponent(getAppComponent())
                .resOrderServerPresenterModule(new ResOrderServerPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mPullRefreshHeader.setPtrHandler(this);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ResOrderServerAdapter(R.layout.item_resorder_server_recyclerview, mTabPosition);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mRecyclerview.setAdapter(mAdapter);

    }

    @Override
    public void requestData() {
        mPresenter.getResOrderServerData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resorder_pending;
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
    public void setResOrderServerData(ResOrderPendingBean resOrderPending) {
        mAdapter.addData(resOrderPending.content);
    }

    @Override
    public void setMoreResOrderServerData(ResOrderPendingBean resOrderPending) {
        mAdapter.getData().addAll(resOrderPending.content);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= 2){
                    mAdapter.loadMoreEnd(false);
                }
                else{
                    mPresenter.getMoreResOrderServerData();
                }
            }
        },1000);
    }

}
