package com.tupperware.huishengyi.ui;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.GlobalAppComponent;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.model.DataManager;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.network.Tupperware;
import com.tupperware.huishengyi.ui.fragment.LoadingDialogFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by dhunter on 2018/2/1.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseActivity";

    public Tupperware mTupperware = new Tupperware(this);
    public TupVolley mTupVolley = new TupVolley(this);
    private LoadingDialogFragment dialogFragment;
    private CopyOnWriteArrayList<OnBackPressedListener> mBackPressedListeners;
    protected Unbinder mUnbinder;
    protected DataManager mDataManager;
    protected SharePreferenceData mSharePreDate;
    protected String token;
    protected String userId;
    protected String storeCode;
    protected String employeeGroup;
    protected Map<String, String> headerparams;
    private static BaseActivity mActivity;

//    private OnHideKeyboardListener onHideKeyboardListener;
    public static BaseActivity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDataManager = getAppComponent().getDataManager();
        mSharePreDate = new SharePreferenceData(getApplicationContext());
        token = (String) mSharePreDate.getParam(GlobalConfig.LOGIN_TOKEN, "");
        userId = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
        storeCode = (String) mSharePreDate.getParam(GlobalConfig.STORE_CODE, "");
        employeeGroup = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_GROUP, "0"); //1为店长,0为店员
        if(headerparams == null) {
            headerparams = new HashMap<>();
        }
        headerparams.put("token", token);
        headerparams.put("userId", userId);
        headerparams.put("employeeGroup", employeeGroup);
        setStateBarColor();
    }

    public void setStateBarColor(){
        setStateBarColor(R.color.statebar_ff7000);
    }

    public void setStateBarColor(@ColorRes int color){
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(color));
        }
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }


    protected abstract void findViews();

    protected abstract void initLayout();

    protected abstract void initLayoutData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTupperware.cancel();
        mTupVolley.cancel();
        if(mUnbinder != null)
            mUnbinder.unbind();
    }

    public void hideDialog() {
        if (dialogFragment != null) {
            try {
                dialogFragment.dismiss();
            } catch (Exception e) {
            }
            dialogFragment = null;
        }
    }

    public void showDialog() {
        hideDialog();
        try { //防止在Activity 还没有恢复状态就showDialog
            dialogFragment = LoadingDialogFragment.newInstance(getResources().getString(R.string.loading));
            dialogFragment.show(getSupportFragmentManager(), null);
        } catch (Exception e) {
            dialogFragment = null;
        }
    }

    public void notLogin() {
        toast(getResources().getString(R.string.account_expiration));
        SharePreferenceData spDate = new SharePreferenceData(getApplicationContext());
        spDate.removeAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private Toast toast = null;

    /**
     * 不会一直重复的提醒了
     */
    public void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
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

    /**
     * back key 监听器，处理按加入的先后顺序，如果先加入的监听器处理了事件，后加入的将无法得到处理
     */
    public interface OnBackPressedListener {
        /**
         * 当back key 按下的时候回调
         *
         * @return true 事件处理了，false，事件未处理
         */
        public boolean onBackPressed();
    }

    public void addOnBackPressedListener(OnBackPressedListener l) {
        if (mBackPressedListeners == null) {
            mBackPressedListeners = new CopyOnWriteArrayList<OnBackPressedListener>();
        }
        mBackPressedListeners.add(l);
    }

    public void removeOnBackPressedListener(OnBackPressedListener l) {
        if (mBackPressedListeners == null) {
            return;
        }
        mBackPressedListeners.remove(l);
    }

    public void clearOnBackPressedListener(OnBackPressedListener l) {
        if (mBackPressedListeners == null) {
            return;
        }
        mBackPressedListeners.clear();
    }

    /**
     * 覆盖BackKey，
     */
    @Override
    public void onBackPressed() {
        //转发back key 事件

        //处理全部的监听器
        if (mBackPressedListeners != null) {
            for (int i = mBackPressedListeners.size() - 1; i >= 0; i--) {
                OnBackPressedListener l = mBackPressedListeners.get(i);
                if (l.onBackPressed()) {
                    return;
                }
            }
        }
        super.onBackPressed();
//        Intent home = new Intent(Intent.ACTION_MAIN);
//        home.addCategory(Intent.CATEGORY_HOME);
//        startActivity(home);
    }

    public String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }


    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

}
