package com.tupperware.huishengyi.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.fragment.BenefitCoinFragment;
import com.tupperware.huishengyi.ui.fragment.CouponVipFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinActivity extends BaseActivity {

    private static final String TAG = "BenefitCoinActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    private BenefitCoinFragment mBenefitCoinFragment;
    private CouponVipFragment mCouponVipFragment;
    private String mSelectTile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_benefit_coin;
    }

    @Override
    protected void initLayout() {
        mSelectTile = getIntent().getStringExtra(Constant.ACTIVITY_TITLE);
        mRightNext.setVisibility(View.GONE);
        if(mSelectTile.equals("Benefit")) {
            mTitle.setText(getResources().getString(R.string.benefit_coin));
            mBenefitCoinFragment = new BenefitCoinFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.benefitFrame, mBenefitCoinFragment).commit();
        } else if(mSelectTile.equals("Coupon")) {
            mTitle.setText(getResources().getString(R.string.personal_store_coupon));
            mCouponVipFragment = new CouponVipFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.benefitFrame, mCouponVipFragment).commit();
        }
    }

    @Override
    protected void requestData() {

    }

    @OnClick(R.id.left_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }

}
