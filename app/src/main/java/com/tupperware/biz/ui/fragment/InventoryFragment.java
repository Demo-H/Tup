package com.tupperware.biz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import com.tupperware.biz.adapter.InventoryAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.hotsale.HotInventoryResponse;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.component.DaggerInventoryFragmentComponent;
import com.tupperware.biz.ui.contract.InventoryContract;
import com.tupperware.biz.ui.module.InventoryPresenterModule;
import com.tupperware.biz.ui.presenter.InventoryPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tupperware.biz.config.Constant.REQUEST_DATE;

/**
 * Created by dhunter on 2018/11/28.
 * 库存状况
 */

public class InventoryFragment extends BaseFragment implements InventoryContract.View, PtrHandler {

    @BindView(R.id.choose_date)
    TextView mChooseDate;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    InventoryPresenter mPresenter;
    private View rootview;
    private View emptyView;
    private TextView mEmptyText;
    private String currentSelctData;
    private InventoryAdapter mAdapter;
    private Integer storeId;
    private Date currentDate;  //固定，从服务器获取当前时间
    private Date selectDate;
    private boolean flagUseServiceDate = true;


    public static InventoryFragment newInstance(Bundle bundle) {
        InventoryFragment fragment = new InventoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        return rootview;
    }


    @Override
    public void initLayout() {
        DaggerInventoryFragmentComponent.builder()
                .appComponent(getAppComponent())
                .inventoryPresenterModule(new InventoryPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //不使用本地时间，使用服务器时间，传入参数为空的时候，服务器返回的是当前时间的数据
//        mChooseDate.setText(DateFormatter.getCurrentMonthbyFormat());
//        currentSelctData = DateFormatter.timestampToDateToSecond(System.currentTimeMillis());
//        currentDate = new Date();
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new InventoryAdapter(mContext, R.layout.item_hot_inventory_view_recycler);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void requestData() {
        showDialog();
        mPresenter.getHotInventory(storeId, currentSelctData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_inventory;
    }

    public void clickRightNext() {
        FragmentManager manager = getFragmentManager();
        MonthPickerFragment dialog;
        if(currentDate != null) {
            dialog = MonthPickerFragment.newInstance(selectDate, currentDate);
        } else {
            if(selectDate != null) {
                dialog = MonthPickerFragment.newInstance(selectDate, DateFormatter.getCurrentDate());
            } else {
                dialog = MonthPickerFragment.newInstance(DateFormatter.getCurrentDate(), DateFormatter.getCurrentDate());
            }
        }
        dialog.setTargetFragment(InventoryFragment.this, REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(Constant.EXTRA_DATE);
            selectDate = date;
            mChooseDate.setText(DateFormatter.MonthFormat(date));
            currentSelctData = DateFormatter.DateToSecond(date);
            requestData();
        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getHotInventory(storeId, currentSelctData);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setHotInventoryData(HotInventoryResponse response) {
        if(getActivity() == null) {
            return;
        }
        if (response.getModels() != null && response.getModels().size() > 0) {
            mAdapter.setNewData(response.getModels());
        } else {
            setEmptyView();
        }
        if(flagUseServiceDate) {
            if (response.getExtra() != null && response.getExtra().getDate() != null) {
                currentDate = DateFormatter.stringToDate(response.getExtra().getDate());
                selectDate = DateFormatter.stringToDate(response.getExtra().getDate());
                mChooseDate.setText(DateFormatter.MonthFormat(selectDate));
                currentSelctData = DateFormatter.DateToSecond(selectDate);
                flagUseServiceDate = false;
            }
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

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                requestData();
                break;
        }
    }
}
