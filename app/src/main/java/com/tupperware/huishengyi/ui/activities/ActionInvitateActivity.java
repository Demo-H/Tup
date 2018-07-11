package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ActionInvitateAdapter;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.ActionMembersBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.component.DaggerActionInvitateActivityComponent;
import com.tupperware.huishengyi.ui.contract.ActionInvitateContract;
import com.tupperware.huishengyi.ui.module.ActionInvitatePresenterModule;
import com.tupperware.huishengyi.ui.presenter.ActionInvitatePresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/7.
 * 爱会员-活动邀约名单
 */

public class ActionInvitateActivity extends BaseActivity implements ActionInvitateContract.View, PtrHandler {

    private static final String TAG = "ActionInvitateActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightImage;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    ActionInvitatePresenter mPresenter;
    private ActionInvitateAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;
    private long infoId;
    private long storeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_recycle_list;
    }

    @Override
    protected void initLayout() {
        infoId = getIntent().getLongExtra(Constant.ACTION_MEMBER_INFO_ID, 0);
        storeId = getIntent().getLongExtra(Constant.ACTION_MEMBER_STORE_ID, 0);
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.order_search_ic);
        mTitle.setText(getResources().getString(R.string.action_inv_name));
        DaggerActionInvitateActivityComponent.builder()
                .appComponent(getAppComponent())
                .actionInvitatePresenterModule(new ActionInvitatePresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ActionInvitateAdapter(R.layout.item_purpose_follow_recyclerview);
        mRecyclerView.setAdapter(mAdapter);
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
    }

    @Override
    protected void requestData() {
        showDialog();
        mPresenter.getActionInvitateData(infoId, storeId);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getActionInvitateData(infoId, storeId);
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

    @Override
    public void setActionInvitateData(ActionMembersBean mBean) {
        mAdapter.setNewData(mBean.getModels());
    }

    @OnClick({R.id.left_back, R.id.error_layout, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.error_layout:
                mPresenter.getActionInvitateData(infoId, storeId);
                break;
            case R.id.next:
                Intent intent = new Intent(ActionInvitateActivity.this, SearchActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.LOVE_VIP_FRAGMENT);
                startActivity(intent);
                break;
        }
    }
}
