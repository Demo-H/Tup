package com.tupperware.biz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.SaleStatusAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.saleenter.SaleTabBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.listener.ISaleEnterListener;
import com.tupperware.biz.ui.activities.EnterListActivity;
import com.tupperware.biz.ui.activities.ProductSearchActivity;
import com.tupperware.biz.ui.component.DaggerSaleHandFragmentComponent;
import com.tupperware.biz.ui.contract.SaleHandContract;
import com.tupperware.biz.ui.module.SaleHandPresenterModule;
import com.tupperware.biz.ui.presenter.SaleHandPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.TabLayoutUtils;
import com.tupperware.biz.utils.data.ProductHistoryProvider;
import com.tupperware.biz.utils.data.ProductProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/25.
 * 销售手动录入
 */

public class SaleHandFragment extends BaseFragment implements SaleHandContract.View{

    @BindView(R.id.search)
    LinearLayout mSearchll;
    @BindView(R.id.scan_rl)
    RelativeLayout mScanRl;
    @BindView(R.id.success_layout)
    LinearLayout mSuccessLl;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorRl;
    @BindView(R.id.sale_status_tab)
    TabLayout mStatusTab;
    @BindView(R.id.sale_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.generate_list_rl)
    RelativeLayout mGenerateListrl;
    @BindView(R.id.generate_list_count)
    TextView mListCount;
    @BindView(R.id.generate_list)
    TextView mGenerateList;

    private View rootview;
    private SaleStatusAdapter mAdapter;
    @Inject
    SaleHandPresenter mPresenter;
    private ISaleEnterListener mEnterListener;
    private String selectDate;
    private boolean isHistory = false;

    public static SaleHandFragment newInstance(Bundle bundle) {
        SaleHandFragment fragment = new SaleHandFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectDate = bundle.getString(Constant.SELECT_DATE);
        } else {
            selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        }
        isHistory = DateFormatter.isHistory(selectDate);
        toast(selectDate);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
//        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        DaggerSaleHandFragmentComponent.builder()
                .appComponent(getAppComponent())
                .saleHandPresenterModule(new SaleHandPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        refreshCount();
    }

    @Override
    public void requestData() {
        mPresenter.getLableData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sale_hand;
    }

    private void initTab(SaleTabBean mBean) {
        mAdapter = new SaleStatusAdapter(rootview.getContext(), getActivity().getSupportFragmentManager(), mBean, selectDate);
        mAdapter.setRefreshView(mListCount);
        mViewPager.setAdapter(mAdapter);
        mStatusTab.setupWithViewPager(mViewPager);
        if(mBean != null && mBean.models!=null && mBean.models.size() > 5) {
            mStatusTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mStatusTab.setTabMode(TabLayout.MODE_FIXED);
        }
        TabLayoutUtils.getInstance().setIndicator(mStatusTab);
    }

    @Override
    public void setLableData(SaleTabBean mBean) {
        if(mBean!= null && mBean.models != null && mBean.models.size() > 0) {
            initTab(mBean);
        }
    }

    @Override
    public void setNetErrorView() {
        mSuccessLl.setVisibility(View.GONE);
        mGenerateListrl.setVisibility(View.GONE);
        mErrorRl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNormalView() {
        mSuccessLl.setVisibility(View.VISIBLE);
        mGenerateListrl.setVisibility(View.VISIBLE);
        mErrorRl.setVisibility(View.GONE);
    }

//    @Override
//    public void setTestData() {
//        initTab(null);
//    }

    @OnClick({R.id.error_layout, R.id.search, R.id.generate_list, R.id.scan_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getLableData();
                break;
            case R.id.search:
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra(Constant.SELECT_DATE, selectDate);
                startActivityForResult(intent, Constant.REQUEST_INTENT_SEARCH_CODE);
                break;
            case R.id.generate_list:
                Intent genIntent = new Intent(getActivity(), EnterListActivity.class);
                genIntent.putExtra(Constant.SELECT_DATE, selectDate);
                startActivity(genIntent);
                break;
            case R.id.scan_rl:
                if(mEnterListener != null) {
                    mEnterListener.onClick(view);
                }
                break;
        }
    }


    public void refreshCount() {
        int count;
        if(isHistory) {
            count = ProductHistoryProvider.getInstance(mContext.getApplicationContext()).getListSize();
            mListCount.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
        } else {
            count = ProductProvider.getInstance(mContext.getApplicationContext()).getListSize();
            mListCount.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_INTENT_SEARCH_CODE) {
            refreshCount();
        }
    }

    public ISaleEnterListener getmEnterListener() {
        return mEnterListener;
    }

    public void setmEnterListener(ISaleEnterListener mEnterListener) {
        this.mEnterListener = mEnterListener;
    }

}
