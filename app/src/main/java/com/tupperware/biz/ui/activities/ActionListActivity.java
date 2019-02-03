package com.tupperware.biz.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.ActionListAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.StoreScheduleBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerActionListActivityComponent;
import com.tupperware.biz.ui.contract.ActionListContract;
import com.tupperware.biz.ui.module.ActionListPresenterModule;
import com.tupperware.biz.ui.presenter.ActionListPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/7.
 */

public class ActionListActivity extends BaseActivity implements ActionListContract.View, PtrHandler {

    private static final String TAG = "ActionListActivity";
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
    ActionListPresenter mPresenter;
    private ActionListAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private String activity_from;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_server;
    }

    @Override
    protected void initLayout() {
        activity_from = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mTitle.setText(getResources().getString(R.string.action_list));
        mRightNext.setVisibility(View.GONE);
        DaggerActionListActivityComponent.builder()
                .appComponent(getAppComponent())
                .actionListPresenterModule(new ActionListPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ActionListAdapter(R.layout.item_action_recycleview);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setActivity_from(activity_from);
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
    }

    @Override
    protected void requestData() {
        showDialog();
        mPresenter.getScheduleListData(storeCode);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getScheduleListData(storeCode);
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
                mPresenter.getScheduleListData(storeCode);
                break;
        }
    }

    @Override
    public void setScheduleListData(StoreScheduleBean mBean) {
        mAdapter.setNewData(mBean.getModels());
    }
}
