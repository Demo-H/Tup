package com.tupperware.huishengyi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.BenefitCoinDeadlineAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerBenefitCoinDeadlineFragmentComponent;
import com.tupperware.huishengyi.ui.module.BenefitCoinDeadlinePresenterModule;
import com.tupperware.huishengyi.ui.contract.BenefitCoinDeadlineContract;
import com.tupperware.huishengyi.ui.presenter.BenefitCoinDeadlinePresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineFragment extends BaseFragment implements BenefitCoinDeadlineContract.View {

    private static final String TAG = "BenefitCoinDeadlineFragment";

    //复用的layout
    @BindView(R.id.expenditure_recyclerview)
    RecyclerView mRecyclerview;

    @Inject
    BenefitCoinDeadlinePresenter mPresenter;

    private BenefitCoinDeadlineAdapter mAdapter;
    private LinearLayout mRightIcon;
    private String mSelectTile;

    public static BenefitCoinDeadlineFragment newInstance(Bundle bundle) {
        BenefitCoinDeadlineFragment fragment = new BenefitCoinDeadlineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSelectTile = bundle.getString(Constant.ACTIVITY_TITLE);
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

    public void setRightIcon(LinearLayout mRightFilter) {
        this.mRightIcon = mRightFilter;
    }

    @Override
    public void initLayout() {
        DaggerBenefitCoinDeadlineFragmentComponent.builder()
                .appComponent(getAppComponent())
                .benefitCoinDeadlinePresenterModule(new BenefitCoinDeadlinePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        mAdapter = new BenefitCoinDeadlineAdapter(R.layout.item_deadline_benefit_recyclerview, mSelectTile);
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getBenefitCoinDeadlineData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expenditure_benefit_coin;    //复用
    }

    @Override
    public void setBenefitCoinDeadlineData(BenefitCoinExpenditureBean benefitCoinExpenditure) {
        mAdapter.addData(benefitCoinExpenditure.content);
    }
}
