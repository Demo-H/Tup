package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.component.DaggerRegisterChooseActivityComponent;
import com.tupperware.huishengyi.ui.contract.RegisterChooseContract;
import com.tupperware.huishengyi.ui.module.RegisterChoosePresenterModule;
import com.tupperware.huishengyi.ui.presenter.RegisterChoosePresenter;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.widget.CustomDialog;

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
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM,Constant.REGISTER_CHOOSE);
                startActivity(intent);
                break;
            case R.id.not_add_register:
                showCustomDialog();
                break;
        }
    }

    @Override
    public void setUseCouponResult() {
        ActivityManager.getInstance().exit();
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
                mPresenter.useProductCoupon(Constants.SCAN_COUPON_ONLY_CODE, Constants.SCAN_PRODUCT_ONLY_CODE);
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
