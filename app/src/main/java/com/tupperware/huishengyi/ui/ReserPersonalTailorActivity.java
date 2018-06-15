package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.flowlayout.FlowLayout;
import com.android.dhunter.common.widget.flowlayout.TagAdapter;
import com.android.dhunter.common.widget.flowlayout.TagFlowLayout;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ReserPersonalTailorActivity extends BaseActivity {
    private static final String TAG = "WaterSafeCheckActivity";

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

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.tel_num)
    TextView mTelNum;
    @BindView(R.id.call_contacts)
    TextView mCallContact;
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.server_time_rl)
    RelativeLayout mServerTimeRl;
    @BindView(R.id.server_time)
    TextView mServerTime;
    @BindView(R.id.waiter_rl)
    RelativeLayout mWaiterRl;
    @BindView(R.id.waiter)
    TextView mWaiter;
    @BindView(R.id.call_num)
    TextView mCallNum;
    @BindView(R.id.brand_model_rl)
    RelativeLayout mBrandModelRl;
    @BindView(R.id.brand_model)
    TextView mBrandModel;


    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String fromTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser_personal_tailor);
        //将相关activity暂时保存，等到扫描完成后再一次性finish
        ActivityManager.getInstance().addActivity(this);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mContext = this;
        initLayoutData();
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.reser_personal_tailor_mark));
        mRightText.setText(getResources().getString(R.string.submit));
        mTagFlowLayout.setAdapter(new TagAdapter<String>(getStringArray(R.array.member_flag)) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.textview_tag_select,
                        mTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @Override
    protected void initLayoutData() {

    }

    @OnClick({R.id.left_back, R.id.next, R.id.call_contacts, R.id.server_time_rl, R.id.waiter_rl, R.id.brand_model_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                toast("提交完成");
                ActivityManager.getInstance().exit();
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
            case R.id.brand_model_rl:
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
