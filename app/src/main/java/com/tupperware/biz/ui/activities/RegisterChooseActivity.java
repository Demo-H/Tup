package com.tupperware.biz.ui.activities;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constants;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.component.DaggerRegisterChooseActivityComponent;
import com.tupperware.biz.ui.contract.RegisterChooseContract;
import com.tupperware.biz.ui.module.RegisterChoosePresenterModule;
import com.tupperware.biz.ui.presenter.RegisterChoosePresenter;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.widget.CustomDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/15.
 */

public class RegisterChooseActivity extends BaseActivity implements RegisterChooseContract.View {

    private static final String TAG = "RegisterChooseActivity";

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

    @BindView(R.id.add_register)
    Button mAddButton;
    @BindView(R.id.not_add_register)
    Button mNotNeedButton;

    @Inject
    RegisterChoosePresenter mPresenter;
    private Integer memberId;
    private static final int TYPE_UPGRADE = 1; //是否升级，1：是，0：否，默认0 ,
    private static final int TYPE_NOT_UPGRADE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_choose;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        DaggerRegisterChooseActivityComponent.builder()
                .appComponent(getAppComponent())
                .registerChoosePresenterModule(new RegisterChoosePresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mTitle.setText(getResources().getString(R.string.register_new_user));
        mRightNext.setVisibility(View.GONE);
        memberId = (Integer) mDataManager.getSpObjectData(Constants.FANS_MEMBER_ID, -1);
    }

    @Override
    protected void requestData() {

    }


    @OnClick({R.id.left_back, R.id.add_register, R.id.not_add_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ActivityManager.getInstance().removeActivity(this);
                finish();
                break;
            case R.id.add_register:
                useCoupon(TYPE_UPGRADE);
//                Intent intent = new Intent(this, RegisterActivity.class);
//                intent.putExtra(Constant.ACTIVITY_CREATE_FROM,Constant.REGISTER_CHOOSE);
//                startActivity(intent);
                break;
            case R.id.not_add_register:
                useCoupon(TYPE_NOT_UPGRADE);
//                showCustomDialog();
                break;
        }
    }

    @Override
    public void setUseCouponResult(String msg) {
        if(TextUtils.isEmpty(msg)) {
            showDialog("核销成功");
        } else {
            showDialog(msg);
        }
    }

    private void showCustomDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setmessage(getResources().getString(R.string.not_register_tip));
        customDialog.setcancelText(getResources().getString(R.string.cancel_and_exit));
        customDialog.setsureText(getResources().getString(R.string.cancel));
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "继续注册!!--", Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
//                mPresenter.useProductCoupon(Constants.SCAN_COUPON_ONLY_CODE, Constants.SCAN_PRODUCT_ONLY_CODE, memberId);
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

    private void useCoupon(int isUpdate) {
        showDialog();
        mPresenter.useProductCoupon(Constants.SCAN_COUPON_ONLY_CODE, Constants.SCAN_PRODUCT_ONLY_CODE, memberId, isUpdate);
    }

    private void showDialog(String msg) {
        CustomDialog customDialog = new CustomDialog(this);
        if(TextUtils.isEmpty(msg)) {
            customDialog.setmessage(getResources().getString(R.string.not_allow_recruit_vip));
        } else {
            customDialog.setmessage(msg);
        }
        customDialog.setCancelable(false);
        customDialog.setsureText(getResources().getString(R.string.back_home));
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.getInstance().exit();
            }
        });
        customDialog.build().show();
    }

}
