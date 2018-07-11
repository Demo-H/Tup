package com.tupperware.huishengyi.base;

import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.GlobalAppComponent;
import com.android.dhunter.common.base.BaseRxFragment;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.entity.college.CourseBean;

import java.util.ArrayList;

import butterknife.Unbinder;


/**
 * Created by dhunter on 2018/2/2.
 */

public abstract class BaseFragment extends BaseRxFragment {

    protected View parent;
    private boolean isAnimationStarting = false;//却换fragment的时候，是否还在播放视频，如果是true 不能却换Fragment
    private ArrayList<AnimatorListenerAdapter> animatorListenerAdapters;

//    public Tupperware mTupperware; //每一个Fragment 都有访问网络的能力

    protected Context mContext;
    protected Unbinder unbinder;
    protected DataManager mDataManager;
    protected String storeCode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getAppComponent().getContext();
        mDataManager = getAppComponent().getDataManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTupperware = new Tupperware(getActivity());
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addOnBackPressedListener(new BaseActivity.OnBackPressedListener() {
                @Override
                public boolean onBackPressed() {
                    return onBackKeyClick();
                }
            });
        }
        storeCode = mDataManager.getSPData(GlobalConfig.STORE_CODE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        mTupperware.cancel();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

    /**
     * 提供给课程详情异步刷新UI
     * @param courseBean
     */
    public void refreshUI(CourseBean courseBean) {

    }

    /**
     * 接受Activity 转发下来的back key
     *
     * @return true 事件被处理
     */
    public boolean onBackKeyClick() {
        return false;
    }

}
