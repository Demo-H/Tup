package com.tupperware.huishengyi.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/29.
 */

public class ReservationMarkActivity extends BaseActivity {

    private static final String TAG = "ReservationMarkActivity";

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

    @BindView(R.id.input_wechat_edit)
    RelativeLayout mWachatEditRl;
    @BindView(R.id.input_wechat_code_edit)
    TextView mWechatEdit;
    @BindView(R.id.input_addr_edit)
    RelativeLayout mAddrEditRl;
    @BindView(R.id.input_addr_code_edit)
    TextView mAddrEdit;

    private String fromTag;
    private String moduleTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_mark;
    }

    @Override
    protected void initLayout() {
        //将相关activity暂时保存，等到扫描完成后再一次性finish
        ActivityManager.getInstance().addActivity(this);
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        moduleTag = getIntent().getStringExtra(Constant.MODULT_FROM);
        mTitle.setText(getResources().getString(R.string.reservation_mark));
        if(Constant.WATER_SAFE.equals(fromTag) || Constant.PERSONAL_TAILOR.equals(fromTag) || Constant.CARNIVAL.equals(fromTag)) {
            mWachatEditRl.setVisibility(View.VISIBLE);
            mAddrEditRl.setVisibility(View.GONE);
        } else if (Constant.RESERVATION_QR.equals(fromTag)) {
            mWachatEditRl.setVisibility(View.GONE);
            mAddrEditRl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(Constant.WATER_SAFE.equals(fromTag) || Constant.PERSONAL_TAILOR.equals(fromTag) || Constant.CARNIVAL.equals(fromTag)) {
                    jumpFromTo(this, ReservationQrActivity.class, fromTag);
                } else if (Constant.RESERVATION_QR.equals(fromTag)) {
                    if(Constant.WATER_SAFE.equals(moduleTag)) {
                        jumpFromTo(this, WaterSafeCheckActivity.class, Constant.FILL_ADDR);
                    } else if(Constant.PERSONAL_TAILOR.equals(moduleTag)) {
                        jumpFromTo(this, ReserPersonalTailorActivity.class, Constant.FILL_ADDR);
                    } else if(Constant.CARNIVAL.equals(moduleTag)) {
                        jumpFromTo(this, ReserCarnivalActivity.class, Constant.FILL_ADDR);
                    }
                }
                break;
        }
    }


    private void jumpFromTo(Context context, Class<?> _cls, String fromFlag) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM, fromFlag);
        startActivity(intent);
    }


    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
