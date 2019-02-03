package com.tupperware.biz.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dhunter on 2018/2/9.
 */

public class SplashPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> viewData;

    public SplashPagerAdapter(Context mContext, List<View> views) {
        super();
        this.mContext = mContext;
        this.viewData = views;
    }

    @Override
    public int getCount() {
        if (viewData != null) {
            return viewData.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewData.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewData.get(position), 0);
        return viewData.get(position);
    }
}
