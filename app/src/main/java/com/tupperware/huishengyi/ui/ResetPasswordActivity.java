package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.ResponseHeader;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.ActivityManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/24.
 */

public class ResetPasswordActivity extends BaseActivity implements
        TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener {

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
        initLayoutData();
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
        submitModified();
    }

    private void submitModified() {

        Map<String, String> params = new HashMap<>();
        params.put("old_password", mOriPsw);
        params.put("new_password", passwd);
        params.put("again_new_password", rePasswd);
        mTupVolley.post(Constants.REQUEST_CODE_MODIFIED_PW, ServerURL.CHANGE_PWD, params, this, this, headerparams);
    }



    @Override
    public void ok(int requestCode, String json) {
        hideDialog();
        ResponseHeader mHeader = ResponseHeader.createInstanceByJson(json);
        if (mHeader == null) {
            toast(getString(R.string.system_error));
            return;
        }
        if(!mHeader.isSuccess()){
            toast(mHeader.getMessage());
            return;
        }
        toast(mHeader.getMessage());
        if(requestCode == Constants.REQUEST_CODE_MODIFIED_PW) {
            startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
            ActivityManager.getInstance().exit();
        }
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
//        toast(errorCode.getMessage());
        return false;
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
