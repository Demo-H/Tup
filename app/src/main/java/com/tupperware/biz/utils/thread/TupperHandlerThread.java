package com.tupperware.biz.utils.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by dhunter on 2018/2/8.
 */

public class TupperHandlerThread extends HandlerThread {

    private Handler mHandler;
    private static final String TAG = "TupperHandlerThread";

    public TupperHandlerThread(String name) {
        super(name, android.os.Process.THREAD_PRIORITY_DEFAULT);
    }

    public TupperHandlerThread(String name, int priority) {
        super(name, priority);
    }

    protected void onLooperPrepared() {
        super.onLooperPrepared();
    }

    public synchronized void post(Runnable runnable) {
        if(runnable == null) {
            Log.i(TAG,"runnable post is null");
            return;
        }
        if(mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        mHandler.post(runnable);
    }

    public synchronized void postDelay(Runnable runnable, long delay) {
        if(runnable == null){
            Log.i(TAG,"runnable post delay is null");
            return;
        }
        if (mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        mHandler.postDelayed(runnable, delay);
    }

    public synchronized Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        return mHandler;
    }
}
