package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;

/**
 * Created by dhunter on 2018/3/22.
 */

public class AddNewStaffActivity extends BaseActivity {

    private static final String TAG = "AddNewStaffActivity";

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

    @BindView(R.id.staff_no_rl)
    RelativeLayout mStaffNoRl;
    @BindView(R.id.staff_no)
    TextView mStaffNo;
    @BindView(R.id.staff_name)
    EditText mStaffName;
    @BindView(R.id.staff_tel)
    EditText mStaffTel;
    @BindView(R.id.staff_sex_rl)
    RelativeLayout mSatffSexRl;
    @BindView(R.id.staff_sex)
    TextView mStaffSex;
    @BindView(R.id.staff_hiredate_rl)
    RelativeLayout mStaffHiredateRl;
    @BindView(R.id.staff_hiredate)
    TextView mStaffHiredate;
    @BindView(R.id.staff_password)
    EditText mStaffPassword;
    @BindView(R.id.staff_status_rl)
    RelativeLayout mStaffStatusRl;
    @BindView(R.id.staff_status)
    TextView mStaffStatus;


    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String flag;  // 判断是新增还是修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_staff);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        mContext = this;
        flag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mRightText.setText(getResources().getString(R.string.submit));
        if(Constant.NEW_ADD.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.new_add_staff));
            mStaffNoRl.setVisibility(View.GONE);
        } else if(Constant.MODIFIED.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.staff_info_modified));
        }
    }

    @Override
    protected void initLayoutData() {

    }

}