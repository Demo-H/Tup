package com.tupperware.huishengyi.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.GlobalAppComponent;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.model.DataManager;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.network.Tupperware;
import com.tupperware.huishengyi.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.Unbinder;


/**
 * Created by dhunter on 2018/2/2.
 */

public abstract class BaseFragment extends RxFragment {

    protected View parent;
    private boolean isAnimationStarting = false;//却换fragment的时候，是否还在播放视频，如果是true 不能却换Fragment
    private ArrayList<AnimatorListenerAdapter> animatorListenerAdapters;
    private LoadingDialogFragment dialogFragment;

    public Tupperware mTupperware; //每一个Fragment 都有访问网络的能力

    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder unbinder;
    protected DataManager mDataManager;
    protected SharePreferenceData mSharePreDate;
    protected String storeCode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = getAppComponent().getContext();
        mDataManager = getAppComponent().getDataManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTupperware = new Tupperware(getActivity());
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addOnBackPressedListener(new BaseActivity.OnBackPressedListener() {
                @Override
                public boolean onBackPressed() {
                    return onBackKeyClick();
                }
            });
        }
        mSharePreDate = new SharePreferenceData(mContext.getApplicationContext());
        storeCode = (String) mSharePreDate.getParam(GlobalConfig.STORE_CODE, "");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTupperware.cancel();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    /**
     * 初始化parent,如果parent 已经在Activity 的View 容器里面，那么就移除这个parent，让onCreateView 返回的View 再次加入view 容器
     *
     * @param inflater
     * @param container
     */
    public void init(int layoutId, LayoutInflater inflater, ViewGroup container) {
        if (parent == null) {
            parent = inflater.inflate(layoutId, container, false);
            initData();
        }
        if (parent != null) {
            ViewGroup parentContainer = (ViewGroup) parent.getParent();
            if (parentContainer != null) {
                parentContainer.removeView(parent);
            }
        }
    }

    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

    /**
     * 初始化数据，在parent 为空的时候回调
     */
    public void initData() {

    }

    /**
     * 提供给课程详情异步刷新UI
     * @param courseBean
     */
    public void refreshUI(CourseBean courseBean) {

    }

    /**
     * 初始化布局控件，拿到控件的引用，初始化ChildFragment 在ParentFragment 的布局
     */
    public abstract void initLayout();

    /**
     * 初始化控件数据，初始化完控件后第一次填充数据
     */
    public abstract void initLayoutData();

    public abstract int getLayoutId();

    public View getLayoutView(LayoutInflater inflate, ViewGroup container) {
        return null;
    }

    /**
     * 接受Activity 转发下来的back key
     *
     * @return true 事件被处理
     */
    public boolean onBackKeyClick() {
        return false;
    }

    /**
     * 添加一个动画
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (nextAnim == 0) {
            return null;
        }
        Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
        if (anim != null) {
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimationStarting = false;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimationStarting = true;
                }
            });
            if (animatorListenerAdapters != null) {
                for (AnimatorListenerAdapter animatorListenerAdapter : animatorListenerAdapters) {
                    anim.addListener(animatorListenerAdapter);
                }
            }
        }
        return anim;
    }
    /**
     * 添加动画播放监听器
     *
     * @param animatorListenerAdapter
     */
    public void addAnimationListener(AnimatorListenerAdapter animatorListenerAdapter) {
        if (animatorListenerAdapter == null) {
            throw new NullPointerException("animatorListenerAdapter == null");
        }

        if (animatorListenerAdapters == null) {
            animatorListenerAdapters = new ArrayList<AnimatorListenerAdapter>();
        }
        animatorListenerAdapters.add(animatorListenerAdapter);

    }
    /**
     * 动画是否正在播放
     *
     * @return
     */
    public boolean isAnimationStarting() {
        return isAnimationStarting;
    }

    public void hideDialog() {
        if (dialogFragment != null) {
            try {
                dialogFragment.dismissAllowingStateLoss();
            } catch (Exception e) {
            }
            dialogFragment = null;
        }

    }

    public void showDialog() {
        hideDialog();
        try { //防止在Activity 还没有恢复状态就showDialog
            dialogFragment = LoadingDialogFragment.newInstance(getActivity().getResources().getString(R.string.loading));
            dialogFragment.show(getFragmentManager(), null);
        } catch (Exception e) {
            dialogFragment = null;
        }
    }

    public void toast(String msg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).toast(msg);
        } else {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
