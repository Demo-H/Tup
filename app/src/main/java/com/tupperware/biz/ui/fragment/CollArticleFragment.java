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
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CollArticleAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.component.DaggerCollArticleFragmentComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.college.CollegeBean;
import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.module.CollArticlePresenterModule;
import com.tupperware.biz.ui.contract.CollArticleContract;
import com.tupperware.biz.ui.presenter.CollArticlePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/17.
 * 精选资讯
 */

public class CollArticleFragment extends BaseFragment implements CollArticleContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullHeaderView;
    @BindView(R.id.college_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private View emptyView;
    private TextView mEmptyText;

    @Inject
    CollArticlePresenter mPresenter;

//    private int mTabPosition;
    private CollArticleAdapter mAdapter;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private int tagId;

    public static CollArticleFragment newInstance(Bundle bundle) {
        CollArticleFragment fragment = new CollArticleFragment();
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
        View view = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, view);
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerCollArticleFragmentComponent.builder()
                .appComponent(getAppComponent())
                .collArticlePresenterModule(new CollArticlePresenterModule(this, CollegeDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mPullHeaderView.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollArticleAdapter(R.layout.item_coll_course_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView); //默认第一次加载会回调onLoadMoreRequested()加载更多这个方法，如果不需要可以配置disableLoadMoreIfNotFullPage
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN); //设置动画
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getArticleData(tagId); //tagId == 6
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article_coll;
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



    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getArticleData(tagId);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setArticleData(CollegeBean mBean) {
        mAdapter.setNewData(mBean.models);
//        mAdapter.addData(mBean.models);
    }

    @Override
    public void setMoreArticleData(CollegeBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete(); //本次数据加载结束
        if(mBean.models == null || mBean.models.size() == 0) {
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
                    mPresenter.getMoreArticleData(tagId, pageIndex);
                    pageIndex++;
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getArticleData(tagId);
                break;
        }
    }

}
