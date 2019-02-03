package com.tupperware.biz.base;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;

import com.android.dhunter.common.GlobalAppComponent;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.FileUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tupperware.biz.R;
import com.tupperware.biz.utils.CrashHandler;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dhunter on 2018/1/30.
 */

public class BaseApplication extends Application{

    private static BaseApplication mInstance;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    public final CrashHandler mCrashHandler = CrashHandler.getInstance();


    @Override
    public void onCreate() {
        super.onCreate();
        if(mInstance == null){
            mInstance = this;
        }
        initScreenSize();
        init();

    }

    public static BaseApplication getInstance() {
        return mInstance;
    }


    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    private void init() {
        /**
         * 初始化SDK卡目录
         */
        FileUtils.initSdcardDirs(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);     		// 初始化 JPush
        /**内存泄漏初始化**/
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        /** 注册自定义crashHandler **/
        mCrashHandler.init(this);
        /** 初始化bugly   */
        CrashReport.initCrashReport(mInstance.getApplicationContext(), GlobalConfig.BUGLY_APP_ID, false);
        // Volley 初始化
//        RequestManager.init(this);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
//        createImageCache();

        Fresco.initialize(this);
        GlobalAppComponent.init(getApplicationContext());

    }

    /**
     * 突破64K问题，MultiDex构建
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.
     */
//    private void createImageCache(){
//        ImageCacheManager.getInstance().init(this,
//                this.getPackageCodePath()
//                , DISK_IMAGECACHE_SIZE
//                , DISK_IMAGECACHE_COMPRESS_FORMAT
//                , DISK_IMAGECACHE_QUALITY
//                , ImageCacheManager.CacheType.DISK);
//    }


    /**
     * Volley and Okhttp init
     */

//    private static void addRequest(Request<?> request) {
//        getInstance().getVolleyRequestQueue().add(request);
//    }
//
//    public RequestQueue getVolleyRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(this, new OkHttp3Stack(new OkHttpClient()));
//        }
//        return mRequestQueue;
//    }
//
//    public static void addRequest(Request<?> request, String tag) {
//        request.setTag(tag);
//        addRequest(request);
//    }
//
//    public static void cancelAllRequests(String tag) {
//        if (getInstance().getVolleyRequestQueue() != null) {
//            getInstance().getVolleyRequestQueue().cancelAll(tag);
//        }
//    }
}
