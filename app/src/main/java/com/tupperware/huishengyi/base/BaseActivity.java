package com.tupperware.huishengyi.base;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.GlobalAppComponent;
import com.android.dhunter.common.base.BaseRxActivity;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.ui.activities.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by dhunter on 2018/2/1.
 */

public abstract class BaseActivity extends BaseRxActivity {

    private static final String TAG = "BaseActivity";

//    public Tupperware mTupperware = new Tupperware(this);
//    public TupVolley mTupVolley = new TupVolley(this);
    protected Unbinder mUnbinder;
    protected DataManager mDataManager;
    protected Context mContext;
    protected String token;
    protected String userId;
    protected String storeCode;
    protected String employeeGroup;
    protected Map<String, String> headerparams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        mDataManager = getAppComponent().getDataManager();
        token = mDataManager.getSPData(GlobalConfig.LOGIN_TOKEN);
        userId = mDataManager.getSPData(GlobalConfig.KEY_DATA_USERID);
        storeCode = mDataManager.getSPData(GlobalConfig.STORE_CODE);
        employeeGroup = mDataManager.getSPData(GlobalConfig.EMPLOYEE_GROUP); //1为店长,0为店员
        if(headerparams == null) {
            headerparams = new HashMap<>();
        }
        headerparams.put("token", token);
        headerparams.put("userId", userId);
        headerparams.put("employeeGroup", employeeGroup);
        initLayout();
        requestData();
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mTupperware.cancel();
//        mTupVolley.cancel();
        if(mUnbinder != null)
            mUnbinder.unbind();
    }


    public void notLogin() {
        toast(getResources().getString(R.string.account_expiration));
        mDataManager.deleteSPData();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return super.isDestroyed();
        }
        return isFinishing();
    }


    public String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }


    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

}
