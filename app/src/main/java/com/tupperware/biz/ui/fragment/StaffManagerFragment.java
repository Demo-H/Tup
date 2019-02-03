package com.tupperware.biz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.StaffManagerAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.StaffManagerBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.activities.AddNewStaffActivity;
import com.tupperware.biz.ui.component.DaggerStaffManagerFragmentComponent;
import com.tupperware.biz.ui.contract.StaffManagerContract;
import com.tupperware.biz.ui.module.StaffManagerPresenterModule;
import com.tupperware.biz.ui.presenter.StaffManagerPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerFragment extends BaseFragment implements StaffManagerContract.View, PtrHandler {

    private static final String TAG = "StaffManagerFragment";

    @BindView(R.id.staff_recyclerview)
    RecyclerView mRecycleView;
    @BindView(R.id.members_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private Integer storeId;
    private View emptyView;
    private TextView mEmptyText;

    @Inject
    StaffManagerPresenter mPresenter;

    private StaffManagerAdapter mAdapter;

    public static StaffManagerFragment newInstance() {
        StaffManagerFragment fragment = new StaffManagerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, view);
        initLayout();
//        requestData();
        return view;
    }


    @Override
    public void initLayout() {
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        DaggerStaffManagerFragmentComponent.builder()
                .appComponent(getAppComponent())
                .staffManagerPresenterModule(new StaffManagerPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new StaffManagerAdapter(R.layout.item_staff_manager_recyclerview);
//        mAdapter.setEnableLoadMore(true);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), AddNewStaffActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.MODIFIED);
                intent.putExtra(Constant.STAFF_INFO, mAdapter.getData().get(position));
                startActivityForResult(intent, Constant.REQUEST_DATA);
                return false;
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == Activity.RESULT_OK) {
//            if (requestCode == Constant.REQUEST_DATA) {
//                requestData();
//            }
//        }
//    }

    @Override
    public void requestData() {
        showDialog();
        mPresenter.getStaffManagerData(storeId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_staff_manager;
    }

    @Override
    public void setStaffManagerData(StaffManagerBean staffManagerBean) {
        mAdapter.setNewData(staffManagerBean.getModels());
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getStaffManagerData(storeId);
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
        mAdapter.setNewData(null);
        mAdapter.setEmptyView(emptyView);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getStaffManagerData(storeId);
                break;
        }
    }
}
