package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.fragment.StaffManagerFragment;

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

    private StaffManagerFragment mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_manager;
    }

    @Override
    protected void initLayout() {
        initToolBar();
        mFragment = (StaffManagerFragment) getSupportFragmentManager().findFragmentById(R.id.staffContentFrame);
        if(mFragment == null) {
            mFragment = StaffManagerFragment.newInstance();
        }
        if(!mFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.staffContentFrame, mFragment, "staffmanager").commit();
        }
    }

    @Override
    protected void requestData() {

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
                startActivityForResult(intent, Constant.REQUEST_DATA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (mFragment != null) {
                mFragment.requestData();
            }
        }
    }
}
