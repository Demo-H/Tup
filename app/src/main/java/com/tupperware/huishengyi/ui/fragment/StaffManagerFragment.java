package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.StaffManagerAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.StaffManagerBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerStaffManagerFragmentComponent;
import com.tupperware.huishengyi.ui.module.StaffManagerPresenterModule;
import com.tupperware.huishengyi.ui.contract.StaffManagerContract;
import com.tupperware.huishengyi.ui.presenter.StaffManagerPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerFragment extends BaseFragment implements StaffManagerContract.View {

    private static final String TAG = "StaffManagerFragment";

    @BindView(R.id.staff_recyclerview)
    RecyclerView mRecycleView;

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
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        requestData();
        return view;
    }


    @Override
    public void initLayout() {

        DaggerStaffManagerFragmentComponent.builder()
                .appComponent(getAppComponent())
                .staffManagerPresenterModule(new StaffManagerPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new StaffManagerAdapter(R.layout.item_staff_manager_recyclerview);
        mAdapter.setEnableLoadMore(true);
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    public void requestData() {
        mPresenter.getStaffManagerData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_staff_manager;
    }

    @Override
    public void setStaffManagerData(StaffManagerBean staffManagerBean) {
        mAdapter.addData(staffManagerBean.content);
    }
}
