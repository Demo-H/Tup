package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.entity.login.ModifiedPwdRequest;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerResetPasswordActivityComponent;
import com.tupperware.biz.ui.contract.ResetPasswordContract;
import com.tupperware.biz.ui.module.ResetPasswordPresenterModule;
import com.tupperware.biz.ui.presenter.ResetPasswordPresenter;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.utils.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/24.
 * 修改密码
 */

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordContract.View {

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.origin_psw_edit)
    EditText mOriPswEdit;
    @BindView(R.id.pws)
    EditText mOriPasswd;
    @BindView(R.id.re_pws)
    EditText mRePasswd;

    private String mOriPsw;
    private String passwd;
    private String rePasswd;
    private String storeEmployeeGroup;

    @Inject
    ResetPasswordPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        initToolBar();
        DaggerResetPasswordActivityComponent.builder()
                .appComponent(getAppComponent())
                .resetPasswordPresenterModule(new ResetPasswordPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        storeEmployeeGroup = mDataManager.getSPData(GlobalConfig.EMPLOYEE_GROUP);
    }

    @Override
    protected void requestData() {
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                nextToSubmitModified();
                break;
        }

    }

    private void initToolBar() {
        mTitle.setText(getResources().getString(R.string.modified_psw));
        mRightText.setText(getResources().getString(R.string.affirm));
    }

    private void nextToSubmitModified() {
        mOriPsw = mOriPswEdit.getText().toString().trim();
        passwd = mOriPasswd.getText().toString().trim();
        rePasswd = mRePasswd.getText().toString().trim();
        if(passwd.isEmpty()) {
            toast(getResources().getString(R.string.input_new_password));
            return;
        }else if(rePasswd.isEmpty()) {
            toast(getResources().getString(R.string.input_new_password_again));
            return;
        }else if(mOriPsw.isEmpty()) {
            toast(getResources().getString(R.string.input_origin_psw));
        }
        if(!passwd.equals(rePasswd)) {
            toast(getResources().getString(R.string.password_not_equal));
            return;
        }
        showDialog();
        mPresenter.modifiedPwd(getModifiedPwdReq());
    }

    @Override
    public void setModifiedPwdSuccess() {
        startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
        ActivityManager.getInstance().exit();
    }

    private ModifiedPwdRequest getModifiedPwdReq() {
        ModifiedPwdRequest request = new ModifiedPwdRequest();
        request.setOldPassword(mOriPsw);
        request.setNewPassword(passwd);
        request.setStoreEmployeeGroup(StringUtils.StringChangeToInt(storeEmployeeGroup));
        return request;
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
