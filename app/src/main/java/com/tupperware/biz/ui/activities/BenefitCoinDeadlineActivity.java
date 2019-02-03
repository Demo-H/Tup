package com.tupperware.biz.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.fragment.BenefitCoinDeadlineFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineActivity extends BaseActivity {

    private static final String TAG = "BenefitCoinDeadlineActivity";

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
    @BindView(R.id.right_image)
    ImageView mRightIcon;

    private BenefitCoinDeadlineFragment mFragment;

    private String mSelectTile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_benefit_coin;
    }


    @Override
    protected void initLayout() {
        mSelectTile = getIntent().getStringExtra(Constant.ACTIVITY_TITLE);
        mRightText.setVisibility(View.GONE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.mbr_filter_btn);
        if(mSelectTile.equals(Constant.BENEFIT)) {
            mTitle.setText(getResources().getString(R.string.benefit_coin_deadline));
        } else if(mSelectTile.equals(Constant.COUPON)) {
            mTitle.setText(getResources().getString(R.string.coupon_deadline));
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ACTIVITY_TITLE, mSelectTile);
        mFragment = BenefitCoinDeadlineFragment.newInstance(bundle);
        mFragment.setRightIcon(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.benefitFrame, mFragment).commit();
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
