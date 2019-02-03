package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/8.
 */

public class ResOrderServerDetialActivity extends BaseActivity {

    private static final String TAG = "ResOrderServerDetialActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.server_title)
    TextView mServerTitle;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.time)
    TextView mResTime;
    @BindView(R.id.date)
    TextView mResDate;
    @BindView(R.id.tel_num)
    TextView mTelNum;
    @BindView(R.id.call_contacts)
    TextView mCallContact;
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.server_time_rl)
    RelativeLayout mServerTimeRl;
    @BindView(R.id.server_time)
    TextView mServerTime;
    @BindView(R.id.waiter_rl)
    RelativeLayout mWaiterRl;
    @BindView(R.id.waiter)
    TextView mWaiter;
    @BindView(R.id.call_contacts_rl)
    RelativeLayout mCallNumRl;
    @BindView(R.id.call_num)
    TextView mCallNum;
    @BindView(R.id.affirm_order)
    TextView mAffirmOrder;

    private String fromTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resorder_server_detial;
    }

    @Override
    protected void initLayout() {
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mTitle.setText(getResources().getString(R.string.order_detial));
        mRightNext.setVisibility(View.GONE);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.call_contacts, R.id.server_time_rl, R.id.waiter_rl, R.id.affirm_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.call_contacts:
                try {
                    String telNum = mTelNum.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;
            case R.id.server_time_rl:
                break;
            case R.id.waiter_rl:
                break;
            case R.id.affirm_order:
                finish();
                toast("已接受订单");
                break;
        }
    }
}
