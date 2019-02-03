package com.tupperware.biz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.StringUtils;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.MeBenfitCoin;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.activities.BenefitCoinActivity;
import com.tupperware.biz.ui.activities.LoginActivity;
import com.tupperware.biz.ui.activities.StaffManagerActivity;
import com.tupperware.biz.ui.component.DaggerMeFragmentComponent;
import com.tupperware.biz.ui.contract.MeContract;
import com.tupperware.biz.ui.module.MePresenterModule;
import com.tupperware.biz.ui.presenter.MePresenter;
import com.tupperware.biz.utils.ImageUrl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/9/6.
 */

public class MeFragment extends BaseFragment implements MeContract.View, PtrHandler {

    @BindView(R.id.me_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.personal_img)
    SimpleDraweeView mPersonalImag;
    @BindView(R.id.personal_store_code)
    TextView mStoreCode;
    @BindView(R.id.personal_store_title)
    TextView mStoreName;
    @BindView(R.id.personal_store_address)
    TextView mStoreAddr;
    @BindView(R.id.personal_store_telephone)
    TextView mStoreTel;
    @BindView(R.id.benefit_coin_num)
    TextView mBenefitNum;
    @BindView(R.id.coupon_num)
    TextView mCouponNum;
    @BindView(R.id.staff_manager)
    RelativeLayout mStaffManager;

    @Inject
    MePresenter mPresenter;
    private Integer storeId;
    private View rootView;


    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerMeFragmentComponent.builder()
                .appComponent(getAppComponent())
                .mePresenterModule(new MePresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        String employeeGroup =  mDataManager.getSPData(GlobalConfig.EMPLOYEE_GROUP);
        if(StringUtils.StringChangeToInt(employeeGroup) == 0) {
            mStaffManager.setVisibility(View.GONE);   //店员
        } else {
            mStaffManager.setVisibility(View.VISIBLE);  //店主
        }
    }

    @Override
    public void requestData() {
        String json = mDataManager.getSPData(GlobalConfig.KEY_DATA_LOGIN_INFO);
        if(json == null) {
            return;
        }
        LoginInfo logininfo = LoginInfo.createInstanceByJson(json);
        if(logininfo.getExtra() != null) {
            mStoreCode.setText(logininfo.getExtra().getStoreCode());
            mStoreName.setText(logininfo.getExtra().getStoreName());
            mStoreAddr.setText(logininfo.getExtra().getStoreAddress());
            mStoreTel.setText(logininfo.getExtra().getStorePhone());
            String imageUrl = logininfo.getExtra().getStoreImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String url = ImageUrl.parseUrl(imageUrl);
                mPersonalImag.setImageURI(url);
            }
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMeData(storeId);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setMeData(MeBenfitCoin bean) {
        if(getActivity() == null) {
            return;
        }
        if(mBenefitNum != null && mCouponNum != null) {
            mBenefitNum.setText(bean.getModel().getStoreIntegralAmount() + "");
            mCouponNum.setText(bean.getModel().getStoreCouponTotal() + "");
        }
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        mPresenter.getMeData(storeId);
    }

    @OnClick({R.id.personal_store_benefit_coin, R.id.personal_store_coupon, R.id.staff_manager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_store_benefit_coin:
                Intent benefitIntent = new Intent(rootView.getContext(), BenefitCoinActivity.class);
                benefitIntent.putExtra(Constant.ACTIVITY_TITLE, "Benefit");
                startActivity(benefitIntent);
                break;
            case R.id.personal_store_coupon:
                Intent couponIntent = new Intent(rootView.getContext(), BenefitCoinActivity.class);
                couponIntent.putExtra(Constant.ACTIVITY_TITLE, "Coupon");
                startActivity(couponIntent);
                break;
            case R.id.staff_manager:
                Intent i = new Intent(rootView.getContext(), StaffManagerActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void reLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
