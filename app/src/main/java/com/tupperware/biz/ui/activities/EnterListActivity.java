package com.tupperware.biz.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.EnterListAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.component.DaggerEnterListActivityComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.saleenter.PostEnterBean;
import com.tupperware.biz.entity.saleenter.SaleEnterContent;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.module.EnterListPresenterModule;
import com.tupperware.biz.ui.contract.EnterListContract;
import com.tupperware.biz.ui.presenter.EnterListPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.data.ProductHistoryProvider;
import com.tupperware.biz.utils.data.ProductProvider;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/29.
 * 录入清单
 */

public class EnterListActivity extends BaseActivity implements EnterListContract.View{

    private static final String TAG = "EnterListActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.before_day)
    ImageView mBeforeDay;
    @BindView(R.id.after_day)
    ImageView mAfterDay;
    @BindView(R.id.select_day)
    TextView mSelectDay;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @BindView(R.id.generate_list_rl)
    RelativeLayout mEnterListrl;
    @BindView(R.id.generate_list_count)
    TextView mListCount;

    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private EnterListAdapter mAdapter;
    private List<SaleEnterContent> mDataList;
    private View emptyView;
    private TextView mEmptyText;
    @Inject
    EnterListPresenter mPresenter;
    private String selectDate;
    private boolean isHistory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter_list;
    }

    @Override
    protected void initLayout() {
        selectDate = getIntent().getStringExtra(Constant.SELECT_DATE);
        if(selectDate == null || selectDate.isEmpty()) {
            selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        }
        isHistory = DateFormatter.isHistory(selectDate);
        mTitle.setText(getResources().getString(R.string.enter_list));
        mRightNext.setVisibility(View.GONE);
        mSelectDay.setText(selectDate);
        DaggerEnterListActivityComponent.builder()
                .appComponent(getAppComponent())
                .enterListPresenterModule(new EnterListPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new EnterListAdapter(R.layout.item_sale_history_recycleview, mContext, isHistory);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_enter_list));
        mAdapter.setOnChangeListener(new EnterListAdapter.ICountChangeListener() {
            @Override
            public void onChange() {
                //数据和UI进行更新
                updateData();
            }
        });
    }

    @Override
    protected void requestData() {
        if(isHistory) {
            mHistoryInstance = ProductHistoryProvider.getInstance(getApplicationContext());
        } else {
            mInstance = ProductProvider.getInstance(getApplicationContext());
        }
        updateData();
    }

    private void updateData() {
        if(isHistory) {
            mDataList = mHistoryInstance.getAll();
        } else {
            mDataList = mInstance.getAll();
        }
        if(mDataList == null || mDataList.size() == 0) {
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(emptyView);
        } else {
            mAdapter.setNewData(mDataList);
        }
        refreshCount();
    }

    @OnClick({R.id.left_back, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.save:
                mPresenter.postSaleList(getpostData());
                break;
        }
    }

    public void refreshCount() {
        int count;
        if(isHistory) {
            count = mHistoryInstance.getListSize();
        } else {
            count = mInstance.getListSize();
        }
//        mListCount.setText(String.format(getResources().getString(R.string.enter_product_count), count));
        mListCount.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
    }

    private PostEnterBean getpostData() {
        PostEnterBean mBean = new PostEnterBean();
        mBean.setStoreCode(storeCode);
        mBean.setDate(mSelectDay.getText().toString().trim());
//        mBean.setDate(new DateFormatter().timestampToDate(System.currentTimeMillis()));

        List<PostEnterBean.EnterRecords> mRecordsList = new ArrayList<>();
        if(mDataList == null) {
            if(isHistory) {
                mDataList = mHistoryInstance.getAll();
            } else {
                mDataList = mInstance.getAll();
            }
        }
        if(mDataList != null && mDataList.size() > 0) {
            for(int i = 0; i < mDataList.size(); i++) {
                PostEnterBean.EnterRecords recordsitem = new PostEnterBean.EnterRecords();
                recordsitem.setCode(mDataList.get(i).getCode());
                recordsitem.setName(mDataList.get(i).getName());
                recordsitem.setSaleNum(mDataList.get(i).getLocalSaleNum());
                recordsitem.setStockNum(mDataList.get(i).getLocalStockNum());
                recordsitem.setUrl(mDataList.get(i).getUrl());
                mRecordsList.add(recordsitem);
            }
        }
        mBean.setRecords(mRecordsList);
        if(mRecordsList == null || mRecordsList.size() == 0) {
            return null;
        }
        return mBean;
    }

    public void postSuccess() {
        toast("success");
        if(isHistory) {
            mHistoryInstance.deleteAll();
        } else {
            mInstance.deleteAll();
        }
        updateData();
    }
}
