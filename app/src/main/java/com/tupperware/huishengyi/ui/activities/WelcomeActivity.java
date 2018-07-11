package com.tupperware.huishengyi.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.SharePreferenceHelper;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.utils.logutils.LogF;

/**
 * Created by dhunter on 2018/2/2.
 */

public class WelcomeActivity extends RxAppCompatActivity {

    private static final String TAG = "WelcomeActivity";
//    private final CountDownLatch countDownLatch = new CountDownLatch(1);
//    private ExecutorService threadPool = Executors.newFixedThreadPool(2);
//    private Timer mTimer = new Timer();

//    private boolean isTimer = false;//是否到了时间了
//    private boolean isNetComplete = false;

    private boolean isLogin = false;//是否登录了
    private boolean isUseFirst ;//是否第一次使用
    private Toast toast = null;
//    private SharePreferenceData mSharePreDate;
//    private SharePreferenceData mSharePreSetting;
    private SharePreferenceHelper mSharePreDate;

    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mSharePreDate = new SharePreferenceHelper(getApplicationContext());
//        mSharePreDate = new SharePreferenceData(this);
//        mSharePreSetting = new SharePreferenceData(SharePreferenceData.BASE_APP_SETTING, this);
//        mContext = this;
//        startPreviousTask();
        setPermission();
    }

    // Android 获取相关权限
    private void setPermission() {
        try{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);
                return;
            } else {
                startToNext();
            }
        } catch (Exception e) {
            toast("请打开APP所需所有权限,否则部分功能会出现无法使用的现象");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //执行自己的业务逻辑
                startToNext();
            } else {
                Toast.makeText(this, "拒绝了权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startToNext() {
        token = mSharePreDate.getValue(GlobalConfig.LOGIN_TOKEN);
        LogF.i(TAG,"bingle--token" + token);
        if (token != null && !token.isEmpty()) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            startToLoginActivity();
        }
    }

    //    private void startPreviousTask() {
//
//        /** 测试代码 **/
//        if(Constant.Demo) {
//            token =(String) mSharePreDate.getParam(GlobalConfig.LOGIN_TOKEN, "");
//            if(token != null && !token.isEmpty()) {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            } else {
//                startToLoginActivity();
//            }
//            return;
//        }
//
//        //检测是否第一次使用
//        isUseFirst = (boolean) mSharePreSetting.getParam(GlobalConfig.APP_FIRST_START, true);
//        if(isUseFirst) {
//            startToSplashActivity();
//        } else {
//            //是否登录
//
//            token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
//            if(token != null && !token.isEmpty()) {
//                isLogin = true;
//                HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //最少也需要等待这么多时间
//                        mTimer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                Log.d(TAG, "run()");
//                                isTimer = true;
//                                countDownLatch.countDown();
//                            }
//                        }, 2000);
//                        getWindow().getDecorView().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                ok();
//                            }
//                        });
//                    }
//                });
//            } else {
//                isLogin = false;
//                startToLoginActivity();
//            }
//
//        }
//    }

    private void startToSplashActivity() {
        startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
        finish();
    }

    private void startToLoginActivity() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }


//    private synchronized void ok() {
//        mTimer.purge();
//        Log.d(TAG,"synchronized ok()"  + Thread.currentThread().getName() + isTimer + isNetComplete);
//        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    public void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
