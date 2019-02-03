package com.tupperware.biz.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.easypermissions.EasyPermissions;
import com.android.dhunter.common.network.SharePreferenceHelper;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Created by dhunter on 2018/2/2.
 */

public class WelcomeActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks {

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
    private String[] permission_perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS};

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
        if(!EasyPermissions.hasPermissions(getApplicationContext(), permission_perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.get_permission),
                    Constant.LOCATION_PERMISSION_REQUESTCODE, permission_perms);
        } else {
            startToNext();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//        toast("onPermissionsGranted:" + requestCode + ":" + perms.size());
        if(EasyPermissions.hasPermissions(getApplicationContext(), permission_perms)) {
            startToNext();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        toast("onPermissionsDenied: Please granted permission");
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//        if (requestCode == Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //执行自己的业务逻辑
//                startToNext();
//            } else {
//                Toast.makeText(this, "拒绝了权限", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startToNext() {
        token = mSharePreDate.getValue(GlobalConfig.LOGIN_TOKEN);
//        LogF.i(TAG,"bingle--token" + token);
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
