package com.tupperware.biz.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.MemberListAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerSearchMemberFragmentComponent;
import com.tupperware.biz.ui.contract.SearchMemberContract;
import com.tupperware.biz.ui.module.SearchMemberPresenterModule;
import com.tupperware.biz.ui.presenter.SearchMemberPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

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
//    private SearchMemberAdapter mMemberAdapter;
    private MemberListAdapter mMemberAdapter;
    private EditText mSearchEditText;
    private int pageIndex = 2;
    private String searchKey;
    private Integer storeId;
    private ImageView mCancelEditText;

    @Inject
    SearchMemberPresenter mPresenter;
    private MemberSearchConditionDTO searchCondition = MemberSearchConditionDTO.getInstance();

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
//        requestData();
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
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mMemberAdapter = new MemberListAdapter(R.layout.item_member_list_recyclerview);
        mMemberAdapter.setOnLoadMoreListener(this);
        mMemberAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mMemberAdapter.setEnableLoadMore(true);
        mMemberAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mMemberAdapter);
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        searchCondition.reset();
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
            mSearchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() > 0) {
                        mCancelEditText.setVisibility(View.VISIBLE);
                    } else {
                        mCancelEditText.setVisibility(View.GONE);
                    }
                }
            });
        }
        mCancelEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSearchEditText != null) {
                    mSearchEditText.setText("");
                }
            }
        });
    }

    private void getMemberSearchData(int pageindex) {
        searchKey = mSearchEditText.getText().toString().trim();
        searchCondition.searchKey = searchKey;
        searchCondition.storeId = storeId;
        searchCondition.page = pageindex;
        if (pageindex == Constant.FIRST_PAGE_INDEX) {
            mPresenter.getMemberSearchData(searchCondition);
        } else {
            mPresenter.getMoreMemberSearchData(searchCondition);
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
        mMemberAdapter.setNewData(memberBean.getPageInfo().getList());
        if(!memberBean.getPageInfo().isHasNextPage()) {
            mMemberAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreMemberSearchData(MemberBean memberBean) {
        pageIndex++;
        mMemberAdapter.getData().addAll(memberBean.getPageInfo().getList());
        mMemberAdapter.loadMoreComplete();
        if(!memberBean.getPageInfo().isHasNextPage()) {
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

    public void setEditTextCancel(ImageView imageView) {
        this.mCancelEditText = imageView;
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
