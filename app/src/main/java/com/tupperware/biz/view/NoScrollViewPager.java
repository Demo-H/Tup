package com.tupperware.biz.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dhunter on 2018/2/1.
 * 可设置滑动与否的ViewPager，默认可滑动
 */

public class NoScrollViewPager extends ViewPager {
    private boolean mScrollble = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mScrollble) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mScrollble) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean isCanScrollble() {
        return mScrollble;
    }

    /**
     * 设置能否滑动
     * @param scrollble
     */
    public void setCanScrollble(boolean scrollble) {
        this.mScrollble = scrollble;
    }
}
