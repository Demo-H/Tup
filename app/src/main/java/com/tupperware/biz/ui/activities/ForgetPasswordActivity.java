package com.tupperware.biz.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.entity.login.ForgetPwInfo;
import com.tupperware.biz.entity.login.ResetPwdRequest;
import com.tupperware.biz.http.LoginDataManager;
import com.tupperware.biz.ui.component.DaggerForgetPasswordActivityComponent;
import com.tupperware.biz.ui.contract.ForgetPasswordContract;
import com.tupperware.biz.ui.module.ForgetPasswordPresenterModule;
import com.tupperware.biz.ui.presenter.ForgetPasswordPresenter;
import com.tupperware.biz.utils.CheckUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/9.
 * 忘记密码和修改密码在该UI上进行
 */

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {

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
    @BindView(R.id.no)
    EditText mNoEdit;
    @BindView(R.id.desc)
    TextView mDescText;

    @BindView(R.id.modified_psw_rl)
    RelativeLayout mModifiedPswRl;
    @BindView(R.id.phone_edit)
    TextView mPhoneEdit;
    @BindView(R.id.code_edit)
    EditText mSmsCodeEdit;
    @BindView(R.id.get_code_btn)
    Button mGetCode;
    @BindView(R.id.pws)
    EditText mOriPasswd;
    @BindView(R.id.re_pws)
    EditText mRePasswd;

    private String mNoString;
    private String mPhoneString;
    private String passwd;
    private String rePasswd;
    private String mSmsCode;
    private int time = 60;
    private String employeeId;
    private String employeeGroup;
    private String employeeCode;
    private int submitstep = 1;

    private boolean isrun = false; //标识短验控件线程销毁

    @Inject
    ForgetPasswordPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initLayout() {
        initToolBar();
        DaggerForgetPasswordActivityComponent.builder()
                .appComponent(getAppComponent())
                .forgetPasswordPresenterModule(new ForgetPasswordPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    @Override
    protected void requestData() {
    }

    private void initToolBar() {
        mTitle.setText(getResources().getString(R.string.forget_pw_title));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isrun = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isrun = false;
    }

    @OnClick({R.id.left_back, R.id.next, R.id.get_code_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(submitstep == 1) {
                    nextTogetTel();
                } else if(submitstep == 2) {
                    nextToSubmitModified();
                }
                break;
            case R.id.get_code_btn:
                getSmsCode();
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

    private void getSmsCode() {
        mPhoneString = mPhoneEdit.getText().toString().trim();
        if(mPhoneString.isEmpty()) {
            toast(getResources().getString(R.string.not_input_phone_number));
            return;
        }
        if(!CheckUtils.isPhoneNumber(mPhoneString)) {
            toast(getResources().getString(R.string.not_match_phone_number));
            return;
        }
        mGetCode.setEnabled(false);
        showDialog();
        mPresenter.getSMSCode(mPhoneString);
    }

    private void nextTogetTel() {
        mNoString = mNoEdit.getText().toString().trim();
        if(mNoString.isEmpty()) {
            toast(getResources().getString(R.string.user_name_empty));
            return;
        }
        showDialog();
        mPresenter.getPhonebyStore(mNoString);
    }

    private void nextToSubmitModified() {
        mSmsCode = mSmsCodeEdit.getText().toString().trim();
        passwd = mOriPasswd.getText().toString().trim();
        rePasswd = mRePasswd.getText().toString().trim();
        if(passwd.isEmpty()) {
            toast(getResources().getString(R.string.input_new_password));
            return;
        }else if(rePasswd.isEmpty()) {
            toast(getResources().getString(R.string.input_new_password_again));
            return;
        }else if(mSmsCode.isEmpty()) {
            toast(getResources().getString(R.string.input_sms_code));
        }
        if(!passwd.equals(rePasswd)) {
            toast(getResources().getString(R.string.password_not_equal));
            return;
        }
        showDialog();
        mPresenter.forgetResetPwd(getResetRequest());
    }

    @Override
    public void setPhoneData(ForgetPwInfo forgetPwInfo) {
        mRightText.setText(getResources().getString(R.string.complete));
        mDescText.setText(getResources().getString(R.string.set_your_new_password));
        mModifiedPswRl.setVisibility(View.VISIBLE);
        submitstep = 2;
        mPhoneString = forgetPwInfo.model.getEmployeeMobile();
        employeeId = forgetPwInfo.model.getEmployeeId();
        employeeGroup = forgetPwInfo.model.getEmployeeGroup();
        employeeCode = forgetPwInfo.model.getEmployeeCode();
        mPhoneEdit.setText(mPhoneString);
    }

    @Override
    public void setSMSCodeSuccess() {
        mGetCode.post(timer);
    }

    @Override
    public void setSMSCodeError() {
        mGetCode.setEnabled(true);
    }

    @Override
    public void setResetPwdSuccess() {
        finish();
    }

    private ResetPwdRequest getResetRequest() {
        ResetPwdRequest request = new ResetPwdRequest();
        request.setMobile(mPhoneString);
        request.setCode(mSmsCode);
        request.setEmployeeId(employeeId);
        request.setEmployeeGroup(employeeGroup);
        request.setEmployeeCode(employeeCode);
        request.setNewPwd(passwd);
        request.setAgainPwd(rePasswd);
        return request;
    }

    Runnable timer = new Runnable() {
        @Override
        public void run() {
            if(isrun) {
                mGetCode.setText("剩余" + (time--) + "秒");
                if (time > 0) {
                    mGetCode.postDelayed(timer, 1000);
                    mGetCode.requestLayout();
                } else {
                    mGetCode.setEnabled(true);
                    mGetCode.setText("重新获取验证码");
                    time = 60;
                }
            }
        }
    };
}
