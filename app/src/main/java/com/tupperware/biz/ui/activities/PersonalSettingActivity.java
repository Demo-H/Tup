package com.tupperware.biz.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerPersonalSettingActivityComponent;
import com.tupperware.biz.ui.contract.PersonalSettingContract;
import com.tupperware.biz.ui.module.PersonalSettingPresenterModule;
import com.tupperware.biz.ui.presenter.PersonalSettingPresenter;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/19.
 * 个人中心
 */

public class PersonalSettingActivity extends BaseActivity implements PersonalSettingContract.View {

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

    @BindView(R.id.schedule)
    RelativeLayout mSchedule;
    @BindView(R.id.modified_psw)
    RelativeLayout mModifiedPsw;
    @BindView(R.id.user_feedback)
    RelativeLayout mUserFeedback;
    @BindView(R.id.function_infomation)
    RelativeLayout mFunctionInfo;
    @BindView(R.id.device_manager)
    RelativeLayout mDeviceManager;
    @BindView(R.id.about_us)
    RelativeLayout mAboutUs;
    @BindView(R.id.login_exit)
    TextView mLoginExit;
    @BindView(R.id.version)
    TextView mVersionText;
//    @BindView(R.id.develop_copy)
//    RelativeLayout mCopyData;
//    @BindView(R.id.staff_manager)
//    RelativeLayout mStaffManager;

    private String appVersion;

    @Inject
    PersonalSettingPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_setting;
    }

    @Override
    protected void initLayout() {
        //将相关activity暂时保存，退出登录finish
        ActivityManager.getInstance().addActivity(this);
        initToolBar();
        DaggerPersonalSettingActivityComponent.builder()
                .appComponent(getAppComponent())
                .personalSettingPresenterModule(new PersonalSettingPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
//        String employeeGroup =  mDataManager.getSPData(GlobalConfig.EMPLOYEE_GROUP);
//        if(StringUtils.StringChangeToInt(employeeGroup) == 0) {
//            mStaffManager.setVisibility(View.GONE);   //店员
//        } else {
//            mStaffManager.setVisibility(View.VISIBLE);  //店主
//        }
    }

    @Override
    protected void requestData() {
        appVersion = AppUtils.getVersionName();
        mVersionText.setText("V" + appVersion);
    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
//        mTitle.setText(getResources().getString(R.string.setting));
        mTitle.setText(getResources().getString(R.string.setting));
    }


    @OnClick({R.id.left_back, R.id.modified_psw, R.id.login_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
//            case R.id.schedule:
//                Intent actIntent = new Intent(PersonalSettingActivity.this, ActionListActivity.class);
//                actIntent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.PERSONAL_SETTING);
//                startActivity(actIntent);
//                break;
//            case R.id.staff_manager:
//                Intent i = new Intent(PersonalSettingActivity.this, StaffManagerActivity.class);
//                startActivity(i);
//                break;
            case R.id.modified_psw:
                Intent intent = new Intent(PersonalSettingActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
//            case R.id.user_feedback:
//                break;
//            case R.id.function_infomation:
//                break;
//            case R.id.device_manager:
//                break;
//            case R.id.about_us:
//                break;
            case R.id.login_exit:
                logout();
                break;
//            case R.id.develop_copy:
//                FileUtils.copyDataBaseToSD(mContext.getApplicationContext());
//                break;
        }
    }

    @Override
    public void setLogoutSuccess() {
        toast(getResources().getString(R.string.login_exit));
        jumptoActivity(this, LoginActivity.class);
        ActivityManager.getInstance().exit();
    }

    private void jumptoActivity(Context context, Class<?> _cls ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        context.startActivity(intent);
    }

    private void logout() {
        showDialog();
        mPresenter.logout();
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        ActivityManager.getInstance().removeActivity(this);
        /**
         * 为了退出登录，将首页也加入ActivityManager的list，所以在该页面返回首页的时候，需要将list清空，防止内存泄漏。
         */
        ActivityManager.getInstance().removeAll();
        finish();
    }

}
