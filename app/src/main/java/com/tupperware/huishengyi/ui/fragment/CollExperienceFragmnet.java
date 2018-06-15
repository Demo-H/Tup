package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.CollExperienceAdapter;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.component.DaggerCollExperienceFragmnetComponent;
import com.tupperware.huishengyi.module.CollExperiencePresenterModule;
import com.tupperware.huishengyi.ui.contract.CollExperienceContract;
import com.tupperware.huishengyi.ui.presenter.CollExperiencePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/18.
 * 经验问答
 */

public class CollExperienceFragmnet extends BaseFragment implements CollExperienceContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mPullHeaderView;
    @BindView(R.id.college_recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    CollExperiencePresenter mPresenter;

    private CollExperienceAdapter mAdapter;
    private int mTabPosition;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private int tagId = 3;

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
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        initLayoutData();
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
    public void initLayoutData() {
        mPresenter.getExperienceData(tagId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article_coll;
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
}
