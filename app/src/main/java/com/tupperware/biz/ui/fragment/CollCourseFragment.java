package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.mzBannerView.MZBannerView;
import com.android.dhunter.common.widget.mzBannerView.holder.MZHolderCreator;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CollCourseAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.component.DaggerCollCourseFragmentComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.college.CollegeBean;
import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.module.CollCoursePresenterModule;
import com.tupperware.biz.ui.contract.CollCourseContract;
import com.tupperware.biz.ui.presenter.CollCoursePresenter;
import com.tupperware.biz.view.BannerViewHolder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/17.
 * 精选课程
 */

public class CollCourseFragment extends BaseFragment implements CollCourseContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullHeaderView;
    @BindView(R.id.college_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private View emptyView;
    private TextView mEmptyText;

    @Inject
    CollCoursePresenter mPresenter;
    private View rootView;
    private View headerView;
    private MZBannerView mBanner;
    private CollCourseAdapter mAdapter;
//    private int mTabPosition;
//    private int []Ban_RES = new int[]{R.mipmap.bs_banner,R.mipmap.bs_banner};
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private int tagId;

    public static CollCourseFragment newInstance(Bundle bundle) {
        CollCourseFragment fragment = new CollCourseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tagId = bundle.getInt(Constant.LABLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        headerView = inflater.inflate(R.layout.layout_banner_view, null);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerCollCourseFragmentComponent.builder()
                .appComponent(getAppComponent())
                .collCoursePresenterModule(new CollCoursePresenterModule(this, CollegeDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mPullHeaderView.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollCourseAdapter(R.layout.item_coll_course_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        mBanner = (MZBannerView) headerView.findViewById(R.id.banner);
    }

    @Override
    public void requestData() {
        mPresenter.getCourseData(tagId);
        mPresenter.getAdvertData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_coll;
    }

    @Override
    public void setNormalView() {
        if(mPullHeaderView != null)
            mPullHeaderView.setVisibility(View.VISIBLE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        if(mPullHeaderView != null)
            mPullHeaderView.setVisibility(View.GONE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        if(mPullHeaderView != null) {
            mPullHeaderView.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }

    private void initBannerView(final CollegeBean mBean) {
//        List<Integer> list = new ArrayList<>();
//        for(int i=0; i<Ban_RES.length; i++){
//            list.add(Ban_RES[i]);
//        }
        mBanner.setIndicatorVisible(true);
        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
//                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
//                String url = mBean.models.get(position).link;
//                intent.putExtra("url",url);
//                intent.putExtra("name","今日头条");
//                view.getContext().startActivity(intent);
                toast("关联ID：" + mBean.models.get(position).refId);
            }
        });
        mBanner.setPages(mBean.models, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    public void setBannerView(CollegeBean mBean) {
        if(mAdapter.getHeaderLayoutCount() == 0) {
            initBannerView(mBean);
            addHeaderView();
        } else {
            mBanner.setRefreshDatas(mBean.models);
        }
        mBanner.start();
    }

    @Override
    public void setBannerViewHide() {

    }

    private void addHeaderView() {
//        mAdapter.removeAllHeaderView();
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(headerView);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getCourseData(tagId);
                mPresenter.getAdvertData();
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setCourseData(CollegeBean mBean) {
        mAdapter.setNewData(mBean.models);
//        mAdapter.addData(mBean.content);
    }

    @Override
    public void setMoreCourseData(CollegeBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete(); //本次数据加载结束
        if(mBean.models.size() == 0) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() == 0){
                    mAdapter.loadMoreEnd(false);
                } else{
                    mPresenter.getMoreCourseData(tagId, pageIndex);
                    pageIndex++;
                }
            }
        },1000);
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

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getCourseData(tagId);
                break;
        }
    }

}
