package com.tupperware.biz.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.RemindAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.RemindMemberResponse;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerRemindActivityComponent;
import com.tupperware.biz.ui.contract.RemindContract;
import com.tupperware.biz.ui.fragment.DatePickerFragment;
import com.tupperware.biz.ui.module.RemindPresenterModule;
import com.tupperware.biz.ui.presenter.RemindPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindActivity extends BaseActivity implements RemindContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

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

    @BindView(R.id.choose_date)
    TextView mChooseDate;
    @BindView(R.id.members_count)
    TextView mMembersCount;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.members_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private View emptyView;
    private int pageIndex = 2;
    private long chooseTimestamp;
    private RemindAdapter mAdapter;
    private TextView mEmptyText;

    @Inject
    RemindPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remind;
    }

    @Override
    protected void initLayout() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.date_btn);
        mTitle.setText(getResources().getString(R.string.water_remind));
        mChooseDate.setText(DateFormatter.getCurrentMonthbyFormat());
        chooseTimestamp = System.currentTimeMillis();
        mMembersCount.setText("需更换滤芯顾客名单(  )");
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        DaggerRemindActivityComponent.builder()
                .appComponent(getAppComponent())
                .remindPresenterModule(new RemindPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new RemindAdapter(R.layout.item_remind_member_list_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        showDialog();
        mPresenter.getFilterReservation(chooseTimestamp);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getFilterReservation(chooseTimestamp);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() == 0){
                    mAdapter.loadMoreEnd(false);
                } else {
                    mPresenter.getMoreFilterReservation(chooseTimestamp, pageIndex);
                }
            }
        },1000);
    }

    @Override
    public void setFilterReservationData(RemindMemberResponse remindBean) {
        if(getActivity() == null) {
            return;
        }
        pageIndex = 2;
        mAdapter.setNewData(remindBean.getPageInfo().getList());
        if(!remindBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
        mMembersCount.setText("需更换滤芯顾客名单(" + remindBean.getPageInfo().getTotal() + ")");
    }

    @Override
    public void setMoreFilterReservationData(RemindMemberResponse remindBean) {
        if(remindBean == null || remindBean.getPageInfo() == null ){
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
        pageIndex++;
        mAdapter.getData().addAll(remindBean.getPageInfo().getList());
        mAdapter.loadMoreComplete();
        if(!remindBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
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
        mAdapter.setNewData(null);
        mAdapter.setEmptyView(emptyView);
    }

    @OnClick({R.id.left_back, R.id.next, R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                chooseDate();
                break;
            case R.id.error_layout:
                requestData();
                break;
        }
    }

    private void chooseDate() {
        FragmentManager manager = getSupportFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.show(manager, Constant.DIALOG_DATE);
        dialog.setOnDialogListener(new DatePickerFragment.OnDialogListener() {
            @Override
            public void onDialogClick(Date date) {
                mChooseDate.setText(DateFormatter.MonthFormat(date));
                chooseTimestamp = date.getTime();
                requestData();
            }
        });
    }
}
