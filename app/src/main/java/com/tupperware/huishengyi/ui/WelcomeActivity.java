package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.android.dhunter.common.volley.DefaultRetryPolicy;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.Tupperware;
import com.tupperware.huishengyi.utils.NetWorkUtils;
import com.tupperware.huishengyi.utils.thread.HandlerThreadFactory;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dhunter on 2018/2/2.
 */

public class WelcomeActivity extends RxAppCompatActivity implements Tupperware.TupperwareErrorListener, Tupperware.TupperwareListener {

    private static final String TAG = "WelcomeActivity";
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private Timer mTimer = new Timer();

    private boolean isTimer = false;//是否到了时间了
    private boolean isNetComplete = false;

    private boolean isLogin = false;//是否登录了
    private boolean isUseFirst ;//是否第一次使用
    public Tupperware mTupperware = new Tupperware(this);
    private Toast toast = null;
//    private Context mContext;
    private SharePreferenceData mSharePreDate;
    private SharePreferenceData mSharePreSetting;

    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mSharePreDate = new SharePreferenceData(this);
        mSharePreSetting = new SharePreferenceData(SharePreferenceData.BASE_APP_SETTING, this);
//        mContext = this;
        startPreviousTask();
    }

    private void startPreviousTask() {

        /** 测试代码 **/
        if(Constant.Demo) {
            startToLoginActivity();
            return;
        }

        //检测是否第一次使用
        isUseFirst = (boolean) mSharePreSetting.getParam(GlobalConfig.APP_FIRST_START, true);
        if(isUseFirst) {
            startToSplashActivity();
        } else {
            //是否登录

            token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
            if(token != null && !token.isEmpty()) {
                isLogin = true;
                HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).post(new Runnable() {
                    @Override
                    public void run() {
                        startToGetData();
                        //最少也需要等待这么多时间
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run()");
                                isTimer = true;
                                countDownLatch.countDown();
                            }
                        }, 2000);
                        getWindow().getDecorView().post(new Runnable() {
                            @Override
                            public void run() {
                                ok();
                            }
                        });
                    }
                });
            } else {
                isLogin = false;
                startToLoginActivity();
            }

        }


    }

    private void startToSplashActivity() {
        startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
        finish();
    }

    private void startToLoginActivity() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    private void startToGetData() {
        if(!NetWorkUtils.isNetworkAvailable(getApplicationContext())) {
            toast(getResources().getString(R.string.network_not_connect));
            return;
        }
        //登陆了，从网络拿数据
        Uri.Builder builder = Uri.parse(ServerURL.HOME).buildUpon();
        builder.appendQueryParameter(GlobalConfig.LOGIN_TOKEN, token);
        String url = builder.toString();
        mTupperware.setRetryPolicy(new DefaultRetryPolicy());
        mTupperware.get(Constants.REQUEST_CODE_INIT, url, WelcomeActivity.this, WelcomeActivity.this);

    }

    private synchronized void ok() {
        mTimer.purge();
        Log.d(TAG,"synchronized ok()"  + Thread.currentThread().getName() + isTimer + isNetComplete);
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void ok(int requestCode, String json) {
        mSharePreDate.setParam(Constants.KEY_DATA_HOMES, json);
        mSharePreDate.setParam(Constants.KEY_DATA_HAS_HOMES, Constants.KEY_DATA_HAS_HOMES);
        isNetComplete = true;
    }

    @Override
    public boolean error(int requestCode, JSONObject jsonObject) {
        isNetComplete = true;

        return false;
    }

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
