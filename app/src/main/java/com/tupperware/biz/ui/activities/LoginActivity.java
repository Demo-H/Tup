package com.tupperware.biz.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.tablayout.SegmentTabLayout;
import com.android.dhunter.common.widget.tablayout.listener.OnTabSelectListener;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.base.BaseApplication;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.http.LoginDataManager;
import com.tupperware.biz.ui.component.DaggerLoginActivityComponent;
import com.tupperware.biz.ui.contract.LoginContract;
import com.tupperware.biz.ui.module.LoginPresenterModule;
import com.tupperware.biz.ui.presenter.LoginPresenter;
import com.tupperware.biz.utils.AppUtils;
import com.tupperware.biz.utils.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by dhunter on 2018/7/4.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.user_name)
    EditText mUserNameText;
    @BindView(R.id.password)
    EditText mPwText;
    @BindView(R.id.login)
    Button mLoginButton;
    @BindView(R.id.user_login_tab)
    SegmentTabLayout mUserTabLayout;
    private String mUserName;
    private String mPw;
    private int default_min_password_length = 6;
    private int employee_group = 1;  // 0表示店员，1表示店主
    private Integer DEFAULT_SELCE_USER_TYPE = 1;

    @Inject
    LoginPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initLayout() {
        DaggerLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
//        setPermission();
        selectUserType();
//        mUserName = mDataManager.getSPData(GlobalConfig.KEY_DATA_USERID);
        mUserName = (String) mDataManager.getSpObjectData(GlobalConfig.LAST_LOGIN_ACCOUNT, "", GlobalConfig.SHARE_PREFERENCE_SETTING);
        if(mUserName != null && !mUserName.isEmpty()) {
            mUserNameText.setText(mUserName);
            mPwText.requestFocus();
        }
        mUserNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwText.setText("");
            }
        });
        mPwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= default_min_password_length) {
                    mLoginButton.setEnabled(true);
                } else {
                    mLoginButton.setEnabled(false);
                }
            }
        });
    }

    private void selectUserType() {
        mUserTabLayout.setTabData(getResources().getStringArray(R.array.user_type));
        mUserTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position == 0) {
                    employee_group = 1;
                } else {
                    employee_group = 0;
                }
                mDataManager.saveSPObjectData(GlobalConfig.CURRENT_USER_TYPE, employee_group, GlobalConfig.SHARE_PREFERENCE_SETTING);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        employee_group = (Integer)mDataManager.getSpObjectData(GlobalConfig.CURRENT_USER_TYPE, DEFAULT_SELCE_USER_TYPE, GlobalConfig.SHARE_PREFERENCE_SETTING);
        if(employee_group == 0) {
            mUserTabLayout.setCurrentTab(1);
        } else {
            mUserTabLayout.setCurrentTab(0);
        }
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.login, R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                try{
                    if(Build.VERSION.SDK_INT>=23) {
                        if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            // 申请权限
                            Toast.makeText(view.getContext(), "请打开文件使用权限", Toast.LENGTH_SHORT).show();
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);
                            return;
                        }
                    }
                    startLogin();
                } catch (Exception e) {
                    if(Build.VERSION.SDK_INT>=23) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);
                        return;
                    }
                }
                break;
            case R.id.forget_password:
                startToForgetPwActivity();
                break;
        }
    }

    @Override
    public void showLoginResult(LoginInfo loginInfo) {
        if(!loginInfo.isSuccess()) {
            toast(loginInfo.getMessage());
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            //设置推送别名，用户扫码关注跳转页面使用
            if(loginInfo.getExtra().getEmployeeGroup() == 1) {
                String alias = StringUtils.MD5(loginInfo.getExtra().getStoreCode());
                JPushInterface.setAlias(this, 0, alias);
            } else {
                String alias = StringUtils.MD5(loginInfo.getExtra().getEmployeeCode());
                JPushInterface.setAlias(this, 0, alias);
            }
        }
    }


    private void startLogin() {
        mUserName = mUserNameText.getText().toString().trim();
        if (mUserName.isEmpty()) {
            toast(getResources().getString(R.string.user_name_empty));
            return;
        }
        mPw = mPwText.getText().toString().trim();
        if (mPw.isEmpty()) {
            toast(getResources().getString(R.string.password_empty));
            return;
        }
        showDialog();
        mPresenter.trylogin(mUserName, mPw, employee_group);
    }

    private void startToForgetPwActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    // Android 获取相关权限
//    private void setPermission() {
//        try{
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                // 申请权限
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
//                        Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
//                return;
//            }
//        } catch (Exception e) {
//            toast("请打开APP所需所有权限,否则部分功能会出现无法使用的现象");
//        }
//
//    }

    /**
     * 添加点击非EditText 部分隐藏InputMethod
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (AppUtils.isShouldHideInput(mUserNameText, ev) && AppUtils.isShouldHideInput(mPwText, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mUserNameText.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

}
