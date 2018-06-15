package com.android.dhunter.common.volley.toolbox.imagetool;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.dhunter.common.volley.toolbox.ImageLoader;


/**
 * Created by dhunter on 2018/3/1.
 * @Description:  Basic LRU Memory cache
 */

public class BitmapLruImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    private final String TAG = this.getClass().getSimpleName();

    public BitmapLruImageCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        Log.v(TAG, "Retrieved item from Mem Cache");
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        Log.v(TAG, "Added item to Mem Cache");
        put(url, bitmap);
    }
}