package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/21.
 */

public class StoreManagerActivity extends BaseActivity {

    private static final String TAG = "StoreManagerActivity";

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

    @BindView(R.id.store_image_ll)
    RelativeLayout mStoreImageRl;
    @BindView(R.id.store_image)
    SimpleDraweeView mStoreImage;
    @BindView(R.id.store_name)
    TextView mStoreName;
    @BindView(R.id.store_wechat)
    TextView mStoreWechat;
    @BindView(R.id.staff_manager_rl)
    RelativeLayout mStaffManager;
    @BindView(R.id.allow_check_vip_tel)
    TextView mAllowCheckTel;
    @BindView(R.id.allow_check_vip_addr)
    TextView mAllowCheckAddr;
    @BindView(R.id.water_purifier_install)
    TextView mWaterPurifierInstall;
    @BindView(R.id.water_purifier_try_use)
    TextView getmWaterPurifierTryUse;
    @BindView(R.id.water_purifier_change)
    TextView getmWaterPurifierChange;
    @BindView(R.id.personal_tailor)
    TextView mPersonalTailor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void initLayout() {
        initToolBar();
    }

    @Override
    protected void requestData() {

    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.store_manager));
    }

    private void changeSelectStatus(TextView textView) {
        if(textView.isSelected()) {
            textView.setSelected(false);
        } else {
            textView.setSelected(true);
        }
    }

    @OnClick({R.id.left_back, R.id.store_image_ll, R.id.staff_manager_rl, R.id.allow_check_vip_tel, R.id.allow_check_vip_addr,
            R.id.water_purifier_install, R.id.water_purifier_try_use, R.id.water_purifier_change, R.id.personal_tailor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.store_image_ll:
                break;
            case R.id.staff_manager_rl:
                Intent intent = new Intent(this, StaffManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.allow_check_vip_tel:
                changeSelectStatus(mAllowCheckTel);
                break;
            case R.id.allow_check_vip_addr:
                changeSelectStatus(mAllowCheckAddr);
                break;
            case R.id.water_purifier_install:
                changeSelectStatus(mWaterPurifierInstall);
                break;
            case R.id.water_purifier_try_use:
                changeSelectStatus(getmWaterPurifierTryUse);
                break;
            case R.id.water_purifier_change:
                changeSelectStatus(getmWaterPurifierChange);
                break;
            case R.id.personal_tailor:
                changeSelectStatus(mPersonalTailor);
                break;
        }
    }
}
