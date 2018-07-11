package com.tupperware.huishengyi.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/11.
 */

public class DataShowFragment extends BaseFragment {

//    @BindView(R.id.data_show_status_tab)
//    TabLayout mTabLayout;
//    @BindView(R.id.viewpager)
//    ViewPager mViewPager;

    @BindView(R.id.rl_member)
    RelativeLayout mMemberRL;
    @BindView(R.id.member_data)
    TextView mMemberText;
    @BindView(R.id.member_line)
    View mMemberView;
    @BindView(R.id.sale_rl)
    RelativeLayout mSaleRL;
    @BindView(R.id.sale_data)
    TextView mSaleText;
    @BindView(R.id.video_line)
    View mSaleView;

    private View rootview;
    private MemberDataFragment mMemberDataFragment;
    private SaleDataFragment mSaleDataFragment;
    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;

    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};
    private boolean issavedInstanceState = false;

    public static DataShowFragment newInstance() {
        DataShowFragment fragment = new DataShowFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        requestData();
        return rootview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager = getChildFragmentManager();
        if(savedInstanceState != null) {
            issavedInstanceState = true;
            mMemberDataFragment = (MemberDataFragment) fragmentManager.findFragmentByTag(MemberDataFragment.class.getName());
            mSaleDataFragment = (SaleDataFragment) fragmentManager.findFragmentByTag(SaleDataFragment.class.getName());
            fragmentManager.beginTransaction().show(mMemberDataFragment).hide(mSaleDataFragment);
            //把当前显示的fragment记录下来
            currentFragment = mMemberDataFragment;
        } else {
            issavedInstanceState = false;

        }
    }

    @Override
    public void initLayout() {
        if(!issavedInstanceState) {
            mMemberDataFragment = MemberDataFragment.newInstance();
            mSaleDataFragment = SaleDataFragment.newInstance();
            showFragment(mMemberDataFragment);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_show;
    }

    @OnClick({R.id.rl_member, R.id.sale_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_member:
                mMemberView.setVisibility(View.VISIBLE);
                mMemberText.setSelected(true);
                showFragment(mMemberDataFragment);
                if(mSaleView != null) {
                    mSaleView.setVisibility(View.GONE);
                }
                if(mSaleText != null) {
                    mSaleText.setSelected(false);
                }
                break;
            case R.id.sale_rl:
                mSaleView.setVisibility(View.VISIBLE);
                mSaleText.setSelected(true);
                showFragment(mSaleDataFragment);
                if(mMemberView != null) {
                    mMemberView.setVisibility(View.GONE);
                }
                if(mMemberText != null) {
                    mMemberText.setSelected(false);
                }
                break;
        }
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(BaseFragment fg){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .add(R.id.data_Frame, fg, fg.getClass().getName());  //第三个参数为添加当前的fragment时绑定一个tag，即绑定fragment的类名
            } else {
                transaction.add(R.id.data_Frame, fg, fg.getClass().getName());
            }
        }else{
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .show(fg);
            } else {
                transaction.show(fg);
            }
        }
        currentFragment = fg;
        transaction.commit();
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

}
