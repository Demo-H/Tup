package com.tupperware.huishengyi.ui.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.tupperware.huishengyi.adapter.SearchMemberAdapter;
import com.tupperware.huishengyi.component.DaggerSearchMemberFragmentComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.module.SearchMemberPresenterModule;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.ui.contract.SearchMemberContract;
import com.tupperware.huishengyi.ui.presenter.SearchMemberPresenter;
import com.tupperware.huishengyi.utils.logutils.LogF;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tupperware.huishengyi.config.Constants.REQUEST_CODE_SEARCH_MEMBER_MORE;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchMemberFragment extends BaseFragment implements SearchMemberContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener,
        TupVolley.TupVolleyErrorListener, TupVolley.TupVolleyListener{

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
    private Map<String, String> headerparams;
    private SharePreferenceData mSharePreDate;
    public TupVolley mTupVolley;
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
        initheaderparams();
        initLayout();
        initLayoutData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerSearchMemberFragmentComponent.builder()
                .appComponent(getAppComponent())
                .searchMemberPresenterModule(new SearchMemberPresenterModule(this, OrderDataManager.getInstance(mDataManager)))
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
        Uri.Builder builder = Uri.parse(ServerURL.MEMBER_LIST).buildUpon();
        builder.appendQueryParameter("searchTxt", memberCode);
        builder.appendQueryParameter("initiation_end", System.currentTimeMillis() + "");
        builder.appendQueryParameter("pageNo", pageindex + "");
        builder.appendQueryParameter("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE + "");
        String url = builder.toString();
        if (pageindex == Constant.FIRST_PAGE_INDEX) {
            mTupVolley.get(Constants.REQUEST_CODE_SEARCH_MEMBER, url, this, this, headerparams);
        } else {
            mTupVolley.get(REQUEST_CODE_SEARCH_MEMBER_MORE, url, this, this, headerparams);
        }
    }

    private void initheaderparams() {
        if(headerparams == null) {
            headerparams = new HashMap<>();
        }
        mSharePreDate = new SharePreferenceData(getContext().getApplicationContext());
        String token = (String) mSharePreDate.getParam(GlobalConfig.LOGIN_TOKEN, "");
        String userId = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
        String employeeGroup = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_GROUP, "0"); //1为店长,0为店员
        headerparams.put("token", token);
        headerparams.put("userId", userId);
        headerparams.put("employeeGroup", employeeGroup);
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
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMemberAdapter.getData().size() == 0){
                    mMemberAdapter.loadMoreEnd(false);
                } else {
                    getMemberSearchData(pageIndex);
                    pageIndex++;
                }
            }
        },1000);
    }

    @Override
    public void ok(int requestCode, String json) {
        LogF.i(TAG, "json = " + json);
        hideDialog();
        MemberBean memberbean = MemberBean.createInstanceByJson(json);
        if (requestCode == Constants.REQUEST_CODE_SEARCH_MEMBER) {
            if (memberbean == null) {
                toast(getString(R.string.system_error));
                return;
            }
            if (!memberbean.isSuccess()) {
                toast(memberbean.getMessage());
                return;
            }
            if (memberbean.models == null || memberbean.models.isEmpty()) {
                setEmptyView();
            } else {
                setMemberSearchData(memberbean);
            }
        } else if (requestCode == REQUEST_CODE_SEARCH_MEMBER_MORE) {
            setMoreMemberSearchData(memberbean);
        }
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        setEmptyView();
        return false;
    }

    public void setMoreMemberSearchData(MemberBean memberBean) {
        mMemberAdapter.getData().addAll(memberBean.models);
        mMemberAdapter.loadMoreComplete();
        if (!memberBean.pageInfo.hasMore) {
            mMemberAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    public void setEditText(EditText mSearch) {
        this.mSearchEditText = mSearch;
    }

    public void setTupVolley(TupVolley mTupVolley) {
        this.mTupVolley = mTupVolley;
    }

}
