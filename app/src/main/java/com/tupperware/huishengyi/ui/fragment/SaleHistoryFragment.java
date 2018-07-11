package com.tupperware.huishengyi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.SaleHistoryAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.activities.EnterListActivity;
import com.tupperware.huishengyi.ui.component.DaggerSaleHistoryFragmentComponent;
import com.tupperware.huishengyi.ui.contract.SaleHistoryContract;
import com.tupperware.huishengyi.ui.module.SaleHistoryPresenterModule;
import com.tupperware.huishengyi.ui.presenter.SaleHistoryPresenter;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.utils.data.ProductHistoryProvider;
import com.tupperware.huishengyi.utils.data.ProductProvider;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tupperware.huishengyi.config.Constant.REQUEST_DATE;

/**
 * Created by dhunter on 2018/5/25.
 * 销售录入历史
 */

public class SaleHistoryFragment extends BaseFragment implements SaleHistoryContract.View,
        PtrHandler/*, BaseQuickAdapter.RequestLoadMoreListener*/ {

    private static final String TAG = "SaleHistoryFragment";

    @BindView(R.id.before_day)
    ImageView mBeforeDay;
    @BindView(R.id.after_day)
    ImageView mAfterDay;
    @BindView(R.id.select_day)
    TextView mSelectDay;
    @BindView(R.id.enter_list_header)
    TextView mEnterListHeader;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @BindView(R.id.generate_list_rl)
    RelativeLayout mGenerateListrl;
    @BindView(R.id.generate_list_count)
    TextView mListCount;
    @BindView(R.id.generate_list)
    TextView mGenerateList;


    @Inject
    SaleHistoryPresenter mPresenter;
    private SaleHistoryAdapter mAdapter;
    private View rootView;
    private View emptyView;
    private TextView mEmptyText;
    private String selectDate;
    private String currentSelect;
    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private boolean isHistory;

    public static SaleHistoryFragment newInstance() {
        SaleHistoryFragment fragment = new SaleHistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_sale_list));
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        mBeforeDay.setVisibility(View.VISIBLE);
        mAfterDay.setVisibility(View.VISIBLE);
        selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        mSelectDay.setText(selectDate);
        mInstance = ProductProvider.getInstance(mContext.getApplicationContext());
        mHistoryInstance = ProductHistoryProvider.getInstance(mContext.getApplicationContext());
        DaggerSaleHistoryFragmentComponent.builder()
                .appComponent(getAppComponent())
                .saleHistoryPresenterModule(new SaleHistoryPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new SaleHistoryAdapter(R.layout.item_sale_history_recycleview, mContext);
//        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        refreshCountView();
        mAdapter.setOnChangeListener(new SaleHistoryAdapter.IHistoryChangeListener() {
            @Override
            public void onChange() {
                refreshFootListCount();
            }
        });
//        mRightImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPopup = new EasyTopPopup(mActivity, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        selection(position);
//                        mChooseString = (String) parent.getItemAtPosition(position);
//                    }
//                }, getAddItemsName(), Config.RIGHT);
//                if(mChooseString == null) {
//                    mChooseString = getResources().getString(R.string.refrigerator_clean);
//                }
//                mPopup.setCurrentSelected(mChooseString);
//                mPopup.show(view);
//            }
//        });

    }

    @Override
    public void requestData() {
        showDialog();
        mPresenter.getSaleHistoryData(storeCode, selectDate);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sale_history;
    }


    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getSaleHistoryData(storeCode, selectDate);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.VISIBLE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.GONE);
        if(mErrorLayout != null)
            mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        if(mRefreshHeader != null)
            mRefreshHeader.setVisibility(View.VISIBLE);
        if(mErrorLayout != null) {
            mErrorLayout.setVisibility(View.GONE);
        }
        mAdapter.setNewData(null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
        refreshCountView();
    }

    @Override
    public void setSaleHistoryData(SaleEnterBean mBean) {
        mAdapter.setNewData(mBean.models);
        if(mBean.models == null) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
        selectDate = mSelectDay.getText().toString().trim();
        isHistory = DateFormatter.isHistory(selectDate);
        mAdapter.setIsHistory(isHistory);
        refreshCountView();
    }
//
//    public void setRightImage(ImageView mImage) {
//        this.mRightImage = mImage;
//    }

    private void refreshCountView() {
        int count;
        if(mAdapter.getData() != null) {
            count = mAdapter.getData().size();
        } else {
            count = 0;
        }
        mEnterListHeader.setText(String.format(getResources().getString(R.string.enter_product_list), count));
    }

    public void refreshFootListCount(){
        selectDate = mSelectDay.getText().toString().trim();
        isHistory = DateFormatter.isHistory(selectDate);
        int count;
        if(isHistory) {
            count = mHistoryInstance.getListSize();
            if(count == 0) {
                mGenerateListrl.setVisibility(View.GONE);
            } else {
                mGenerateListrl.setVisibility(View.VISIBLE);
                mListCount.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
            }
        } else {
            count = mInstance.getListSize();
            if(count == 0) {
                mGenerateListrl.setVisibility(View.GONE);
            } else {
                mGenerateListrl.setVisibility(View.VISIBLE);
                mListCount.setText(Html.fromHtml("<font color=#43484b>录入产品：</font>" + "<big>" + count + "</big>" + "<font color=#9b9b9b>件</font>"));
            }
        }

    }

    @OnClick({R.id.error_layout, R.id.select_day,R.id.before_day, R.id.after_day, R.id.generate_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getSaleHistoryData(storeCode, selectDate);
                break;
            case R.id.select_day:
                chooseDate();
                break;
            case R.id.before_day:
                currentSelect = mSelectDay.getText().toString().trim();
                String before = DateFormatter.getSpecifiedDayBefore(currentSelect);
                mSelectDay.setText(before);
                mHistoryInstance.deleteAll();
                selectDate = before;
                refreshFootListCount();
                requestData();
                break;
            case R.id.after_day:
                currentSelect = mSelectDay.getText().toString().trim();
                String after = DateFormatter.getSpecifiedDayAfter(currentSelect);
                mSelectDay.setText(after);
                mHistoryInstance.deleteAll();
                selectDate = after;
                refreshFootListCount();
                requestData();
                break;
            case R.id.generate_list:
                currentSelect = mSelectDay.getText().toString().trim();
                Intent genIntent = new Intent(getActivity(), EnterListActivity.class);
                genIntent.putExtra(Constant.SELECT_DATE, currentSelect);
                startActivity(genIntent);
                break;
        }
    }

    public String getCurrentSelectDate() {
        currentSelect = mSelectDay.getText().toString().trim();
        return currentSelect;
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.setTargetFragment(SaleHistoryFragment.this, REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(Constant.EXTRA_DATE);
            currentSelect = mSelectDay.getText().toString().trim();
            selectDate = new DateFormatter().DateFormat(date);
            if(!currentSelect.equals(selectDate)) {
                mHistoryInstance.deleteAll();
                mSelectDay.setText(selectDate);
                refreshFootListCount();
                requestData();
            }
        }
    }
//    @Override
//    public void setMoreSaleHistoryData(SaleEnterBean mBean) {
//        mAdapter.getData().addAll(mBean.models);
//        mAdapter.loadMoreComplete();
//        if(mBean.models.size() == 0) {
//            mAdapter.loadMoreEnd(false); //所有数据加载结束
//        }
//    }

//    @Override
//    public void onLoadMoreRequested() {
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mAdapter.getData().size() == 0){
//                    mAdapter.loadMoreEnd(false);
//                } else{
//                    mPresenter.getMoreSaleHistoryData();
//                }
//            }
//        },1000);
//    }
}
