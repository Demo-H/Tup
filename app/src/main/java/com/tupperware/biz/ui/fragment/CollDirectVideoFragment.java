package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.mzBannerView.MZBannerView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.android.dhunter.common.widget.tablayout.SegmentTabLayout;
import com.android.dhunter.common.widget.tablayout.listener.OnTabSelectListener;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CollDirectVideoAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.college.CollegeBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.component.DaggerCollDirectVideoFragmentComponent;
import com.tupperware.biz.ui.module.CollDirectVideoPresenterModule;
import com.tupperware.biz.ui.contract.CollDirectVideoContract;
import com.tupperware.biz.ui.presenter.CollDirectVideoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/18.
 * 直播课堂
 */

public class CollDirectVideoFragment  extends BaseFragment implements CollDirectVideoContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.college_filter_tab)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullHeaderView;
    @BindView(R.id.college_recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    CollDirectVideoPresenter mPresenter;
    private CollDirectVideoAdapter mAdapter;
    private int mTabPosition;
    private int []Ban_RES = new int[]{R.mipmap.bs_banner,R.mipmap.bs_banner};

    public static CollDirectVideoFragment newInstance(Bundle bundle) {
        CollDirectVideoFragment fragment = new CollDirectVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        initBannerView();
        initFliterLayout();
        DaggerCollDirectVideoFragmentComponent.builder()
                .appComponent(getAppComponent())
                .collDirectVideoPresenterModule(new CollDirectVideoPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mPullHeaderView.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollDirectVideoAdapter(R.layout.item_coll_direct_video_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getDirectVideoData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_coll;  //复用
    }

    private void initBannerView() {
//        List<Integer> list = new ArrayList<>();
//        for(int i=0; i<Ban_RES.length; i++){
//            list.add(Ban_RES[i]);
//        }
//        mBanner.setIndicatorVisible(true);
//        mBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//            @Override
//            public BannerViewHolder createViewHolder() {
//                return new BannerViewHolder();
//            }
//        });
    }

    private void initFliterLayout() {
        mTabLayout.setTabData(getResources().getStringArray(R.array.college_fliter));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mTabLayout.setCurrentTab(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();
    }


    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setDirectVideoData(CollegeBean mBean) {
        mAdapter.addData(mBean.models);
    }

    @Override
    public void setMoreDirectVideoData(CollegeBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= 4){
                    mAdapter.loadMoreEnd(false);
                }
                else{
                    mPresenter.getMoreDirectVideoData();
                }
            }
        },1000);
    }
}
