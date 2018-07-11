package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.BenefitCoinExpenditureAdapter;
import com.tupperware.huishengyi.adapter.BenefitCoinIncomeAdapter;
import com.tupperware.huishengyi.adapter.CouponUnUsedAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerBenefitCoinExpenditureFragmentComponent;
import com.tupperware.huishengyi.ui.module.BenefitCoinExpenditurePresenterModule;
import com.tupperware.huishengyi.ui.contract.BenefitCoinExpenditureContract;
import com.tupperware.huishengyi.ui.presenter.BenefitCoinExpenditurePresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditureFragment extends BaseFragment implements BenefitCoinExpenditureContract.View {

    private static final String TAG = "BenefitCoinExpenditureFragment";

    @BindView(R.id.expenditure_recyclerview)
    RecyclerView mExpenditureRecyclerview;
//    @BindView(R.id.find_pull_refresh_header)
//    PullHeaderView pendingPullRefreshHeader;

    private int mTabPosition;
    private String mFlag; //标记惠金币还是券码

    @Inject
    BenefitCoinExpenditurePresenter mPresenter;

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
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            mFlag = bundle.getString(Constant.FRAGMENT_FLAG);
        }
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
        DaggerBenefitCoinExpenditureFragmentComponent.builder()
                .appComponent(getAppComponent())
                .benefitCoinExpenditurePresenterModule(new BenefitCoinExpenditurePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mExpenditureRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mExpenditureRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        if(Constant.BENEFIT.equals(mFlag)) {
            if(mTabPosition == 0) { //支出
                mExpAdapter = new BenefitCoinExpenditureAdapter(R.layout.item_expenditure_benefit_recyclerview);
                mExpenditureRecyclerview.setAdapter(mExpAdapter);
            } else if(mTabPosition == 1) {//收入
                mIncAdapter = new BenefitCoinIncomeAdapter(R.layout.item_income_benefit_recyclerview);
                mExpenditureRecyclerview.setAdapter(mIncAdapter);
            }
        } else if(Constant.COUPON.equals(mFlag)) {
            mUnUseAdapter = new CouponUnUsedAdapter(R.layout.item_not_use_coupon_recyclerview);
            mExpenditureRecyclerview.setAdapter(mUnUseAdapter);
        }

    }

    @Override
    public void requestData() {
        mPresenter.getBenefitCoinExpenditureData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expenditure_benefit_coin;
    }


    @Override
    public void setBenefitCoinExpenditureData(BenefitCoinExpenditureBean benefitCoinExpenditure) {
        if(Constant.BENEFIT.equals(mFlag)) {
            if(mTabPosition == 0) {
                mExpAdapter.addData(benefitCoinExpenditure.content);
            } else  if(mTabPosition == 1) {
                mIncAdapter.addData(benefitCoinExpenditure.content);
            }
        } else if(Constant.COUPON.equals(mFlag)) {
            mUnUseAdapter.addData(benefitCoinExpenditure.content);
        }

    }
}
