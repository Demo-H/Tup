package com.tupperware.biz.ui.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.TopMiddlePopup;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.MemberListAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.product.ProductType;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerMemberSearchActivityComponent;
import com.tupperware.biz.ui.contract.MemberListContract;
import com.tupperware.biz.ui.module.MemberListPresenterModule;
import com.tupperware.biz.ui.presenter.MemberListPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/10/26.
 */

public class MemberSearchActivity extends BaseActivity implements MemberListContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.left_back)
    ImageView mLeftBack;
    @BindView(R.id.search_choose_title_ll)
    LinearLayout mChoosell;
    @BindView(R.id.search_choose_title)
    TextView mChooseTite;
    @BindView(R.id.search)
    EditText mSearchEdit;
    @BindView(R.id.search_cancel)
    TextView mCancelView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.members_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @BindView(R.id.cancel_input_text)
    ImageView mCancelInputText;

    private View emptyView;
    private int pageIndex = 2;
    private MemberListAdapter mAdapter;
    private TextView mEmptyText;
    private String searchKey;
    private Integer storeId;
    private TopMiddlePopup mTitleMiddlePopup;
    private MemberSearchConditionDTO searchCondition = MemberSearchConditionDTO.getInstance();

    @Inject
    MemberListPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_search;
    }

    @Override
    protected void initLayout() {
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        DaggerMemberSearchActivityComponent.builder()
                .appComponent(getAppComponent())
                .memberListPresenterModule(new MemberListPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new MemberListAdapter(R.layout.item_member_list_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) v.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            MemberSearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    showDialog();
                    getMemberSearchData(Constant.FIRST_PAGE_INDEX);
                    return true;
                }
                return false;
            }
        });
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    mCancelInputText.setVisibility(View.VISIBLE);
                } else {
                    mCancelInputText.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        MemberSearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        mCancelInputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdit.setText("");
                mSearchEdit.setHint("请输入" + mChooseTite.getText() + "进行搜索");
            }
        });
    }

    @Override
    protected void requestData() {
        searchCondition.searchType = 1; //默认
    }

    private void getMemberSearchData(int pageindex) {
        searchKey = mSearchEdit.getText().toString().trim();
        searchCondition.searchKey = searchKey;
        searchCondition.storeId = storeId;
        if (pageindex == Constant.FIRST_PAGE_INDEX) {
            searchCondition.page = Constant.FIRST_PAGE_INDEX;
            mPresenter.getMemberListData(searchCondition);
        } else {
            searchCondition.page = pageIndex;
            mPresenter.getMoreMemberListData(searchCondition);
        }
    }

    @OnClick({R.id.left_back, R.id.search, R.id.search_choose_title_ll, R.id.search_cancel, R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mSearchEdit, 0);
                break;
            case R.id.search_cancel:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        MemberSearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
//                onBackPressed();
                break;
            case R.id.error_layout:
                requestData();
                break;
            case R.id.search_choose_title_ll:
                /**
                 * 弹出弹框的宽度适配
                 */
                mTitleMiddlePopup = new TopMiddlePopup(view.getContext(), view.getLayoutParams().width, 0,  new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
                        mSearchEdit.setHint("请输入" + mChooseTite.getText() + "进行搜索");
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
        items.add(getResources().getString(R.string.search_type_order));
        items.add(getResources().getString(R.string.search_type_unique));
        items.add(getResources().getString(R.string.search_type_tel_number));
        items.add(getResources().getString(R.string.search_type_name));
        return items;
    }

    private void selection(int position) {
        switch (position) {
            case 0:
                searchCondition.searchType = 1;
                mChooseTite.setText(getResources().getString(R.string.search_type_order));
                break;
            case 1:
                searchCondition.searchType = 2;
                mChooseTite.setText(getResources().getString(R.string.search_type_unique));
                break;
            case 2:
                searchCondition.searchType = 3;
                mChooseTite.setText(getResources().getString(R.string.search_type_tel_number));
                break;
            case 3:
                searchCondition.searchType = 4;
                mChooseTite.setText(getResources().getString(R.string.search_type_name));
                break;
        }
    }

    @Override
    public void setMemberFilterProductType(ProductType bean) {

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
    public void setMemberListData(MemberBean memberBean) {
        pageIndex = 2;
        mAdapter.setNewData(memberBean.getPageInfo().getList());
        if(!memberBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreMemberListData(MemberBean memberBean) {
        pageIndex++;
        mAdapter.getData().addAll(memberBean.getPageInfo().getList());
        mAdapter.loadMoreComplete();
        if(!memberBean.getPageInfo().isHasNextPage()) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
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
        mAdapter.setNewData(null);
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() == 0){
                    mAdapter.loadMoreEnd(false);
                } else {
                    getMemberSearchData(pageIndex);
                }
            }
        },1000);
    }

}