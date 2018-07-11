package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyProduct;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.entity.saleenter.MemUpgradeRequest;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.component.DaggerRegisterActivityComponent;
import com.tupperware.huishengyi.ui.contract.RegisterContract;
import com.tupperware.huishengyi.ui.module.RegisterPresenterModule;
import com.tupperware.huishengyi.ui.presenter.RegisterPresenter;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.utils.CheckUtils;
import com.tupperware.huishengyi.widget.CustomDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/16.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.nick_name)
    EditText mCustomerNick;
    @BindView(R.id.textInputCustomerNumber)
    EditText mCustomerNumber;
    @BindView(R.id.textInputSmsCode)
    EditText mSmsCode;
    @BindView(R.id.get_sms_code)
    TextView mGetSmsCode;
    @BindView(R.id.nick_name_layout)
    RelativeLayout mNickNamerl;
    @BindView(R.id.textInputSmsCode_layout)
    RelativeLayout mSmsCoderl;

    private String mCustomerNickString;
    private String mCustomerNumberString;
    private String mSmsCodeString;

//    private String sms_token = "1c7a33333893743db69a197fea0bd9ba";
//    private String bind_type = "huishengyi";
    private boolean isrun = false; //标识短验控件线程销毁
    private int time = 60;
    private String fromTag;
    private DevMemberSelect mDevMember;
    @Inject
    RegisterPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        DaggerRegisterActivityComponent.builder()
                .appComponent(getAppComponent())
                .registerPresenterModule(new RegisterPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        if(Constant.REGISTER_CHOOSE.equals(fromTag)) {
            mTitle.setText(getResources().getString(R.string.register_new_user));
            mRightText.setText(getResources().getString(R.string.submit));
        } else if(Constant.HOME.equals(fromTag) || Constant.KEY_SALE_PROJECT.equals(fromTag) ) {
            mTitle.setText(getResources().getString(R.string.develop_new_member));
            mRightText.setText(getResources().getString(R.string.next));
            mCustomerNumber.setHint(getResources().getString(R.string.input_tel_num));
            mGetSmsCode.setVisibility(View.GONE);
            mNickNamerl.setVisibility(View.GONE);
            mSmsCoderl.setVisibility(View.GONE);
            mDevMember = new DevMemberSelect();
        }
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.get_sms_code, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ActivityManager.getInstance().removeActivity(this);
                finish();
                break;
            case R.id.get_sms_code:
                mCustomerNumberString = mCustomerNumber.getText().toString();
                if (mCustomerNumberString.isEmpty()) {
                    toast("电话号码未填写");
                    return;
                } else if(!checkPhoneNumber()) {
                    toast("请输入正确的手机号码");
                    return;
                } else {
                    mGetSmsCode.setEnabled(false);
                    showDialog();
                    mPresenter.getSMSCode(mCustomerNumberString);
//                    getSMSCode();
                }
                break;
            case R.id.next:
                if(Constant.REGISTER_CHOOSE.equals(fromTag)) {
                    mCustomerNumberString = mCustomerNumber.getText().toString();
                    mSmsCodeString = mSmsCode.getText().toString();
                    mCustomerNickString = mCustomerNick.getText().toString();
                    if(mCustomerNickString.isEmpty()) {
                        toast("顾客昵称未填写");
                        return;
                    } else if (mCustomerNumberString.isEmpty()) {
                        toast("电话号码未填写");
                        return;
                    } else if(mSmsCodeString.isEmpty()) {
                        toast("请输入验证码");
                        return;
                    } else {
                        mRightText.setEnabled(false);
                        showDialog();
                        startRegister();
                    }
                } else if(Constant.HOME.equals(fromTag) || Constant.KEY_SALE_PROJECT.equals(fromTag)) {
                    mCustomerNumberString = mCustomerNumber.getText().toString();
                    if (mCustomerNumberString == null || mCustomerNumberString.isEmpty()) {
                        toast("电话号码未填写");
                    } else if(!checkPhoneNumber()) {
                        toast("请输入正确的手机号码");
                    } else {
                        if(mDevMember == null) {
                            mDevMember = new DevMemberSelect();
                        }
                        mDevMember.setMobile(mCustomerNumberString);
                        Intent intent = new Intent(this, DevCookSelectActivity.class);
                        // bundle
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    public void setSmsCodeResult() {
        mGetSmsCode.post(timer);
    }

    @Override
    public void setSMSCodeError() {
        mRightText.setEnabled(true);
        mGetSmsCode.setEnabled(true);
    }

    @Override
    public void setRegisterResult() {
        showResultDialog();
    }

    @Override
    public void setRegisterError() {
        mRightText.setEnabled(true);
    }

    private boolean checkPhoneNumber() {
        boolean result = false;
        result = CheckUtils.isPhoneNumber(mCustomerNumberString);
        return result;
    }


    private void startRegister() {
        int member_id = 0;
        String json = mDataManager.getSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON);
        VerifyProduct verifyProduct = VerifyProduct.createInstanceByJson(json);
        if (verifyProduct != null) {
            member_id = verifyProduct.model.getMembers().getMember_id();
        }
        MemUpgradeRequest request = new MemUpgradeRequest();
        request.setQrCode(Constants.SCAN_COUPON_ONLY_CODE);
        request.setUniqueCode(Constants.SCAN_PRODUCT_ONLY_CODE);
        request.setCode(mSmsCodeString);
        request.setNickname(mCustomerNickString);
        request.setMobile(mCustomerNumberString);
        if(member_id == 0 || member_id +"" == null) {
            request.setMemberId(Constants.FANS_MEMBER_ID);
        } else {
            request.setMemberId(member_id + "");
        }
        mPresenter.startRegister(request);
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


    Runnable timer = new Runnable() {
        @Override
        public void run() {
            if(isrun) {
                mGetSmsCode.setText("剩余" + (time--) + "秒");
                if (time > 0) {
                    mGetSmsCode.postDelayed(timer, 1000);
                    mGetSmsCode.requestLayout();
                } else {
                    mGetSmsCode.setEnabled(true);
                    mGetSmsCode.setText("重新获取验证码");
                    time = 60;
                }
            }
        }
    };

    private void showResultDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setmessage(mCustomerNickString);
        customDialog.setmessage2("+86" + mCustomerNumberString);
        customDialog.setmessage3(getResources().getString(R.string.register_success));
        customDialog.setsureText(getResources().getString(R.string.know));
        customDialog.setCancelable(false);
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("注册完成，返回主页面");
                ActivityManager.getInstance().exit();
//                Toast.makeText(view.getContext(), "success!!--", Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.build().show();
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

}
