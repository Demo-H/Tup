package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.RemarksRequest;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.component.DaggerRemarksActivityComponent;
import com.tupperware.huishengyi.ui.contract.RemarksContract;
import com.tupperware.huishengyi.ui.module.RemarksPresenterModule;
import com.tupperware.huishengyi.ui.presenter.RemarksPresenter;

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
    private long member_id;
    private String remarks;
    private RemarksRequest mRequestData;
    private String currentStoreId;
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
        remarks = getIntent().getStringExtra(Constant.REMARKS_CONTENT);
        if(remarks != null) {
            mRemarks.setText(remarks);
        }
        mTitle.setText(getResources().getString(R.string.gen_remarks));
//        mRightNext.setVisibility(View.GONE);
        mRightText.setText(getResources().getString(R.string.submit));
        DaggerRemarksActivityComponent.builder()
                .appComponent(getAppComponent())
                .remarksPresenterModule(new RemarksPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    @Override
    protected void requestData() {
//        employeeCode = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_CODE, "");
//        currentStoreId = (String) mSharePreDate.getParam(GlobalConfig.STORE_ID, "");
//        employeeGroup = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_GROUP, "");
        employeeCode = mDataManager.getSPData(GlobalConfig.EMPLOYEE_CODE);
        currentStoreId = mDataManager.getSPData(GlobalConfig.STORE_ID);
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
        mRequestData.setMobile(mobilePhone);
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
