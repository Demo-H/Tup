package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dhunter.common.utils.SharePreferenceData;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.ResponseHeader;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.widget.CustomDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/15.
 */

public class RegisterChooseActivity extends BaseActivity implements
        TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        ActivityManager.getInstance().addActivity(this);
        mSharePreDate = new SharePreferenceData(this);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.register_new_user));
        mRightNext.setVisibility(View.GONE);
    }

    @Override
    protected void initLayoutData() {

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
                useCoupon();
                ActivityManager.getInstance().exit();
//                Toast.makeText(view.getContext(), "finish!!--", Toast.LENGTH_SHORT).show();

            }
        });
        customDialog.build().show();
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

        ActivityManager.getInstance().removeActivity(this);
        finish();

    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        toast(errorCode.getMessage() + "");
        return false;
    }

    private void useCoupon() {
        //联网确认使用
        asyncLoading(Constants.REQUEST_CODE_USE_COUPON);
        showDialog();
    }

    /**
     * 异步加载数据数据,完成请求的url与筛选条件的添加，把请求添加到请求队列,使用优惠券
     */
    private void asyncLoading(int requestCode) {

        Map<String, String> params = new HashMap<>();
        params.put("qrCode", Constants.SCAN_COUPON_ONLY_CODE);
        params.put("uniqueCode", Constants.SCAN_PRODUCT_ONLY_CODE);
        mTupVolley.post(requestCode, ServerURL.USE_COUPONS, params, this, this, headerparams);
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

}
