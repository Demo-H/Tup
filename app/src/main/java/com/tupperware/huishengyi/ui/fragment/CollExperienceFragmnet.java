package com.tupperware.huishengyi.ui.fragment;

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
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.CollExperienceAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.component.DaggerCollExperienceFragmnetComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.module.CollExperiencePresenterModule;
import com.tupperware.huishengyi.ui.contract.CollExperienceContract;
import com.tupperware.huishengyi.ui.presenter.CollExperiencePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/18.
 * 经验问答
 */

public class CollExperienceFragmnet extends BaseFragment implements CollExperienceContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullHeaderView;
    @BindView(R.id.college_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private View emptyView;
    private TextView mEmptyText;

    @Inject
    CollExperiencePresenter mPresenter;

    private CollExperienceAdapter mAdapter;
//    private int mTabPosition;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private int tagId;

    public static CollExperienceFragmnet newInstance(Bundle bundle) {
        CollExperienceFragmnet fragment = new CollExperienceFragmnet();
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
        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerCollExperienceFragmnetComponent.builder()
                .appComponent(getAppComponent())
                .collExperiencePresenterModule(new CollExperiencePresenterModule(this, CollegeDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mPullHeaderView.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollExperienceAdapter(R.layout.item_coll_experience_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    public void requestData() {
//        mPresenter.getExperienceData(tagId);
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
    public void onResume() {
        super.onResume();
        mPresenter.getExperienceData(tagId);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getExperienceData(tagId);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setExperienceData(CollegeBean mBean) {
        mAdapter.setNewData(mBean.models);
//        mAdapter.addData(mBean.content);
    }

    @Override
    public void setMoreExperienceData(CollegeBean mBean) {
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
                }
                else{
                    mPresenter.getMoreExperienceData(tagId, pageIndex);
                    pageIndex++;
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getExperienceData(tagId);
                break;
        }
    }
}
