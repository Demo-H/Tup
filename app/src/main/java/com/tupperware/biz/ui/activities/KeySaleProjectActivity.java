package com.tupperware.biz.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/27.
 * 需求变更，暂时不用该页面
 */

public class KeySaleProjectActivity extends BaseActivity {

    private static final String TAG = "BenefitCoinActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.purpose_vip_enter)
    LinearLayout mPurposerEnter;
    @BindView(R.id.mbr_pure_ic)
    ImageView mPureImage;
    @BindView(R.id.mbr_box_ic)
    ImageView mBoxImage;
    @BindView(R.id.mbr_kit_ic)
    ImageView mKitImage;
    @BindView(R.id.purpose_member_follow)
    LinearLayout mPurposeFollow;
    @BindView(R.id.customer_service_manager)
    LinearLayout mServiceManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_key_sale_project;
    }

    @Override
    protected void initLayout() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.key_sale_project));
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.purpose_vip_enter, R.id.mbr_pure_ic, R.id.mbr_box_ic, R.id.mbr_kit_ic,
            R.id.purpose_member_follow, R.id.customer_service_manager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.purpose_vip_enter:
                jumptoActivity(this, RegisterActivity.class, Constant.KEY_SALE_PROJECT);
                break;
            case R.id.mbr_pure_ic:
                jumptoActivity(this, ScanCouponActivity.class, Constant.WATER_SAFE);
                break;
            case R.id.mbr_box_ic:
                jumptoActivity(this, ScanCouponActivity.class, Constant.PERSONAL_TAILOR);
                break;
            case R.id.mbr_kit_ic:
                jumptoActivity(this, ScanCouponActivity.class, Constant.CARNIVAL);
                break;
            case R.id.purpose_member_follow:
                jumptoActivity(this, PurposeFollowActivity.class, Constant.PORPUSE_FOLLOW);
                break;
            case R.id.customer_service_manager:
                jumptoActivity(this, ServerManagerActivity.class, Constant.SERVER_MANAGER);
                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls, String fromTag ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM, fromTag);
        context.startActivity(intent);
    }
}
