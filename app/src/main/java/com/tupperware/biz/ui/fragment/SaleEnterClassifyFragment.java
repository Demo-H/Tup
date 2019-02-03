package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.SaleEnterAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.saleenter.SaleEnterBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.component.DaggerSaleEnterClassifyFragmentComponent;
import com.tupperware.biz.ui.contract.SaleEnterClassifyContract;
import com.tupperware.biz.ui.module.SaleEnterClassifyPresenterModule;
import com.tupperware.biz.ui.presenter.SaleEnterClassifyPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.data.ProductHistoryProvider;
import com.tupperware.biz.utils.data.ProductProvider;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/25.
 * 销售录入手动分类页
 */

public class SaleEnterClassifyFragment extends BaseFragment implements SaleEnterClassifyContract.View,
        PtrHandler/*, BaseQuickAdapter.RequestLoadMoreListener*/{

    private static final String TAG = "SaleEnterClassifyFragment";
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @Inject
    SaleEnterClassifyPresenter mPresenter;
    private SaleEnterAdapter mAdapter;
    private View rootView;
    private View emptyView;
    private TextView mEmptyText;
    private int mTabPosition;
    private TextView mTextView;
    private String selectDate;
    private boolean isHistory;
    private int SeriesId;

    public static SaleEnterClassifyFragment newInstance(Bundle bundle) {
        SaleEnterClassifyFragment fragment = new SaleEnterClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            selectDate = bundle.getString(Constant.SELECT_DATE);
            SeriesId = bundle.getInt(Constant.SALE_SERIES_ID);
        } else {
            selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        }
        isHistory = DateFormatter.isHistory(selectDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_sale_list));
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerSaleEnterClassifyFragmentComponent.builder()
                .appComponent(getAppComponent())
                .saleEnterClassifyPresenterModule(new SaleEnterClassifyPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new SaleEnterAdapter(R.layout.item_sale_enter_recycleview, mContext, isHistory);
//        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnChangeListener(new SaleEnterAdapter.IListChangeListener() {
            @Override
            public void onChange() {
                refreshListCount();
            }
        });

    }

    @Override
    public void requestData() {
        mPresenter.getSaleEnterData(storeCode, selectDate, SeriesId + "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycle_common;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getSaleEnterData(storeCode, selectDate, SeriesId + "");
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.VISIBLE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.GONE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.VISIBLE);
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public void setSaleEnterData(SaleEnterBean mBean) {
        mAdapter.setNewData(mBean.models);
        if(mBean.models == null) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getSaleEnterData(storeCode, selectDate, SeriesId + "");
                break;
        }
    }

    public void setRefreshView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    private void refreshListCount() {
        if(isHistory) {
            int count = ProductHistoryProvider.getInstance(mContext.getApplicationContext()).getListSize();
            mTextView.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
        } else {
            int count = ProductProvider.getInstance(mContext.getApplicationContext()).getListSize();
            mTextView.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
        }
    }

//    @Override
//    public void setMoreSaleEnterData(SaleEnterBean mBean) {
//        mAdapter.getData().addAll(mBean.models);
//        mAdapter.loadMoreComplete();
//        if(mBean.models.size() == 0) {
//            mAdapter.loadMoreEnd(false); //所有数据加载结束
//        }
//    }

//    @Override
//    public void onLoadMoreRequested() {
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mAdapter.getData().size() == 0){
//                    mAdapter.loadMoreEnd(false);
//                } else{
//                    mPresenter.getMoreSaleEnterData();
//                }
//            }
//        },1000);
//    }
}
