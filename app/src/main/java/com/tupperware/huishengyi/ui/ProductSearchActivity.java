package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.TopMiddlePopup;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ProductSearchAdapter;
import com.tupperware.huishengyi.component.DaggerProductSearchActivityComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.module.ProductSearchPresenterModule;
import com.tupperware.huishengyi.ui.contract.ProductSearchContract;
import com.tupperware.huishengyi.ui.presenter.ProductSearchPresenter;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/28.
 * 销售录入搜索
 */

public class ProductSearchActivity extends BaseActivity implements ProductSearchContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ProductSearchActivity";
    @BindView(R.id.search)
    EditText mSearchEdit;
    @BindView(R.id.search_choose_title_ll)
    LinearLayout mChoosell;
    @BindView(R.id.search_choose_title)
    TextView mChooseTite;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    private TopMiddlePopup mTitleMiddlePopup;
    private View emptyView;
    private TextView mEmptyText;
    @Inject
    ProductSearchPresenter mPresenter;
    private ProductSearchAdapter mAdapter;
    private String keywords; //搜索的输入的关键字
    private int searchType = 0;  // 0：编码，1：名称，2：条形码
    private String selectDate;
    private boolean isHistory;
    private int pageIndex = 2;  //分页加载更多，从第二页开始
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        mContext = this;
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        selectDate = getIntent().getStringExtra(Constant.SELECT_DATE);
        if(selectDate == null || selectDate.isEmpty()) {
            selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        }
        isHistory = DateFormatter.isHistory(selectDate);
        toast(selectDate);
        DaggerProductSearchActivityComponent.builder()
                .appComponent(getAppComponent())
                .productSearchPresenterModule(new ProductSearchPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new ProductSearchAdapter(R.layout.item_sale_history_recycleview, mContext, isHistory); //UI复用
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
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
                            ProductSearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    keywords = mSearchEdit.getText().toString();
                    mPresenter.getProductSearchData(storeCode, selectDate, searchType, keywords, Constant.FIRST_PAGE_INDEX);
                    return true;
                }
                return false;
            }
        });
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_search_result));

    }

    @Override
    protected void initLayoutData() {

    }


    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                keywords = mSearchEdit.getText().toString();
                mPresenter.getProductSearchData(storeCode, selectDate, searchType, keywords, Constant.FIRST_PAGE_INDEX);
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
        mAdapter.setEmptyView(emptyView);
    }

    @OnClick({R.id.left_back, R.id.search, R.id.search_choose_title_ll, R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mSearchEdit, 0);
                break;
            case R.id.search_choose_title_ll:
                mTitleMiddlePopup = new TopMiddlePopup(view.getContext(),  new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
                        mTitleMiddlePopup.dismiss();
                    }
                }, getItemsName(), Config.POPTYPE_PRODUCT_SEARCH);
                mTitleMiddlePopup.show(mChoosell);
                break;
            case R.id.error_layout:
                keywords = mSearchEdit.getText().toString();
                mPresenter.getProductSearchData(storeCode, selectDate, searchType, keywords, Constant.FIRST_PAGE_INDEX);
                break;
        }
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<>();
        items.add(getResources().getString(R.string.product_code));
        items.add(getResources().getString(R.string.product_name));
        items.add(getResources().getString(R.string.product_qr_code));
        return items;
    }

    private void selection(int position) {
        switch (position) {
            case 0:
                mSearchEdit.setHint(R.string.search_product_code_tip);
                mChooseTite.setText(getResources().getString(R.string.product_code));
                searchType = 0;
                break;
            case 1:
                mSearchEdit.setHint(R.string.search_product_name_tip);
                mChooseTite.setText(getResources().getString(R.string.product_name));
                searchType = 1;
                break;
            case 2:
                mSearchEdit.setHint(R.string.search_product_qr_code_tip);
                mChooseTite.setText(getResources().getString(R.string.product_qr_code));
                searchType = 2;
                break;
        }
    }

    @Override
    public void setProductSearchData(SaleEnterBean mBean) {
        mAdapter.setNewData(mBean.models);
    }

    @Override
    public void setMoreProductSearchData(SaleEnterBean mBean) {
        mAdapter.getData().addAll(mBean.models);
        mAdapter.loadMoreComplete();
        pageIndex++;
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
                } else {
                    mPresenter.getMoreProductSearchData(storeCode, selectDate, searchType, keywords, pageIndex);
                }
            }
        },1000);
    }
}
