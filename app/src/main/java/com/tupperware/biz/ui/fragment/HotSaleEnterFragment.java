package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
import com.tupperware.biz.adapter.HotSaleEnterAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.hotsale.HotInventoryInfo;
import com.tupperware.biz.entity.hotsale.HotSaleEnterInfo;
import com.tupperware.biz.entity.hotsale.HotSaleEnterReqeust;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.listener.IHotSaleSysRefreshListener;
import com.tupperware.biz.ui.component.DaggerHotSaleEnterFragmentComponent;
import com.tupperware.biz.ui.contract.HotSaleEnterContract;
import com.tupperware.biz.ui.module.HotSaleEnterPresenterModule;
import com.tupperware.biz.ui.presenter.HotSaleEnterPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/28.
 * 热卖产品上报录入
 */

public class HotSaleEnterFragment extends BaseFragment implements HotSaleEnterContract.View, PtrHandler {

    @BindView(R.id.choose_date)
    TextView mChooseDate;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    HotSaleEnterPresenter mPresenter;
    private View rootview;
    private View emptyView;
    private TextView mEmptyText;
    private Integer storeId;
    private HotSaleEnterAdapter mAdapter;
//    private String currentSelctData;
    private IHotSaleSysRefreshListener mListener;


    public static HotSaleEnterFragment newInstance(Bundle bundle) {
        HotSaleEnterFragment fragment = new HotSaleEnterFragment();
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
        unbinder = ButterKnife.bind(this, rootview);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        initLayout();
        return rootview;
    }


    @Override
    public void initLayout() {
        DaggerHotSaleEnterFragmentComponent.builder()
                .appComponent(getAppComponent())
                .hotSaleEnterPresenterModule(new HotSaleEnterPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mChooseDate.setText(DateFormatter.getCurrentDay());
//        currentSelctData = DateFormatter.timestampToDateToSecond(System.currentTimeMillis());
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new HotSaleEnterAdapter(mContext, R.layout.item_hot_enter_view_recycler);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        showDialog();
        mPresenter.getHotEnterList(storeId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_sale_enter;
    }

    public void clickRightNext() {
        if(mAdapter == null) {
            return;
        }
        SparseArray<HotSaleEnterInfo> sparseArray = mAdapter.getSparselist();
        if(sparseArray.size() == 0) {
            toast("无可提交数据");
            return;
        }
        HotSaleEnterReqeust requestData = new HotSaleEnterReqeust();
        List<HotSaleEnterInfo> requestList = new ArrayList<>();
        for(int i = 0; i < sparseArray.size(); i++) {
            requestList.add(sparseArray.valueAt(i));
        }
        requestData.setChilList(requestList);
        requestData.setStoreId(storeId);
        showDialog();
        mPresenter.submitHotSale(requestData);

    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getHotEnterList(storeId);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setHotSaleListData(List<HotInventoryInfo> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void setSubmitResult() {
        toast("热卖数据提交成功");
        mListener.refreshData();
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
        setNormalView();
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

    public IHotSaleSysRefreshListener getmListener() {
        return mListener;
    }

    public void setmListener(IHotSaleSysRefreshListener mListener) {
        this.mListener = mListener;
    }
}
