package com.android.dhunter.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.dhunter.common.R;
import com.android.dhunter.common.view.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dhunter on 2018/6/22.
 */

public abstract class BaseRxActivity extends RxAppCompatActivity {

    protected static final String TAG = BaseRxActivity.class.getSimpleName();
    private LoadingDialog loadingdialog;
    private Toast toast = null;
    private static BaseRxActivity mActivity;
    private CopyOnWriteArrayList<OnBackPressedListener> mBackPressedListeners;

    public static BaseRxActivity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStateBarColor();
//        setContentView(getLayoutId());
//        initLayout();
//        requestData();
    }

    public void setStateBarColor(){
        setStateBarColor(R.color.color_ff7000);
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


    protected  abstract int getLayoutId();

    protected abstract void initLayout();

    protected abstract void requestData();


    public void hideDialog() {
        if (loadingdialog != null) {
            try {
                loadingdialog.dismissAllowingStateLoss();
            } catch (Exception e) {
            }
            loadingdialog = null;
        }

    }

    public void showDialog() {
        hideDialog();
        try { //防止在Activity 还没有恢复状态就showDialog
            loadingdialog = LoadingDialog.newInstance(getResources().getString(R.string.loading));
            loadingdialog.show(getSupportFragmentManager(), null);
        } catch (Exception e) {
            loadingdialog = null;
        }
    }

    /**
     * 防止一直重复的提醒了
     */
    public void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
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
    }
}
