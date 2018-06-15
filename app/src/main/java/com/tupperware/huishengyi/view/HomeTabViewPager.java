package com.tupperware.huishengyi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.utils.logutils.LogF;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/2/1.
 */


public class HomeTabViewPager extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "HomeTabViewPager";

    private View mView;
    private NoScrollViewPager mViewPager;
    private TextView tvHome;
    private TextView tvVip;
    private TextView tvOrder;
    private TextView tvData;
    private TextView tvMarket;
    private ArrayList<TextView> mTags;
    private int mPosition;
    private View viewLine;
    private View viewMeRedDot;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public HomeTabViewPager(Context context) {
        super(context, null);
    }

    public HomeTabViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressWarnings("ResourceType")
    private void init() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_home_tab_view_pager, this, true);
        mViewPager = (NoScrollViewPager) mView.findViewById(R.id.viewPager);
        viewLine = (View) mView.findViewById(R.id.viewLine);
        mTags = new ArrayList<TextView>();

        {
            tvHome = (TextView) mView.findViewById(R.id.tvHome);
            tvHome.setId(Constants.PAGE_HOME);
            tvHome.setOnClickListener(this);
            mTags.add(tvHome);
        }

        {
            tvVip = (TextView) mView.findViewById(R.id.tvVip);
            tvVip.setId(Constants.PAGE_VIP);
            tvVip.setOnClickListener(this);
            mTags.add(tvVip);
        }

        {
            tvOrder = (TextView) mView.findViewById(R.id.tvOrder);
            tvOrder.setId(Constants.PAGE_ORDER);
            tvOrder.setOnClickListener(this);
            mTags.add(tvOrder);
        }

        {
            tvData = (TextView) mView.findViewById(R.id.tvData);
            tvData.setId(Constants.PAGE_DATA);
            tvData.setOnClickListener(this);
            mTags.add(tvData);
        }

        {
            tvMarket = (TextView) mView.findViewById(R.id.tvMarket);
            tvMarket.setId(Constants.PAGE_MARKET);
            tvMarket.setOnClickListener(this);
            mTags.add(tvMarket);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                LogF.d(TAG, "page: " + position);
                setSelector(position);
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

        //初始显示第一个tab的内容
        setSelector(0);
    }

    public void setSelector(int index) {
        if (index >= 0) {
            //更改viewpager显示
            mViewPager.setCurrentItem(index, false);
            //更改tag状态
            for (int i = 0; i < mTags.size(); i++) {
                TextView tag = mTags.get(i);
                if (index == i) {
                    tag.setSelected(true);
                } else {
                    tag.setSelected(false);
                }
            }
            mPosition = index;
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
        setSelector(i);
    }

    public int getSelector() {
        return mPosition;
    }

    public NoScrollViewPager getViewPager() {
        return mViewPager;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

}
