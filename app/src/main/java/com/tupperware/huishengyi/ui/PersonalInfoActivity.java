package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/19.
 */

public class PersonalInfoActivity extends BaseActivity {

    private static final String TAG = "PersonalInfoActivity";

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

    @BindView(R.id.personal_info)
    LinearLayout mPersonalInfoLL;
    @BindView(R.id.personal_img)
    SimpleDraweeView mPersonalImage;
    @BindView(R.id.personal_store_title)
    TextView mStoreTitle;
    @BindView(R.id.personal_store_address)
    TextView mStoreAddr;
    @BindView(R.id.personal_store_telephone)
    TextView mStoreTel;
    @BindView(R.id.personal_store_qr_code)
    RelativeLayout mQrCodeRl;
    @BindView(R.id.personal_store_benefit_coin)
    RelativeLayout mBenefitCoinRl;
    @BindView(R.id.personal_store_coupon)
    RelativeLayout mCouponRl;
    @BindView(R.id.personal_store_rewards)
    RelativeLayout mRewardsRl;
    @BindView(R.id.personal_store_setting)
    RelativeLayout mSettingRl;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ActivityManager.getInstance().addActivity(this);
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setText(getResources().getString(R.string.change_store));
        mTitle.setText(getResources().getString(R.string.personal_info));
    }

    @OnClick({R.id.left_back, R.id.next, R.id.personal_info, R.id.personal_store_qr_code, R.id.personal_store_benefit_coin,
            R.id.personal_store_coupon, R.id.personal_store_rewards, R.id.personal_store_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                jumptoActivity(view.getContext(), StoreSwitchActivity.class, null);
                break;
            case R.id.personal_info:
                jumptoActivity(view.getContext(), StoreManagerActivity.class, null);
                break;
            case R.id.personal_store_qr_code:
                jumptoActivity(view.getContext(), PersonalQrActivity.class, null);
                break;
            case R.id.personal_store_benefit_coin:
                jumptoActivity(view.getContext(), BenefitCoinActivity.class, "Benefit");
                break;
            case R.id.personal_store_coupon:
                jumptoActivity(view.getContext(), BenefitCoinActivity.class, "Coupon");
                break;
            case R.id.personal_store_rewards:
                break;
            case R.id.personal_store_setting:
                jumptoActivity(view.getContext(), PersonalSettingActivity.class, null);
                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls, String string ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.PERSONAL_INFO);
        if(string != null) {
            intent.putExtra(Constant.ACTIVITY_TITLE, string);
        }
        context.startActivity(intent);
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeAll();
        finish();
    }

}
