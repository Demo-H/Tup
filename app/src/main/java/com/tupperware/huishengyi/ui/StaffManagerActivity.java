package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.StaffManagerFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerActivity extends BaseActivity {

    private static final String TAG = "StaffManagerActivity";

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
    ImageView mRightImage;


    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
        StaffManagerFragment mFragment = (StaffManagerFragment) getSupportFragmentManager().findFragmentById(R.id.staffContentFrame);
        if(mFragment == null) {
            mFragment = StaffManagerFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.staffContentFrame, mFragment, "staffmanager").commit();
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.user_add_ic);
        mTitle.setText(getResources().getString(R.string.staff_manager));
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                Intent intent = new Intent(this, AddNewStaffActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.NEW_ADD);
                startActivity(intent);
                break;
        }
    }
}
