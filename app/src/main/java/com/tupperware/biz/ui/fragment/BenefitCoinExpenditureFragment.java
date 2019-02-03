package com.tupperware.biz.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.BenefitCoinAllAdapter;
import com.tupperware.biz.adapter.BenefitCoinExpenditureAdapter;
import com.tupperware.biz.adapter.BenefitCoinIncomeAdapter;
import com.tupperware.biz.adapter.CouponUnUsedAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.benefit.BenefitCoinResponse;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerBenefitCoinExpenditureFragmentComponent;
import com.tupperware.biz.ui.contract.BenefitCoinExpenditureContract;
import com.tupperware.biz.ui.module.BenefitCoinExpenditurePresenterModule;
import com.tupperware.biz.ui.presenter.BenefitCoinExpenditurePresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditureFragment extends BaseFragment implements BenefitCoinExpenditureContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "BenefitCoinExpenditureFragment";

    @BindView(R.id.expenditure_recyclerview)
    RecyclerView mExpenditureRecyclerview;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView pendingPullRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @BindView(R.id.linearLayout_mask)
    LinearLayout mLinearMask;

    private int mTabPosition;
    private String mFlag; //标记惠金币还是券码
    private Context mContext;
    private boolean needInit = false;
    private View emptyView;
    private TextView mEmptyTip;
    private int pageIndex;  //分页加载更多，从第二页开始
    private int status = 99; //默认一个无效的数字

    @Inject
    BenefitCoinExpenditurePresenter mPresenter;

    private BenefitCoinAllAdapter mAllAdapter;
    private BenefitCoinExpenditureAdapter mExpAdapter;
    private BenefitCoinIncomeAdapter mIncAdapter;
    private CouponUnUsedAdapter mUnUseAdapter;

    public static BenefitCoinExpenditureFragment newInstance(Bundle bundle) {
        BenefitCoinExpenditureFragment fragment = new BenefitCoinExpenditureFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mContext = getContext();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            mFlag = bundle.getString(Constant.FRAGMENT_FLAG);
        }
        pageIndex = 2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyTip = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyTip.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, view);
//        isViewCreated = true;
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerBenefitCoinExpenditureFragmentComponent.builder()
                .appComponent(getAppComponent())
                .benefitCoinExpenditurePresenterModule(new BenefitCoinExpenditurePresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        pendingPullRefreshHeader.setPtrHandler(this);
        mExpenditureRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        if(Constant.BENEFIT.equals(mFlag)) {
            mLinearMask.setVisibility(View.GONE);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
            mExpenditureRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
            if(mTabPosition == 0) {//全部
                mAllAdapter = new BenefitCoinAllAdapter(R.layout.item_income_benefit_recyclerview, mContext);
                mAllAdapter.setOnLoadMoreListener(this);
                mAllAdapter.disableLoadMoreIfNotFullPage(mExpenditureRecyclerview);
                mAllAdapter.setEnableLoadMore(true);
                mAllAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                mExpenditureRecyclerview.setAdapter(mAllAdapter);
            }else if(mTabPosition == 1) { //支出
                mExpAdapter = new BenefitCoinExpenditureAdapter(R.layout.item_income_benefit_recyclerview, mContext); //item_expenditure_benefit_recyclerview
                mExpAdapter.setOnLoadMoreListener(this);
                mExpAdapter.disableLoadMoreIfNotFullPage(mExpenditureRecyclerview);
                mExpAdapter.setEnableLoadMore(true);
                mExpAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                mExpenditureRecyclerview.setAdapter(mExpAdapter);
            } else if(mTabPosition == 2) {//收入
                mIncAdapter = new BenefitCoinIncomeAdapter(R.layout.item_income_benefit_recyclerview);
                mIncAdapter.setOnLoadMoreListener(this);
                mIncAdapter.disableLoadMoreIfNotFullPage(mExpenditureRecyclerview);
                mIncAdapter.setEnableLoadMore(true);
                mIncAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                mExpenditureRecyclerview.setAdapter(mIncAdapter);
            }
        } else if(Constant.COUPON.equals(mFlag)) {
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
            mExpenditureRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
            mUnUseAdapter = new CouponUnUsedAdapter(R.layout.item_not_use_coupon_recyclerview, mTabPosition);
            mUnUseAdapter.setOnLoadMoreListener(this);
            mUnUseAdapter.disableLoadMoreIfNotFullPage(mExpenditureRecyclerview);
            mUnUseAdapter.setEnableLoadMore(true);
            mUnUseAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mExpenditureRecyclerview.setAdapter(mUnUseAdapter);
            if(mTabPosition == 1) {
                mLinearMask.setVisibility(View.VISIBLE);
            } else {
                mLinearMask.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void requestData() {
        if(Constant.BENEFIT.equals(mFlag)) {
            if(mTabPosition == 0) {
                status = 0;
            } else if(mTabPosition == 1) {
                status = 1;
            } else if(mTabPosition == 2) {
                status = 2;
            }
            showDialog();
            mPresenter.getBenefitCoinExpenditureData(status);
        } else if(Constant.COUPON.equals(mFlag)) {
            if(mTabPosition == 0) {
                status = Constant.NOT_USE;
            } else if(mTabPosition == 1) {
                status = Constant.IS_USED;
            }
            showDialog();
            mPresenter.getCouponListData(status);
        }

//        if(needInit) {
//            needInit = false;
//            showDialog();
//            mPresenter.getBenefitCoinExpenditureData();
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expenditure_benefit_coin;
    }

//    @Override
//    protected void lazyLoadData() {
//        super.lazyLoadData();
//        if(isViewCreated = true) {
//            showDialog();
//            mPresenter.getBenefitCoinExpenditureData();
//        } else {
//            needInit = true;
//        }
//    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Constant.BENEFIT.equals(mFlag)) {
                    mPresenter.getBenefitCoinExpenditureData(status);
                } else if(Constant.COUPON.equals(mFlag)){
                    mPresenter.getCouponListData(status);
                }
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        if(pendingPullRefreshHeader != null) {
            pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNetErrorView() {
        if(pendingPullRefreshHeader != null) {
            pendingPullRefreshHeader.setVisibility(View.GONE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEmptyView() {
        pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if(Constant.BENEFIT.equals(mFlag)) {
            if(mTabPosition == 0) {
                mAllAdapter.setEmptyView(emptyView);
            } else if(mTabPosition == 1) {
                mExpAdapter.setEmptyView(emptyView);
            } else if(mTabPosition == 2) {
                mIncAdapter.setEmptyView(emptyView);
            }
        } else if(Constant.COUPON.equals(mFlag)) {
            mUnUseAdapter.setEmptyView(emptyView);
        }
    }

    @Override
    public void setBenefitCoinExpenditureData(BenefitCoinResponse mBean) {
        pageIndex = 2; //重置
        if(mTabPosition == 0) {
            mAllAdapter.setNewData(mBean.getPageInfo().getList());
            if(!mBean.getPageInfo().isHasNextPage()) {
                mAllAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        } else if(mTabPosition == 1) {
            mExpAdapter.setNewData(mBean.getPageInfo().getList());
            if(!mBean.getPageInfo().isHasNextPage()) {
                mExpAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        } else if(mTabPosition == 2) {
            mIncAdapter.setNewData(mBean.getPageInfo().getList());
            if(!mBean.getPageInfo().isHasNextPage()) {
                mIncAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        }
    }

    @Override
    public void setMoreBenefitCoinExpenditureData(BenefitCoinResponse mBean) {
        if(mTabPosition == 0) {
            mAllAdapter.getData().addAll(mBean.getPageInfo().getList());
            mAllAdapter.loadMoreComplete();
            if(!mBean.getPageInfo().isHasNextPage()) {
                mAllAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        } else if(mTabPosition == 1) {
            mExpAdapter.getData().addAll(mBean.getPageInfo().getList());
            mExpAdapter.loadMoreComplete();
            if(!mBean.getPageInfo().isHasNextPage()) {
                mExpAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        } else if(mTabPosition == 2) {
            mIncAdapter.getData().addAll(mBean.getPageInfo().getList());
            mIncAdapter.loadMoreComplete();
            if(!mBean.getPageInfo().isHasNextPage()) {
                mIncAdapter.loadMoreEnd(false); //所有数据加载结束
            }
        }
    }

    @Override
    public void setCouponListData(CouponResponse mBean) {
        pageIndex = 2; //重置
        if(mBean.getPageInfo().getList() == null || mBean.getPageInfo().getList().size() == 0) {
            setEmptyView();
        } else {
            mUnUseAdapter.setNewData(mBean.getPageInfo().getList());
        }
        if(!mBean.getPageInfo().isHasNextPage()) {
            mUnUseAdapter.loadMoreEnd(false); //所有数据加载结束
        }

    }

    @Override
    public void setMoreCouponListData(CouponResponse mBean) {
        mUnUseAdapter.getData().addAll(mBean.getPageInfo().getList());
        mUnUseAdapter.loadMoreComplete();
        if(!mBean.getPageInfo().isHasNextPage()) {
            mUnUseAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mExpenditureRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Constant.BENEFIT.equals(mFlag)) {
                    if(mTabPosition == 0) {
                        if (mAllAdapter.getData().size() == 0){
                            mAllAdapter.loadMoreEnd(false);
                        } else{
                            mPresenter.getMoreBenefitCoinExpenditureData(status, pageIndex);
                            pageIndex++;
                        }
                    } else if(mTabPosition == 1) {
                        if (mExpAdapter.getData().size() == 0){
                            mExpAdapter.loadMoreEnd(false);
                        } else{
                            mPresenter.getMoreBenefitCoinExpenditureData(status, pageIndex);
                            pageIndex++;
                        }
                    } else if(mTabPosition == 2) {
                        if (mIncAdapter.getData().size() == 0){
                            mIncAdapter.loadMoreEnd(false);
                        } else{
                            mPresenter.getMoreBenefitCoinExpenditureData(status, pageIndex);
                            pageIndex++;
                        }
                    }
                } else if(Constant.COUPON.equals(mFlag)) {
                    if (mUnUseAdapter.getData().size() == 0){
                        mUnUseAdapter.loadMoreEnd(false);
                    } else{
                        mPresenter.getMoreCouponListData(status, pageIndex);
                        pageIndex++;
                    }
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                if(Constant.BENEFIT.equals(mFlag)) {
                    mPresenter.getBenefitCoinExpenditureData(status);
                } else if(Constant.COUPON.equals(mFlag)) {
                    mPresenter.getCouponListData(status);
                }
                break;
        }
    }

}
