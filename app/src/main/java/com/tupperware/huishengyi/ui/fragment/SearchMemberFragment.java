package com.tupperware.huishengyi.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.SearchMemberAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.component.DaggerSearchMemberFragmentComponent;
import com.tupperware.huishengyi.ui.contract.SearchMemberContract;
import com.tupperware.huishengyi.ui.module.SearchMemberPresenterModule;
import com.tupperware.huishengyi.ui.presenter.SearchMemberPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchMemberFragment extends BaseFragment implements SearchMemberContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener{

    private static final String TAG = "SearchMemberFragment";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private View rootView;
    private View emptyView;
    private TextView mEmptyText;
    private String memberCode; //搜索的会员编号
    private SearchMemberAdapter mMemberAdapter;
    private EditText mSearchEditText;
    private int pageIndex = 2;
    @Inject
    SearchMemberPresenter mPresenter;

    public static SearchMemberFragment newInstance() {
        SearchMemberFragment fragment = new SearchMemberFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_search_result));
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerSearchMemberFragmentComponent.builder()
                .appComponent(getAppComponent())
                .searchMemberPresenterModule(new SearchMemberPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mMemberAdapter = new SearchMemberAdapter(R.layout.item_search_member_result_recycleview);
        mMemberAdapter.setOnLoadMoreListener(this);
        mMemberAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mMemberAdapter.setEnableLoadMore(true);
        mMemberAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mMemberAdapter);

        if(mSearchEditText != null) {
            mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        // 当按了搜索之后关闭软键盘
                        ((InputMethodManager) v.getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                                showDialog();
                                getMemberSearchData(Constant.FIRST_PAGE_INDEX);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void getMemberSearchData(int pageindex) {
        memberCode = mSearchEditText.getText().toString();
        if (pageindex == Constant.FIRST_PAGE_INDEX) {
            mPresenter.getMemberSearchData(memberCode);
        } else {
            mPresenter.getMoreMemberSearchData(memberCode, pageindex);
        }
    }


    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycle_common;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex = 2;
                getMemberSearchData(Constant.FIRST_PAGE_INDEX);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNetErrorView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.GONE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEmptyView() {
        if(mRefreshHeader != null) {
            mRefreshHeader.setVisibility(View.VISIBLE);
        }
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mMemberAdapter.setNewData(null);
        mMemberAdapter.setEmptyView(emptyView);
    }

    @Override
    public void setMemberSearchData(MemberBean memberBean) {
        mMemberAdapter.setNewData(memberBean.models);
        if(!memberBean.pageInfo.hasMore) {
            mMemberAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreMemberSearchData(MemberBean memberBean) {
        pageIndex++;
        mMemberAdapter.getData().addAll(memberBean.models);
        mMemberAdapter.loadMoreComplete();
        if (!memberBean.pageInfo.hasMore) {
            mMemberAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }


    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMemberAdapter.getData().size() == 0){
                    mMemberAdapter.loadMoreEnd(false);
                } else {
                    getMemberSearchData(pageIndex);
                }
            }
        },1000);
    }

    public void setEditText(EditText mSearch) {
        this.mSearchEditText = mSearch;
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                getMemberSearchData(Constant.FIRST_PAGE_INDEX);
                break;
        }
    }

}
