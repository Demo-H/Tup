package com.android.dhunter.common.volley;

import android.content.Context;

import com.android.dhunter.common.volley.toolbox.Volley;

import static android.net.sip.SipErrorCode.TIME_OUT;

/**
 * Created by dhunter on 2018/2/9.
 */

public class RequestManager {

    /**
     * the queue :-)
     */
    private static RequestQueue mRequestQueue;

    /**
     * Nothing to see here.
     */
    private RequestManager() {
        // no instances
    }

    /**
     * @param context
     * 			application context
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @return
     * 		instance of the queue
     * @throws
     * 	 if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    private static <T> void addRequest(RequestQueue requestQueue, Request<T> request) {
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
