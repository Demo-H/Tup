package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.SplashPagerAdapter;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.view.GradualCircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhunter on 2018/2/2.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SplashActivity";
    private ViewPager mViewPager;
    private SplashPagerAdapter mAdapter;
    private List<View> mImageViews;
    private Button splashBtn;

    // 引导页图片资源
    private int[] images = new int[] {
            R.mipmap.ic_navigation0,
            R.mipmap.ic_navigation1,
            R.mipmap.ic_navigation2,
            R.mipmap.ic_navigation3};

//    private GradualCircleView mCircle1, mCircle2, mCircle3, mCircle4; //导航圆点
    private GradualCircleView[] mCircles;

    private boolean isScrollable;
    private boolean isFirst;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEnabledBtn(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_btn:
                setEnabledBtn(false);
                enterLoginActivity();
                break;
            default:
                break;
        }
    }

    private void setEnabledBtn(final boolean enabled) {
        SplashActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                splashBtn.setEnabled(enabled);
            }
        });

    }

    //完成引导页
    private void enterLoginActivity() {
//        SharePreferenceData mSharePreSetting = new SharePreferenceData(SharePreferenceData.BASE_APP_SETTING, this);
//        mSharePreSetting.setParam(GlobalConfig.APP_FIRST_START, false);
//        Log.i(TAG,"--isFirst = " + mSharePreDate.setParam(GlobalConfig.APP_FIRST_START, false));
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initLayout() {
        mImageViews = new ArrayList<View>();
        mViewPager = (ViewPager) findViewById(R.id.splash_view_pager);
        splashBtn = (Button) findViewById(R.id.splash_btn);
        splashBtn.setOnClickListener(this);
        mCircles = new GradualCircleView[] {(GradualCircleView) findViewById(R.id.circle_1),
                (GradualCircleView) findViewById(R.id.circle_2),
                (GradualCircleView) findViewById(R.id.circle_3),
                (GradualCircleView) findViewById(R.id.circle_4)};
//        mCircles[0] = (GradualCircleView) findViewById(R.id.circle_1);
//        mCircles[1] = (GradualCircleView) findViewById(R.id.circle_2);
//        mCircles[2] = (GradualCircleView) findViewById(R.id.circle_3);
//        mCircles[3] = (GradualCircleView) findViewById(R.id.circle_4);
//        mCircles = {
//                (GradualCircleView) findViewById(R.id.circle_1),
//                (GradualCircleView) findViewById(R.id.circle_2),
//                (GradualCircleView) findViewById(R.id.circle_3),
//                (GradualCircleView) findViewById(R.id.circle_4),
//
//        };
//        mCircle1 = (GradualCircleView) findViewById(R.id.circle_1);
//        mCircle2 = (GradualCircleView) findViewById(R.id.circle_2);
//        mCircle3 = (GradualCircleView) findViewById(R.id.circle_3);
//        mCircle4 = (GradualCircleView) findViewById(R.id.circle_4);
//        mCircle1.setAlpha(0xff);
        mCircles[0].setAlpha(0xff);
        initViewPagerContent();
    }

    @Override
    protected void requestData() {

    }

    private void initViewPagerContent() {
        try {
            for(int i = 0; i < images.length; i++) {
                View view = getLayoutInflater().inflate(R.layout.splash_view, null);
                RelativeLayout root = (RelativeLayout) view.findViewById(R.id.splash_root);
                root.setBackgroundResource(images[i]);

//                splashBtn = (ImageButton) view.findViewById(R.id.splash_btn);
//                splashBtn.setOnClickListener(this);
/*                if (i == images.length - 1) {
                    splashBtn.setVisibility(View.VISIBLE);
                    splashBtn.setOnClickListener(this);
                }*/
                mImageViews.add(view);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        mAdapter = new SplashPagerAdapter(this,mImageViews);
        mViewPager.setAdapter(mAdapter);
        if(isScrollable) {
            mViewPager.setCurrentItem(images.length-1);
        }
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                LogF.i(TAG,"   isScrollable:"+isScrollable);
                return isScrollable;
            }
        });
        if (mViewPager.getChildCount() > 0) {
            mViewPager.setCurrentItem(0);
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int arg2) {
            if(position == 3) {
                isScrollable = true;
                return;
            }
            int alpha = (int) Math.floor(positionOffset * 255);
            mCircles[position + 1].setAlpha(alpha);
            mCircles[position].setAlpha(255 - alpha);
/*            int alpha = (int) Math.floor(positionOffset * 255);
            if(position == 0) {
                mCircle1.setAlpha(255 - alpha);
                mCircle2.setAlpha(alpha);
                mCircle3.setAlpha(alpha);
            } else if(position == 1) {
                mCircle1.setAlpha(alpha);
                mCircle2.setAlpha(255 - alpha);
                mCircle3.setAlpha(alpha);
            } else if(position == 2) {
                mCircle1.setAlpha(alpha);
                mCircle2.setAlpha(alpha);
                mCircle3.setAlpha(255 - alpha);
            }*/
        }

        @Override
        public void onPageSelected(int position) {

            if (position == 3) {
                Animation alphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.window_bg_down_enter);
                splashBtn.startAnimation(alphaAnimation);
                splashBtn.setVisibility(View.VISIBLE);
            } else {
                if (splashBtn.getVisibility() == View.VISIBLE) {
                    Animation alphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.window_bg_down_exit);
                    splashBtn.startAnimation(alphaAnimation);
                    splashBtn.setVisibility(View.GONE);
                }
            }

 /*           if(position == 3) {
                mCircle1.setVisibility(View.GONE);
                mCircle2.setVisibility(View.GONE);
                mCircle3.setVisibility(View.GONE);
                mCircle4.setVisibility(View.GONE);
                Animation alphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.window_bg_down_enter);
                splashBtn.startAnimation(alphaAnimation);
                splashBtn.setVisibility(View.VISIBLE);
            } else {
                mCircle1.setVisibility(View.VISIBLE);
                mCircle2.setVisibility(View.VISIBLE);
                mCircle3.setVisibility(View.VISIBLE);
                mCircle4.setVisibility(View.VISIBLE);
                if (splashBtn.getVisibility() == View.VISIBLE) {
                    Animation alphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.window_bg_down_exit);
                    splashBtn.startAnimation(alphaAnimation);
                    splashBtn.setVisibility(View.GONE);
                }
            }*/
        }

    }

}
