package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/27.
 */

public class DevSexSelectActivity extends BaseActivity {

    private static final String TAG = "DevProductSelectActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.female_ll)
    LinearLayout mFemale;
    @BindView(R.id.male_ll)
    LinearLayout mMale;

    private int currentSelPosition = -1; //默认全部不选中


    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_sex_select;
    }
    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        mTitle.setText(getResources().getString(R.string.member_sex));

    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.next, R.id.female_ll, R.id.male_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(this, DevAgeSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.female_ll:
                selectChange(mFemale, 0);
                break;
            case R.id.male_ll:
                selectChange(mMale, 1);
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

    private void selectChange(LinearLayout mLinlayout, int position) {
        if(mLinlayout.isSelected()) {
            mLinlayout.setSelected(false);
            currentSelPosition = -1;
        } else {
            mLinlayout.setSelected(true);
            if(currentSelPosition == 0) {
                mFemale.setSelected(false);
            } else if(currentSelPosition == 1) {
                mMale.setSelected(false);
            }
            currentSelPosition = position;
        }
    }

}
