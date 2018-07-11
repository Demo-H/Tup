package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ResOrderPendingAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.component.DaggerResOrderPendingFragmentComponent;
import com.tupperware.huishengyi.ui.contract.ResOrderPendingContract;
import com.tupperware.huishengyi.ui.module.ResOrderPendingPresenterModule;
import com.tupperware.huishengyi.ui.presenter.ResOrderPendingPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/14.
 */

public class ResOrderPendingFragment extends BaseFragment implements ResOrderPendingContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ResOrderPendingFragment";

    @BindView(R.id.pending_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView pendingPullRefreshHeader;

    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    ResOrderPendingPresenter mPresenter;
    private ResOrderPendingAdapter mAdapter;
    private View emptyView;
    private int mTabPosition;
    private String code; //门店编码
    private String status; //订单状态
    private int pageIndex = 2;  //分页加载更多，从第二页开始
//    private SharePreferenceData mSharePreDate;

    public static ResOrderPendingFragment newInstance(Bundle bundle) {
        ResOrderPendingFragment fragment = new ResOrderPendingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
//        mSharePreDate = new SharePreferenceData(getContext());
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerResOrderPendingFragmentComponent.builder()
                .appComponent(getAppComponent())
                .resOrderPendingPresenterModule(new ResOrderPendingPresenterModule(this, OrderDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        pendingPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ResOrderPendingAdapter(R.layout.item_resorder_pending_recyclerview, mTabPosition);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        if(mTabPosition == 3) {
            status = String.valueOf(Constant.COMPLETED);
        } else if(mTabPosition == 4) {
            status = String.valueOf(Constant.ROLLBACK);
        } else {
            status = String.valueOf(mTabPosition);
        }
        code = mDataManager.getSPData(GlobalConfig.KEY_DATA_USERID);
        mPresenter.getResOrderPendingData(code, status);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resorder_pending;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getResOrderPendingData(code, status);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        pendingPullRefreshHeader.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public void setResOrderPendingData(OrderBean orderBean) {
        mAdapter.setNewData(orderBean.models);
    }

    @Override
    public void setMoreResOrderPendingData(OrderBean orderBean) {
        mAdapter.getData().addAll(orderBean.models);
        mAdapter.loadMoreComplete();
        if(orderBean.models.size() == 0) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() == 0){
                    mAdapter.loadMoreEnd(false);
                } else{
                    mPresenter.getMoreResOrderPendingData(code, status, pageIndex);
                    pageIndex++;
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getResOrderPendingData(code, status);
                break;
        }
    }

}
