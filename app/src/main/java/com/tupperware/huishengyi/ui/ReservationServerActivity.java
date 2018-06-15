package com.tupperware.huishengyi.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ReservationServerAdapter;
import com.tupperware.huishengyi.component.DaggerReservationServerActivityComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.ReservationServerBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.module.ReservationServerPresenterModule;
import com.tupperware.huishengyi.ui.contract.ReservationServerContract;
import com.tupperware.huishengyi.ui.presenter.ReservationServerPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/31.
 * 会员预约服务
 */

public class ReservationServerActivity extends BaseActivity implements ReservationServerContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ReservationServerActivity";

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
    ReservationServerPresenter mPresenter;
    private ReservationServerAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;
    private String mobilephone;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_server);
        if(Constant.Demo) {
            mobilephone = "13660942833";
        } else {
            mobilephone = getIntent().getStringExtra(Constant.MEMBER_PHONE);
        }
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.reservation_server));
        mRightNext.setVisibility(View.GONE);
        DaggerReservationServerActivityComponent.builder()
                .appComponent(getAppComponent())
                .reservationServerPresenterModule(new ReservationServerPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ReservationServerAdapter(R.layout.item_reservation_server_recycleview);
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
    protected void initLayoutData() {
        showDialog();
        mPresenter.getReservationServerData(mobilephone, storeCode);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getReservationServerData(mobilephone, storeCode);
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

    @OnClick({R.id.left_back, R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.error_layout:
                mPresenter.getReservationServerData(mobilephone, storeCode);
                break;
        }
    }

    @Override
    public void setReservationServerData(ReservationServerBean mBean) {
        mAdapter.setNewData(mBean.models);
    }


    @Override
    public void setMoreReservationServerData(ReservationServerBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete();
        pageIndex++;
        if(mBean.models == null || mBean.models.size() == 0) {
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
                    mPresenter.getMoreReservationServerData(mobilephone, storeCode, pageIndex);
                }
            }
        },1000);
    }
}
