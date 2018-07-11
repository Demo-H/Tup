package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
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

public class DevAgeSelectActivity extends BaseActivity {

    private static final String TAG = "DevAgeSelectActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.young)
    LinearLayout mYoung;
    @BindView(R.id.major)
    LinearLayout mMajor;
    @BindView(R.id.middle_aged)
    LinearLayout mMiddleAged;
    @BindView(R.id.old_age)
    LinearLayout mOldAge;

    private int currentSelPosition = -1; //默认全部不选中

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_age_select;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        mTitle.setText(getResources().getString(R.string.member_age));

    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.next, R.id.young, R.id.major, R.id.middle_aged, R.id.old_age})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(this, DevInfoSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.young:
                selectChange(mYoung, 0);
                break;
            case R.id.major:
                selectChange(mMajor, 1);
                break;
            case R.id.middle_aged:
                selectChange(mMiddleAged, 2);
                break;
            case R.id.old_age:
                selectChange(mOldAge, 3);
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
                mYoung.setSelected(false);
            } else if(currentSelPosition == 1) {
                mMajor.setSelected(false);
            } else if(currentSelPosition == 2) {
                mMiddleAged.setSelected(false);
            } else if(currentSelPosition == 3) {
                mOldAge.setSelected(false);
            }
            currentSelPosition = position;
        }
    }

}
