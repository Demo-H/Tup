package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.PurFollowDetialAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.component.DaggerPurFollowDetialFragmentComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.module.PurFollowDetialPresenterModule;
import com.tupperware.huishengyi.ui.contract.PurFollowDetialContract;
import com.tupperware.huishengyi.ui.presenter.PurFollowDetialPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/30.
 * 意向客户跟进
 */

public class PurFollowDetialFragment extends BaseFragment implements PurFollowDetialContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "PurFollowDetialFragment";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView pendingPullRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    PurFollowDetialPresenter mPresenter;

    private PurFollowDetialAdapter mAdapter;
    private View emptyView;
    private int mTabPosition;
    private String code; //门店编码
    private String status; //订单状态
    private int pageIndex = 2;  //分页加载更多，从第二页开始
//    private SharePreferenceData mSharePreDate;
    private String tagCodes;
//    private ArrayList<PurFollowDetialBean> list = new ArrayList<>();


    public static PurFollowDetialFragment newInstance(Bundle bundle) {
        PurFollowDetialFragment fragment = new PurFollowDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            if(mTabPosition == 1) {
                tagCodes = Constant.TAG_CODE_TUP;
            } else if(mTabPosition == 2) {
                tagCodes = Constant.TAG_CODE_TAKE_IN;
            } else if(mTabPosition == 3) {
                tagCodes = Constant.TAG_CODE_KITCHNEN;
            } else {
                tagCodes = Constant.TAG_CODE_WATER_CLEANER;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        DaggerPurFollowDetialFragmentComponent.builder()
                .appComponent(getAppComponent())
                .purFollowDetialPresenterModule(new PurFollowDetialPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        pendingPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        mAdapter = new PurFollowDetialAdapter(R.layout.item_purpose_follow_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getPurFollowDetialData(tagCodes);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycle_common; //复用
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getPurFollowDetialData(tagCodes);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        pendingPullRefreshHeader.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        pendingPullRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }


    @Override
    public void setPurFollowDetialData(PurFollowDetialBean purFollowDetialBean) {
        mAdapter.addData(purFollowDetialBean.getPageInfo().getList());
        if(!purFollowDetialBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMorePurFollowDetialData(PurFollowDetialBean purFollowDetialBean) {
        mAdapter.getData().addAll(purFollowDetialBean.getPageInfo().getList());
        mAdapter.loadMoreComplete();
        if(!purFollowDetialBean.getPageInfo().isHasNextPage()) {
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
                    mPresenter.getMorePurFollowDetialData(tagCodes, pageIndex);
                    pageIndex++;
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getPurFollowDetialData(tagCodes);
                break;
        }
    }
}
