package com.tupperware.biz.utils.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhunter on 2018/2/8.
 */

public class HandlerThreadFactory {
    public static final String BackgroundThread = "tupperware_Background_HandlerThread";
    public static final String RealTimeThread = "tupperware_RealTime_HandlerThread";
    private static Map<String,TupperHandlerThread> mHandlerThread = new HashMap<String,TupperHandlerThread>();
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public static TupperHandlerThread getHandlerThread(String threadName) {
        TupperHandlerThread thread = mHandlerThread.get(threadName);
        if(null == thread) {
            thread = new TupperHandlerThread(threadName, getThreadPriority(threadName));
            thread.start();
            mHandlerThread.put(threadName, thread);
        } else {
            if(!thread.isAlive()) {
                thread.start();
            }
        }
        return thread;
    }

    private static int getThreadPriority(String threadName) {
        if(BackgroundThread.equals(threadName))
            return android.os.Process.THREAD_PRIORITY_BACKGROUND;
        else if(RealTimeThread.equals(threadName))
            return android.os.Process.THREAD_PRIORITY_FOREGROUND;
        else
            return android.os.Process.THREAD_PRIORITY_DEFAULT;
    }

    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }
}
