package com.tupperware.huishengyi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.dhunter.common.utils.ScreenUtil;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.HomeMultipleRecycleAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.home.HomeIndexBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.activities.LoginActivity;
import com.tupperware.huishengyi.ui.activities.MessageActivity;
import com.tupperware.huishengyi.ui.activities.PersonalSettingActivity;
import com.tupperware.huishengyi.ui.activities.ScanCouponActivity;
import com.tupperware.huishengyi.ui.activities.SearchActivity;
import com.tupperware.huishengyi.ui.component.DaggerHomePageFragmentComponent;
import com.tupperware.huishengyi.ui.contract.HomePageContract;
import com.tupperware.huishengyi.ui.module.HomePagePresenterModule;
import com.tupperware.huishengyi.ui.presenter.HomePagePresenter;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.view.SpaceItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by dhunter on 2018/2/2.
 */

public class HomePageFragment extends BaseFragment implements HomePageContract.View {

    private static final String TAG = "HomePageFragment";

//    @BindView(R.id.scan_btn)
//    ImageView scanningImage;
    @BindView(R.id.personal)
    ImageView mPersonal;
    @BindView(R.id.msg_btn)
    ImageView msgNotiImage;
    @BindView(R.id.search)
    LinearLayout mSearchLayout;
    @BindView(R.id.msg_red_tip)
    ImageView msgRedTip;

    @BindView(R.id.home_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

//    private PullHeaderView mPtrFrame;
    private HomeMultipleRecycleAdapter adapter;

    private View rootView = null;
    private int DEFAULT_REFRESH_RED_TIP_TIME = 1000 * 30; //30s刷新一次小红点
    private int isShow;

    @Inject
    HomePagePresenter mPresenter;
//    private TupperHandlerThread thread;


    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        requestData();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShow = (int) mDataManager.getSpObjectData(Constant.MSG_RED_TIP, 0);
//        thread = getHandlerThread(HandlerThreadFactory.BackgroundThread);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initLayout() {
        setShowMsgRedTip(isShow);
        DaggerHomePageFragmentComponent.builder()
                .appComponent(getAppComponent())
                .homePagePresenterModule(new HomePagePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.dip2px(getContext(),3)));
        adapter = new HomeMultipleRecycleAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isShow == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isShow == 0) {
                        try {
                            mPresenter.getMsgRedTip();
                            Thread.sleep(DEFAULT_REFRESH_RED_TIP_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
//            thread.post(new Runnable() {
//                @Override
//                public void run() {
//                    while (isShow == 0) {
//                        try {
//                            mPresenter.getMsgRedTip();
//                            Thread.sleep(DEFAULT_REFRESH_RED_TIP_TIME);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
        }
        Log.i(TAG,"onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        if(thread != null && !thread.interrupted()) {
//            thread.interrupt();
//        }
        Log.i(TAG,"onStop" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if(thread != null) {
//            if(!thread.interrupted()) {
//                thread.interrupt();
//            }
//            thread = null;
//        }
        Log.i(TAG,"onDestroyView" );
    }



    @Override
    public void setHomePageData(HomeIndexBean homeBean) {
        if(homeBean == null){
            return;
        }
//        adapter.addData(homeBean);
        adapter.getData().clear();
        adapter.resetMaxHasLoadPosition();
        adapter.setNewData(homeBean.itemInfoList);
        adapter.setEnableLoadMore(false);
    }

    @Override
    public void setNormalView() {
        recyclerView.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        recyclerView.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestData() {
        mPresenter.getHomePageData();
    }

    @OnClick({ R.id.personal, R.id.msg_btn, R.id.search,  R.id.error_layout})
    public void onclick(View view) {
        switch (view.getId()) {
//            case R.id.scan_btn:
//                try{
//                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        // 申请权限
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
//                        return;
//                    }
//                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
//                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    toast("请打开使用摄像头权限");
//                }
//
//                break;
            case R.id.personal:
                Intent personIntent = new Intent(getActivity(), PersonalSettingActivity.class); //需求变更，个人中心需要减至个人设置页面
                startActivity(personIntent);
                ActivityManager.getInstance().addActivity(getActivity());
                break;
            case R.id.msg_btn:
                Intent msgIntent = new Intent(getActivity(), MessageActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.HOME);
                startActivity(searchIntent);
                break;
            case R.id.error_layout:
                mPresenter.getHomePageData();
                break;
        }
    }

    @Override
    public void setShowMsgRedTip(int unread) {
//        mSharePreDate.setParam(Constant.MSG_RED_TIP, unread);
        mDataManager.saveSPObjectData(Constant.MSG_RED_TIP, unread);
        if(msgRedTip != null) {
            if(unread == 1) {
                isShow = 1;
                msgRedTip.setVisibility(View.VISIBLE);
            } else {
                isShow = 0;
                msgRedTip.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 初始化下拉刷新
     */
//    private void initPtrFrame() {
//        mPtrFrame = (PullHeaderView) rootView.findViewById(R.id.find_pull_refresh_header);
//        mPtrFrame.setEnabled(false);
////        mPtrFrame.setOnRefreshDistanceListener(this);
//        mPtrFrame.setPtrHandler(new PtrHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                toast("onRefreshBegin");
//            }
//        });
//    }


    @Override
    public void showToast(String string) {
        toast(string);
    }

    @Override
    public void reLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
                    startActivity(intent);
                } else {
                    // 被禁止授权
                    toast("请至权限中心打开本应用的相机访问权限");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null) {
            mPresenter.clearDisposable();
        }
        super.onDestroy();
    }
}
