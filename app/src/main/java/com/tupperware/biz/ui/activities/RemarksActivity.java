package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.RemarksRequest;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerRemarksActivityComponent;
import com.tupperware.biz.ui.contract.RemarksContract;
import com.tupperware.biz.ui.module.RemarksPresenterModule;
import com.tupperware.biz.ui.presenter.RemarksPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/31.
 * 修改备注
 */

public class RemarksActivity extends BaseActivity implements RemarksContract.View {

    private static final String TAG = "RemarksActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.remarks)
    EditText mRemarks;
    @BindView(R.id.content_length)
    TextView mRemarkSize;
    private long member_id;
    private String remarks;
    private RemarksRequest mRequestData;
    private Integer currentStoreId;
    private String employeeCode;
    private String employeeGroup;
    private String mobilePhone;

    @Inject
    RemarksPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarks;
    }

    @Override
    protected void initLayout() {
        mobilePhone = getIntent().getStringExtra(Constant.MEMBER_PHONE);
        member_id = getIntent().getLongExtra(Constant.MEMBER_ID, 0);
        remarks = getIntent().getStringExtra(Constant.REMARKS_CONTENT);
        if(remarks != null) {
            mRemarks.setText(remarks);
            mRemarkSize.setText(remarks.length() + "/140");
        }
        mTitle.setText(getResources().getString(R.string.gen_remarks));
//        mRightNext.setVisibility(View.GONE);
        mRightText.setText(getResources().getString(R.string.submit));
        DaggerRemarksActivityComponent.builder()
                .appComponent(getAppComponent())
                .remarksPresenterModule(new RemarksPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = mRemarks.getText().toString();
                int size = content.length();
                mRemarkSize.setText(size + "/140");
                if(size > 140) {
                    mRemarks.setText(s.delete(140, s.length()).toString());
                    mRemarks.setSelection(140);
                    toast("已达到字数上限");
                }
            }
        });
    }

    @Override
    protected void requestData() {
//        employeeCode = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_CODE, "");
//        currentStoreId = (String) mSharePreDate.getParam(GlobalConfig.STORE_ID, "");
//        employeeGroup = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_GROUP, "");
        employeeCode = mDataManager.getSPData(GlobalConfig.EMPLOYEE_CODE);
        currentStoreId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        employeeGroup = mDataManager.getSPData(GlobalConfig.EMPLOYEE_GROUP);
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                showDialog();
                mPresenter.submitRemarks(getRequestData());
                break;
        }
    }

    @Override
    public void setremarksSuccess() {
        Intent intent = new Intent();
        intent.putExtra(Constant.REMARKS_CONTENT, remarks);
        setResult(Constant.REQUEST_REMARKS_CONTENT_RESULT, intent);
        finish();
    }

    private RemarksRequest getRequestData() {
        mRequestData = new RemarksRequest();
        remarks = mRemarks.getText().toString().trim();
        mRequestData.setEmployeeCode(employeeCode);
        mRequestData.setEmployeeGroup(employeeGroup);
        mRequestData.setMemberId(member_id + "");
        mRequestData.setRemark(remarks);
        mRequestData.setStoreId(currentStoreId);
        return mRequestData;
    }

//    @Override
//    public void onBackPressed() {
//        remarks = mRemarks.getText().toString().trim();
//        Intent intent = new Intent();
//        intent.putExtra(Constant.REMARKS_CONTENT, remarks);
//        setResult(Constant.REQUEST_REMARKS_CONTENT_RESULT, intent);
//        finish();
//    }
}
