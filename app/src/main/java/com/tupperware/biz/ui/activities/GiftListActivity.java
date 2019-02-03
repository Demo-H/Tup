package com.tupperware.biz.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.GiftListAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerGiftListActivityComponent;
import com.tupperware.biz.ui.contract.GiftListContract;
import com.tupperware.biz.ui.module.GiftListPresenterModule;
import com.tupperware.biz.ui.presenter.GiftListPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/6.
 */

public class GiftListActivity extends BaseActivity implements GiftListContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "GiftListActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    GiftListPresenter mPresenter;
    private GiftListAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;
    private Integer member_id;
    private int pageIndex = 2;  //分页加载更多，从第二页开始

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_server;
    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.logistics_schedule_list));
        mRightNext.setVisibility(View.GONE);
        DaggerGiftListActivityComponent.builder()
                .appComponent(getAppComponent())
                .giftListPresenterModule(new GiftListPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new GiftListAdapter(R.layout.item_logistics_schedule_recycleview);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
    }

    @Override
    protected void requestData() {
//        if(Constant.Demo) {
//            member_id = "2069506";
//        }
        member_id = getIntent().getIntExtra(Constant.MEMBER_ID, 0);
        showDialog();
        mPresenter.getGiftListData(member_id);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getGiftListData(member_id);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNetErrorView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.GONE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEmptyView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }

    @OnClick({R.id.left_back, R.id.error_layout })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.error_layout:
                showDialog();
                mPresenter.getGiftListData(member_id);
                break;
        }
    }

    @Override
    public void setGiftListData(GiftBean mBean) {
        pageIndex = 2;
        mAdapter.setNewData(mBean.getPageInfo().getList());
        if(!mBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreGiftListData(GiftBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete();
        pageIndex++;
        if(!mBean.getPageInfo().isHasNextPage()) {
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
                } else {
                    mPresenter.getMoreGiftListData(member_id, pageIndex);
                }
            }
        },1000);
    }
}
