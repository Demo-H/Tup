package com.tupperware.huishengyi.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.login.ForgetPwInfo;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/9.
 * 忘记密码和修改密码在改UI上进行
 */

public class ForgetPasswordActivity extends BaseActivity implements
        TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener{

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
//        ActivityManager.getInstance().addActivity(this);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
    }

    @Override
    protected void initLayoutData() {
    }

    private void initToolBar() {
        mTitle.setText(getResources().getString(R.string.forget_pw_title));
    }

    private void refreshUI(ForgetPwInfo mforgetInfo) {
        mRightText.setText(getResources().getString(R.string.complete));
        mDescText.setText(getResources().getString(R.string.set_your_new_password));
        mModifiedPswRl.setVisibility(View.VISIBLE);
        submitstep = 2;
        mPhoneString = mforgetInfo.model.getEmployee_mobile();
        employeeId = mforgetInfo.model.getEmployee_id();
        employeeGroup = mforgetInfo.model.getEmployee_group();
        employeeCode = mforgetInfo.model.getEmployee_code();
        mPhoneEdit.setText(mPhoneString);
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

    Runnable timer = new Runnable() {
        @Override
        public void run() {
            mGetCode.setText( "剩余"+(time--) + "秒");
            if (time > 0) {
                mGetCode.postDelayed(timer, 1000);
                mGetCode.requestLayout();
            } else {
                mGetCode.setEnabled(true);
                mGetCode.setText("重新获取验证码");
                time = 60;
            }
        }
    };

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
        getCode();
    }

    private void getCode() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", mPhoneString);
        mTupVolley.post(Constants.REQUEST_CODE_GET_SMS_CODE, ServerURL.GET_REGISTER_SMS_CODE, params, this, this);
    }

    private void nextTogetTel() {
        mNoString = mNoEdit.getText().toString().trim();
        if(mNoString.isEmpty()) {
            toast(getResources().getString(R.string.user_name_empty));
            return;
        }
        showDialog();
        submit();

    }

    private void submit() {
        Uri.Builder builder = Uri.parse(ServerURL.GET_EMPLOYEE_MOBILE).buildUpon();
        builder.appendQueryParameter("storeEmployeeCode", mNoString);
        String url = builder.toString();
        mTupVolley.get(Constants.REQUEST_CODE_FORGET_PW,url,this,this);
    }

    @Override
    public void ok(int requestCode, String json) {
        hideDialog();
        ForgetPwInfo mforgetInfo = ForgetPwInfo.createInstanceByJson(json);
        if (mforgetInfo == null) {
            toast(getString(R.string.system_error));
            return;
        }
        if(!mforgetInfo.isSuccess()){
            toast(mforgetInfo.getMessage());
            return;
        }
        if(requestCode == Constants.REQUEST_CODE_FORGET_PW) {
            refreshUI(mforgetInfo);
        } else if(requestCode == Constants.REQUEST_CODE_GET_SMS_CODE) {
            mGetCode.post(timer);
            toast(mforgetInfo.getMessage());
        } else if(requestCode == Constants.REQUEST_CODE_MODIFIED_PW) {
            toast(mforgetInfo.getMessage());
            finish();
        }

    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        toast(errorCode.getMessage());
        if(requestCode == Constants.REQUEST_CODE_GET_SMS_CODE) {
            mGetCode.setEnabled(true);
        }
        return false;
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
        submitModified();
    }

    private void submitModified() {

        Map<String, String> params = new HashMap<>();
        params.put("phone", mPhoneString);
        params.put("code", mPhoneString);
        params.put("employeeId", employeeId);
        params.put("employeeGroup", employeeGroup);
        params.put("employeeCode", employeeCode);
        params.put("newPwd", passwd);
        params.put("againPwd", rePasswd);
        mTupVolley.post(Constants.REQUEST_CODE_MODIFIED_PW, ServerURL.FIND_PASSWORD, params, this, this);
    }

}
