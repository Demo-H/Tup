package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.videoplayer.CustomJZVideoPlayerStandard;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CourseDetialAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.component.DaggerCourseDetialActivityComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.college.CourseBean;
import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.listener.ICourseVideoListener;
import com.tupperware.biz.ui.module.CourseDetialPresenterModule;
import com.tupperware.biz.ui.contract.CourseDetialContract;
import com.tupperware.biz.ui.presenter.CourseDetialPresenter;
import com.tupperware.biz.utils.BitmapUtils;
import com.tupperware.biz.utils.RxAsyncHelper;
import com.tupperware.biz.utils.TabLayoutUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import rx.functions.Func1;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CourseDetialActivity extends BaseActivity implements CourseDetialContract.View, ICourseVideoListener {

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightIcon;

    @BindView(R.id.content_video)
    CustomJZVideoPlayerStandard jcVideo;
    @BindView(R.id.course_status_tab)
    TabLayout mStatusTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Inject
    CourseDetialPresenter mPresenter;
    private long courseId;
    private CourseDetialAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detial;
    }
    @Override
    protected void initLayout() {
        initToolBar();
        initTab();
        DaggerCourseDetialActivityComponent.builder()
                .appComponent(getAppComponent())
                .courseDetialPresenterModule(new CourseDetialPresenterModule(this, CollegeDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        jcVideo.thumbImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    @Override
    protected void requestData() {
        Intent intent = getIntent();
        courseId = intent.getLongExtra(Constant.COURSE_ID, 0);
        mPresenter.getCourseDetialData(courseId);
    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.biz_share_btn);
        mTitle.setText(getResources().getString(R.string.course_detial));
    }

    private void initTab() {
        mAdapter = new CourseDetialAdapter(this, getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mStatusTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mStatusTab, 80,80);
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                toast("share");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        jcVideo.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (jcVideo.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void setCourseDetialData(CourseBean mBean) {
        if(mBean != null && mBean.model.outlineFrontList != null) {
            final String defaultUrl = mBean.model.outlineFrontList.get(0).outlineList.get(0).filePath;
            refreshVideo(defaultUrl);
            //传递数据
            mAdapter.setDatas(mBean);
        }
    }

    public void refreshVideo(final String url) {
        jcVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        RxAsyncHelper helper = new RxAsyncHelper("");
        helper.runInThread(new Func1() {
            @Override
            public Bitmap call(Object o) {
                try {
                    Bitmap bitmap = BitmapUtils.getNetVideoBitmap(url);
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).runOnMainThread(new Func1<Bitmap,String>() {
            @Override
            public String call(Bitmap bitmap) {
                if(bitmap == null)
                    return null;
                if(jcVideo!= null && jcVideo.thumbImageView!= null) {
                    if (bitmap != null) {
                        jcVideo.thumbImageView.setImageBitmap(bitmap);
                    } else {
                        jcVideo.thumbImageView.setImageResource(R.mipmap.pic_back); //默认显示
                    }
                    jcVideo.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                return null;
            }
        }).subscribe();
    }

    @Override
    public void itemClickListener(final String filename, final String url) {
        if(filename.endsWith(".mp4")) {
            jcVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            RxAsyncHelper helper = new RxAsyncHelper("");
            helper.runInThread(new Func1() {
                @Override
                public Bitmap call(Object o) {
                    Bitmap bitmap = BitmapUtils.getNetVideoBitmap(url);
                    return bitmap;
                }
            }).runOnMainThread(new Func1<Bitmap, String>() {
                @Override
                public String call(Bitmap bitmap) {
                    if (bitmap != null) {
                        jcVideo.thumbImageView.setImageBitmap(bitmap);
                    } else {
                        jcVideo.thumbImageView.setImageResource(R.mipmap.pic_back); //默认显示
                    }
                    jcVideo.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    jcVideo.startVideo();
                    return null;
                }
            }).subscribe();
        } else {
            jcVideo.thumbImageView.setImageResource(R.mipmap.pic_back);
            jcVideo.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}