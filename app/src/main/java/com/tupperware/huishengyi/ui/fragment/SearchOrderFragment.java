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

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.SearchOrderAdapter;
import com.tupperware.huishengyi.component.DaggerSearchOrderFragmentComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.module.SearchOrderPresenterModule;
import com.tupperware.huishengyi.ui.contract.SearchOrderContract;
import com.tupperware.huishengyi.ui.presenter.SearchOrderPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchOrderFragment extends BaseFragment implements SearchOrderContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener{

    private static final String TAG = "SearchOrderFragment";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private SearchOrderAdapter mOrderAdapter;
    private View rootView;
    private String orderCode; //搜索的订单编号
    private View emptyView;
    private TextView mEmptyText;
    private EditText mSearchEditText;
    private String code; //门店编码
    private SharePreferenceData mSharePreDate;
    @Inject
    SearchOrderPresenter mPresenter;

    public static SearchOrderFragment newInstance() {
        SearchOrderFragment fragment = new SearchOrderFragment();
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
        initLayoutData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerSearchOrderFragmentComponent.builder()
                .appComponent(getAppComponent())
                .searchOrderPresenterModule(new SearchOrderPresenterModule(this, OrderDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mOrderAdapter = new SearchOrderAdapter(R.layout.item_search_order_result_recycleview);
        mOrderAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mOrderAdapter.setOnLoadMoreListener(this);
        mOrderAdapter.setEnableLoadMore(true);
        mOrderAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mOrderAdapter);

        if(Constant.DemoTest) {
            code = "200001";
        } else {
            mSharePreDate = new SharePreferenceData(getContext().getApplicationContext());
            code = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
        }

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
                        orderCode = mSearchEditText.getText().toString();
                        mPresenter.getOrderSearchData(code, orderCode);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void initLayoutData() {

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
//                orderCode = mOrderSearchEdit.getText().toString();
//                mPresenter.getOrderSearchData(code, orderCode);
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
        mOrderAdapter.setEmptyView(emptyView);
    }

    @Override
    public void setOrderSearchData(OrderBean searchData) {
        mOrderAdapter.setNewData(searchData.models);
    }


    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mOrderAdapter.getData().size() == 0){
                    mOrderAdapter.loadMoreEnd(false);
                }
                else{

                }
            }
        },1000);
    }

    public void setEditText(EditText mSearch) {
        this.mSearchEditText = mSearch;
    }

}
