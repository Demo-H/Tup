package com.tupperware.huishengyi.utils.logutils;

import android.content.Context;

import com.tencent.mars.xlog.Xlog;

/**
 * Created by dhunter on 2018/3/5.
 */

public class XLogHelper {
    public static void initXlog(Context context){
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
        //      MetYouActivityManager.getInstance().registerActivityLifecycleCallback(lifecycleCallback);
        openXlog(context);
        com.tencent.mars.xlog.Log.setLogImp(new Xlog());
//        LogF.e("xlogtest","initXlog*************");
//        LogF.d("xlogtest","initXlog debug*************");
    }



    private static void openXlog(Context context){
//        final String cachePath = FileUtil.DIR_PUBLLIC_ROOT+ "/xlog/cache/";
//        final String logPath = FileUtil.DIR_PUBLLIC_ROOT + "/xlog/log/";
//        String processName = SystemUtil.getProcessName(context);
//        //  if (BuildConfig.DEBUG) {
//        if(SystemUtil.IsDebugVersion){
//            Xlog.open(false,Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "AndFetion"+processName, "");
//            Xlog.setConsoleLogOpen(false);
////            LogF.w("xlogtest","buildconfig is debug");
//
//        } else {
//            Xlog.open(false,Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, "AndFetion"+processName, "");
//            Xlog.setConsoleLogOpen(false);
////            LogF.w("xlogtest","buildconfig is release");
//        }
    }
    public static void flush(){
        com.tencent.mars.xlog.Log.appenderFlush(true);
    }
}
