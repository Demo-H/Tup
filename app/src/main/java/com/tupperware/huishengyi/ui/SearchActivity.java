package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.TopMiddlePopup;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.fragment.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.SearchMemberFragment;
import com.tupperware.huishengyi.ui.fragment.SearchOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/16.
 */

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    @BindView(R.id.home_search_ll)
    LinearLayout mHomeSearchTitle;
    @BindView(R.id.search)
    EditText mSearchEdit;
    @BindView(R.id.search_cancel)
    TextView mCancelView;
    @BindView(R.id.search_choose_title_ll)
    LinearLayout mChoosell;
    @BindView(R.id.search_choose_title)
    TextView mChooseTite;

    @BindView(R.id.order_search_ll)
    LinearLayout mOrderSearchTitle;
    @BindView(R.id.order_search)
    EditText mOrderSearchEdit;
    @BindView(R.id.order_search_cancel)
    TextView mOrderCancelView;

//    @BindView(R.id.recyclerview)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.find_pull_refresh_header)
//    PullHeaderView mRefreshHeader;
//    @BindView(R.id.error_layout)
//    RelativeLayout mErrorLayout;

//    private SearchOrderAdapter mOrderAdapter;
//    private SearchMemberAdapter mMemberAdapter;
//    private Context context;
//    private Cursor cursor;
    private TopMiddlePopup mTitleMiddlePopup;
    private String activity_from;

    private SearchOrderFragment mOrderFragment;
    private SearchMemberFragment mMemberFragment;
    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;
//    private String orderCode; //搜索的订单编号
//    private String memberCode; //搜索的会员编号
//    private View emptyView;
//    private TextView mEmptyText;
//    private int searchType = 0; // 0 表示订单搜索，1 表示会员搜索
//    private int pageIndex = 2; //从第二页开始

//    @Inject
//    SearchPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        fragmentManager = getSupportFragmentManager();
        activity_from = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
//        if(Constant.DemoTest) {
//            code = "200001";
//        } else {
//            code = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
//        }
        if(savedInstanceState != null) {
            mOrderFragment = (SearchOrderFragment) fragmentManager.findFragmentByTag(SearchOrderFragment.class.getName());
            mMemberFragment = (SearchMemberFragment) fragmentManager.findFragmentByTag(SearchMemberFragment.class.getName());
            fragmentManager.beginTransaction().show(mOrderFragment).hide(mMemberFragment);
            //把当前显示的fragment记录下来
            currentFragment = mOrderFragment;
        } else {
            mOrderFragment = mOrderFragment.newInstance();
            mMemberFragment = mMemberFragment.newInstance();
            if(Constant.LOVE_VIP_FRAGMENT.equals(activity_from)) {
                mSearchEdit.setHint(R.string.search_vip_tip);
                mChooseTite.setText(getResources().getString(R.string.vip));
                showFragment(mMemberFragment);
            } else {
                showFragment(mOrderFragment);
            }
        }
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {}

    @Override
    protected void initLayout() {
        if(Constant.ORDER_FRAGMENT.equals(activity_from)) {
            mHomeSearchTitle.setVisibility(View.GONE);
            mOrderSearchTitle.setVisibility(View.VISIBLE);
            mOrderFragment.setEditText(mOrderSearchEdit);
        } else {
            mHomeSearchTitle.setVisibility(View.VISIBLE);
            mOrderSearchTitle.setVisibility(View.GONE);
            mOrderFragment.setEditText(mSearchEdit);
            mMemberFragment.setEditText(mSearchEdit);
            mMemberFragment.setTupVolley(mTupVolley);
        }


//        DaggerSearchActivityComponent.builder()
//                .appComponent(getAppComponent())
//                .searchPresenterModule(new SearchPresenterModule(this, OrderDataManager.getInstance(mDataManager)))
//                .build()
//                .inject(this);
//        mRefreshHeader.setPtrHandler(this);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        if(Constant.ORDER_FRAGMENT.equals(activity_from)) {
//            mHomeSearchTitle.setVisibility(View.GONE);
//            mOrderSearchTitle.setVisibility(View.VISIBLE);
//            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
//            mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
//            mOrderAdapter = new SearchOrderAdapter(R.layout.item_search_order_result_recycleview);
//            mOrderAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//            mOrderAdapter.setOnLoadMoreListener(this);
//            mOrderAdapter.setEnableLoadMore(true);
//            mOrderAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//            mRecyclerView.setAdapter(mOrderAdapter);
//
//            mOrderSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        // 当按了搜索之后关闭软键盘
//                        ((InputMethodManager) v.getContext().getSystemService(
//                                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                                SearchActivity.this.getCurrentFocus().getWindowToken(),
//                                InputMethodManager.HIDE_NOT_ALWAYS);
//                        orderCode = mOrderSearchEdit.getText().toString();
//                        mPresenter.getOrderSearchData(code, orderCode);
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        } else {
//            mHomeSearchTitle.setVisibility(View.VISIBLE);
//            mOrderSearchTitle.setVisibility(View.GONE);
//            checkinitAdapter(searchType);
//            mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        // 当按了搜索之后关闭软键盘
//                        ((InputMethodManager) v.getContext().getSystemService(
//                                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                                SearchActivity.this.getCurrentFocus().getWindowToken(),
//                                InputMethodManager.HIDE_NOT_ALWAYS);
//                        startSearchbyType(searchType);
////                        searchAndShowResult(mSearchEdit.getText().toString());
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
//
//
//        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
//        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
//        mEmptyText.setText(getResources().getString(R.string.no_search_result));
    }

    @Override
    protected void initLayoutData() {

    }

//    private void checkinitAdapter(int searchType) {
//        mRecyclerView.removeAllViews();
//        if(searchType == 0) {
//            if(mOrderAdapter == null) {
//                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
//                mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
//                mOrderAdapter = new SearchOrderAdapter(R.layout.item_search_order_result_recycleview);
//                mOrderAdapter.setOnLoadMoreListener(this);
//                mOrderAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//                mOrderAdapter.setEnableLoadMore(true);
//                mOrderAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//                mRecyclerView.setAdapter(mOrderAdapter);
//            }
//            if(mMemberAdapter != null) {
//                mMemberAdapter.notifyDataSetChanged();
//            }
//        } else {
//            if(mMemberAdapter == null) {
//                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
//                mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
//                mMemberAdapter = new SearchMemberAdapter(R.layout.item_search_member_result_recycleview);
//                mMemberAdapter.setOnLoadMoreListener(this);
//                mMemberAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//                mMemberAdapter.setEnableLoadMore(true);
//                mMemberAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//                mRecyclerView.setAdapter(mMemberAdapter);
//            }
//            if(mOrderAdapter != null) {
//                mOrderAdapter.notifyDataSetChanged();
//            }
//        }
//    }

//    private void startSearchbyType(int type) {
//        if(type == 0) {
//            orderCode = mSearchEdit.getText().toString();
//            showDialog();
//            mPresenter.getOrderSearchData(code, orderCode);
//        } else {
//            showDialog();
//            getMemberSearchData(Constant.FIRST_PAGE_INDEX);
////            mPresenter.getMemberSearchData(memberCode);
//        }
//    }

//    private void getMemberSearchData(int pageindex) {
//
//        memberCode = mSearchEdit.getText().toString();
//        Uri.Builder builder = Uri.parse(ServerURL.MEMBER_LIST).buildUpon();
//        builder.appendQueryParameter("searchTxt", memberCode);
//        builder.appendQueryParameter("initiation_end", System.currentTimeMillis() + "");
//        builder.appendQueryParameter("pageNo", pageindex + "");
//        builder.appendQueryParameter("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE + "");
//        String url = builder.toString();
//        if(pageindex == Constant.FIRST_PAGE_INDEX) {
//            mTupVolley.get(Constants.REQUEST_CODE_SEARCH_MEMBER, url, this, this, headerparams);
//        } else {
//            mTupVolley.get(REQUEST_CODE_SEARCH_MEMBER_MORE, url, this, this, headerparams);
//        }
//    }

//    @Override
//    public void onRefreshBegin(final PtrFrameLayout frame) {
//        frame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                orderCode = mOrderSearchEdit.getText().toString();
//                mPresenter.getOrderSearchData(code, orderCode);
//                frame.refreshComplete();
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void setNormalView() {
//        if(mRefreshHeader != null) {
//            mRefreshHeader.setVisibility(View.VISIBLE);
//        }
//        if(mErrorLayout != null) {
//            mErrorLayout.setVisibility(View.GONE);
//        }
//    }

//    @Override
//    public void setNetErrorView() {
//        if(mRefreshHeader != null) {
//            mRefreshHeader.setVisibility(View.GONE);
//        }
//        if(mErrorLayout != null) {
//            mErrorLayout.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void setEmptyView() {
//        if(mRefreshHeader != null) {
//            mRefreshHeader.setVisibility(View.VISIBLE);
//        }
//        if(mErrorLayout != null) {
//            mErrorLayout.setVisibility(View.GONE);
//        }
//        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        if(searchType == 0) {
//            mOrderAdapter.setEmptyView(emptyView);
//        } else {
//            mMemberAdapter.setEmptyView(emptyView);
//        }
//    }

    @OnClick({R.id.search, R.id.order_search, R.id.search_cancel, R.id.search_choose_title_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mSearchEdit, 0);
                break;
            case R.id.order_search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mOrderSearchEdit, 0);
                break;
            case R.id.search_cancel:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        SearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
//                onBackPressed();
                break;
            case R.id.search_choose_title_ll:
                mTitleMiddlePopup = new TopMiddlePopup(view.getContext(),  new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
//                        checkinitAdapter(position);
                        mTitleMiddlePopup.dismiss();
                    }
                }, getItemsName(), Config.POPTYPE_SEARCH);
                mTitleMiddlePopup.show(mChoosell);
                break;
        }
    }


    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.order));
        items.add(getResources().getString(R.string.vip));
        return items;
    }

    private void selection(int position) {
        switch (position) {
            case 0:
                mSearchEdit.setHint(R.string.search_order_tip);
                mChooseTite.setText(getResources().getString(R.string.order));
                showFragment(mOrderFragment);
//                searchType = 0;
                break;
            case 1:
                mSearchEdit.setHint(R.string.search_vip_tip);
                mChooseTite.setText(getResources().getString(R.string.vip));
                showFragment(mMemberFragment);
//                searchType = 1;
//                pageIndex = 2;
                break;
        }
    }

//    @Override
//    public void ok(int requestCode, String json) {
//        LogF.i(TAG,"json = " + json);
//        hideDialog();
//        MemberBean memberbean = MemberBean.createInstanceByJson(json);
//        if(requestCode == Constants.REQUEST_CODE_SEARCH_MEMBER) {
//            if (memberbean == null) {
//                toast(getString(R.string.system_error));
//                return;
//            }
//            if(!memberbean.isSuccess()){
//                toast(memberbean.getMessage());
//                return;
//            }
//            if(memberbean.models == null || memberbean.models.isEmpty()) {
//                setEmptyView();
//            } else {
//                setMemberSearchData(memberbean);
//            }
//        } else if(requestCode == Constants.REQUEST_CODE_SEARCH_MEMBER_MORE) {
//            setMoreMemberSearchData(memberbean);
//        }
//    }
//
//    @Override
//    public boolean error(int requestCode, ResponseBean errorCode) {
//        hideDialog();
//        setEmptyView();
//        return false;
//    }
//
//    @Override
//    public void setOrderSearchData(OrderBean searchData) {
//        mOrderAdapter.setNewData(searchData.models);
//    }
//
//    @Override
//    public void setMemberSearchData(MemberBean memberBean) {
//        mMemberAdapter.setNewData(memberBean.models);
//    }
//
//    public void setMoreMemberSearchData(MemberBean memberBean) {
//        mMemberAdapter.getData().addAll(memberBean.models);
//        mMemberAdapter.loadMoreComplete();
//        if(!memberBean.pageInfo.hasMore) {
//            mMemberAdapter.loadMoreEnd(false); //所有数据加载结束
//        }
//    }
//
//
//    @Override
//    public void onLoadMoreRequested() {
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mMemberAdapter.getData().size() == 0){
//                    mMemberAdapter.loadMoreEnd(false);
//                }
//                else{
//                    getMemberSearchData(pageIndex);
//                    pageIndex++;
//                }
//            }
//        },1000);
//    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(BaseFragment fg){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .add(R.id.searchresultFrame, fg, fg.getClass().getName());  //第三个参数为添加当前的fragment时绑定一个tag，即绑定fragment的类名
            } else {
                transaction.add(R.id.searchresultFrame, fg, fg.getClass().getName());
            }
        }else{
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .show(fg);
            } else {
                transaction.show(fg);
            }
        }
        currentFragment = fg;
        transaction.commit();
    }
}
