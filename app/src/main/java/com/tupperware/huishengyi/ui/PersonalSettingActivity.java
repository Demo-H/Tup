package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/19.
 * 修改密码
 */

public class PersonalSettingActivity extends BaseActivity implements TupVolley.TupVolleyErrorListener, TupVolley.TupVolleyListener{

    private static final String TAG = "PersonalInfoActivity";

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

    @BindView(R.id.schedule)
    RelativeLayout mSchedule;
    @BindView(R.id.modified_psw)
    RelativeLayout mModifiedPsw;
    @BindView(R.id.user_feedback)
    RelativeLayout mUserFeedback;
    @BindView(R.id.function_infomation)
    RelativeLayout mFunctionInfo;
    @BindView(R.id.device_manager)
    RelativeLayout mDeviceManager;
    @BindView(R.id.about_us)
    RelativeLayout mAboutUs;
    @BindView(R.id.login_exit)
    TextView mLoginExit;
    @BindView(R.id.version)
    TextView mVersionText;

    private Context mContext;
    private String appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        //将相关activity暂时保存，退出登录finish
        ActivityManager.getInstance().addActivity(this);
        mContext = this;
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
        appVersion = AppUtils.getVersionName();
        mVersionText.setText("V" + appVersion);
    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
//        mTitle.setText(getResources().getString(R.string.setting));
        mTitle.setText(getResources().getString(R.string.personal_info));
    }


    @OnClick({R.id.left_back, R.id.schedule, R.id.modified_psw, R.id.login_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.schedule:
                Intent actIntent = new Intent(PersonalSettingActivity.this, ActionListActivity.class);
                actIntent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.PERSONAL_SETTING);
                startActivity(actIntent);
                break;
            case R.id.modified_psw:
                Intent intent = new Intent(PersonalSettingActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
//            case R.id.user_feedback:
//                break;
//            case R.id.function_infomation:
//                break;
//            case R.id.device_manager:
//                break;
//            case R.id.about_us:
//                break;
            case R.id.login_exit:
                logout();
                break;
        }
    }

    @Override
    public void ok(int requestCode, String json) {
        hideDialog();
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGOUT:
                toast(getResources().getString(R.string.login_exit));
                jumptoActivity(this, LoginActivity.class);
                ActivityManager.getInstance().exit();
                break;
        }
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        jumptoActivity(this, LoginActivity.class);
        ActivityManager.getInstance().exit();
        return false;
    }

    private void jumptoActivity(Context context, Class<?> _cls ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        context.startActivity(intent);
    }

    private void logout() {
        showDialog();
        Map<String, String> params = new HashMap<>();
        mTupVolley.post(Constants.REQUEST_CODE_LOGOUT, ServerURL.LOGOUT, params, this, this, headerparams);
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        ActivityManager.getInstance().removeActivity(this);
        /**
         * 为了退出登录，将首页也加入ActivityManager的list，所以在该页面返回首页的时候，需要将list清空，防止内存泄漏。
         */
        ActivityManager.getInstance().removeAll();
        finish();
    }

}
