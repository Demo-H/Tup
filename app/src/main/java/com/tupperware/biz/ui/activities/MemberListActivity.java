package com.tupperware.biz.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.tupperware.biz.adapter.FilterProductTypeAdapter;
import com.tupperware.biz.adapter.MemberListAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.product.ProductType;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerMemberListActivityComponent;
import com.tupperware.biz.ui.contract.MemberListContract;
import com.tupperware.biz.ui.fragment.DatePickerFragment;
import com.tupperware.biz.ui.module.MemberListPresenterModule;
import com.tupperware.biz.ui.presenter.MemberListPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.StringUtils;
import com.tupperware.biz.view.MemberSortSelectPopup;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/10/22.
 * 我的会员列表
 */

public class MemberListActivity extends BaseActivity implements MemberListContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.member_sort_title)
    TextView mSortTitle;
    @BindView(R.id.sort_arrow)
    ImageView mSortArrow;
    @BindView(R.id.scrollview)
    NestedScrollView mScrollView;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.members_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @BindView(R.id.dl_left)
    DrawerLayout mDlLeft;
    @BindView(R.id.fans)
    TextView mFans;
    @BindView(R.id.general_vip)
    TextView mGeneralVip;
    @BindView(R.id.very_vip)
    TextView mSuperVip;
    @BindView(R.id.date_select_from)
    TextView mDateSelectFrom;
    @BindView(R.id.date_select_to)
    TextView mDateSelectTo;
    @BindView(R.id.integrating_select_from)
    EditText mIntegratingFrom;
    @BindView(R.id.integrating_select_to)
    EditText mIntegratingTo;
    @BindView(R.id.request)
    TextView mRequest;
    @BindView(R.id.send_gift)
    TextView mSendGift;
    @BindView(R.id.received)
    TextView mReceived;
    @BindView(R.id.one_month)
    TextView mOneMonth;
    @BindView(R.id.three_month)
    TextView mThreeMonth;
    @BindView(R.id.six_month)
    TextView mSixMonth;
    @BindView(R.id.reset)
    TextView mReset;
    @BindView(R.id.sure)
    TextView mSure;
    @BindView(R.id.product_tyle)
    RecyclerView mProductType;


    private View emptyView;
    private int pageIndex = 2;
    private MemberListAdapter mAdapter;
    private TextView mEmptyText;
    private FilterProductTypeAdapter mFilterAdapter;
    private Integer storeId;
    private MemberSortSelectPopup mPopup;
    private MemberSearchConditionDTO searchCondition = MemberSearchConditionDTO.getInstance();

    @Inject
    MemberListPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void initLayout() {
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        DaggerMemberListActivityComponent.builder()
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
        searchCondition.reset();
        initFilterLayout();
        initFilterProductType();
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        MemberListActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
    }

    @Override
    protected void requestData() {
        showDialog();
        searchCondition.clearSearchCondition();
        searchCondition.storeId = storeId;
        searchCondition.page = Constant.FIRST_PAGE_INDEX;
        mPresenter.getMemberListData(searchCondition);
        mPresenter.getMemberFilterProductType();
    }

    @OnClick({R.id.left_back, R.id.search, R.id.member_sort_choose, R.id.member_filter_ll, R.id.error_layout,
            R.id.fans,R.id.general_vip,R.id.very_vip,R.id.date_select_from,R.id.date_select_to,
            R.id.integrating_select_from,R.id.integrating_select_to,R.id.request,R.id.send_gift,R.id.received,
            R.id.one_month,R.id.three_month,R.id.six_month,R.id.reset,R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.search:
                Intent intent = new Intent(MemberListActivity.this, MemberSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.member_sort_choose:
                mPopup = new MemberSortSelectPopup(this, new MemberSortSelectPopup.OnSortSelectedClickListener() {
                    @Override
                    public void onSortSelectedClick(int position) {
                        selection(position);
                        mPopup.dismiss();
                    }
                }, getAddItemsName(), mSortTitle.getText().toString().trim());
                mPopup.show(view);
                break;
            case R.id.member_filter_ll:
                mDlLeft.openDrawer(Gravity.RIGHT);
                break;
            case R.id.error_layout:
                requestData();
                break;
            case R.id.fans:
                selectMemberType(mFans, MemberSearchConditionDTO.MEMBER_TYPE_FANS);
                break;
            case R.id.general_vip:
                selectMemberType(mGeneralVip, MemberSearchConditionDTO.MEMBER_TYPE_VIP);
                break;
            case R.id.very_vip:
                selectMemberType(mSuperVip, MemberSearchConditionDTO.MEMBER_TYPE_ADVANCED_VIP);
                break;
            case R.id.date_select_from:
                chooseDate(mDateSelectFrom);
                break;
            case R.id.date_select_to:
                chooseDate(mDateSelectTo);
                break;
            case R.id.integrating_select_from:
                break;
            case R.id.integrating_select_to:
                break;
            case R.id.request:
                searchCondition.orderStatus = getResources().getString(R.string.request);
                updateGiftStatus();
                break;
            case R.id.send_gift:
                searchCondition.orderStatus = getResources().getString(R.string.send_gift);
                updateGiftStatus();
                break;
            case R.id.received:
                searchCondition.orderStatus = getResources().getString(R.string.received);
                updateGiftStatus();
                break;
            case R.id.one_month:
                searchCondition.recentConsumption = MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_ONE;
                updateRecentConsumption(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_ONE);
                break;
            case R.id.three_month:
                searchCondition.recentConsumption = MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_THREE;
                updateRecentConsumption(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_THREE);
                break;
            case R.id.six_month:
                searchCondition.recentConsumption = MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_SIX;
                updateRecentConsumption(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_SIX);
                break;
            case R.id.reset:
                searchCondition.reset();
                initFilterLayout();
                break;
            case R.id.sure:
                if(mIntegratingFrom.getText().toString().trim() == null || mIntegratingFrom.getText().toString().trim().isEmpty()) {
                    searchCondition.integralAmountStart = null;
                } else {
                    searchCondition.integralAmountStart = StringUtils.StringChangeToInt(mIntegratingFrom.getText().toString().trim());
                }
                if(mIntegratingTo.getText().toString().trim() == null || mIntegratingTo.getText().toString().trim().isEmpty()) {
                    searchCondition.integralAmountEnd = null;
                } else {
                    searchCondition.integralAmountEnd = StringUtils.StringChangeToInt(mIntegratingTo.getText().toString().trim());
                }
                if(mDateSelectFrom.getText().toString().trim().equals(getResources().getString(R.string.select))) {
                    searchCondition.registerTimeStart = null;
                } else {
                    searchCondition.registerTimeStart = mDateSelectFrom.getText().toString().trim();
                }
                if(mDateSelectTo.getText().toString().trim().equals(getResources().getString(R.string.select))) {
                    searchCondition.registerTimeEnd = null;
                } else {
                    searchCondition.registerTimeEnd = mDateSelectTo.getText().toString().trim();
                }
                mDlLeft.closeDrawers();
                requestData();
                break;
        }
    }


    private void selection(int position) {
        mSortTitle.setText(getAddItemsName().get(position));
        switch (position) {
            case 0:
                searchCondition.sortConditionDTO.ascOrDesc = MemberSearchConditionDTO.SORT_ORDER_DESC;
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.TYPE_SORT_INITIATION;
                requestData();
                break;
            case 1:
                searchCondition.sortConditionDTO.ascOrDesc = MemberSearchConditionDTO.SORT_ORDER_ASCE;
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.TYPE_SORT_INITIATION;
                requestData();
                break;
            case 2:
                searchCondition.sortConditionDTO.ascOrDesc = MemberSearchConditionDTO.SORT_ORDER_ASCE;
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.TYPE_SORT_INTEGRAL;
                requestData();
                break;
            case 3:
                searchCondition.sortConditionDTO.ascOrDesc = MemberSearchConditionDTO.SORT_ORDER_DESC;
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.TYPE_SORT_INTEGRAL;
                requestData();
                break;
            case 4:
                searchCondition.sortConditionDTO.ascOrDesc = "";
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.SORT_ORDER_ALL;
                requestData();
                break;
            case 5:
                searchCondition.sortConditionDTO.ascOrDesc = MemberSearchConditionDTO.SORT_ORDER_DESC;
                searchCondition.sortConditionDTO.sortKey = MemberSearchConditionDTO.TYPE_SORT_GIFT;
                requestData();
                break;
        }
    }


    private ArrayList<String> getAddItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.member_sort_entry_time_down));
        items.add(getResources().getString(R.string.member_sort_entry_time_up));
        items.add(getResources().getString(R.string.member_sort_integral_up));
        items.add(getResources().getString(R.string.member_sort_integral_down));
        items.add(getResources().getString(R.string.member_sort_all));
        items.add(getResources().getString(R.string.member_sort_gift));
        return items;
    }

    @Override
    public void setMemberFilterProductType(ProductType bean) {
        if(bean.getModels() != null && bean.getModels().size() > 0) {
            mFilterAdapter.setNewData(bean.getModels());
        }
    }

    private void initFilterLayout() {
        initmemberType(MemberSearchConditionDTO.MEMBER_TYPE_FANS  + "", mFans);
        initmemberType(MemberSearchConditionDTO.MEMBER_TYPE_VIP  + "", mGeneralVip);
        initmemberType(MemberSearchConditionDTO.MEMBER_TYPE_ADVANCED_VIP  + "", mSuperVip);
        initDateText(searchCondition.registerTimeStart, mDateSelectFrom);
        initDateText(searchCondition.registerTimeEnd, mDateSelectTo);
        initIntegratingText(searchCondition.integralAmountStart, mIntegratingFrom);
        initIntegratingText(searchCondition.integralAmountEnd, mIntegratingTo);
        updateGiftStatus();
        updateRecentConsumption(searchCondition.recentConsumption);
        if(mFilterAdapter != null) {
            mFilterAdapter.notifyDataSetChanged();
        }
    }

    private void initmemberType(String type, TextView mView) {
        if(searchCondition.groupId.contains(type)) {
            mView.setSelected(true);
        } else {
            mView.setSelected(false);
        }
    }

    private void initDateText(String string, TextView mView) {
        if(string != null && !string.isEmpty()) {
            mView.setText(string + "");
        } else {
            mView.setText(getResources().getString(R.string.select));
        }
    }

    private void initIntegratingText(Integer vale, EditText mView) {
        if(vale != null) {
            mView.setText(vale + "");
        } else {
            mView.setText("");
            mView.setHint(getResources().getString(R.string.select));
        }
    }

    private void updateGiftStatus() {
        if(getResources().getString(R.string.request).equals(searchCondition.orderStatus)) {
            mRequest.setSelected(true);
            mSendGift.setSelected(false);
            mReceived.setSelected(false);
        } else if(getResources().getString(R.string.send_gift).equals(searchCondition.orderStatus)) {
            mRequest.setSelected(false);
            mSendGift.setSelected(true);
            mReceived.setSelected(false);
        } else if(getResources().getString(R.string.received).equals(searchCondition.orderStatus)) {
            mRequest.setSelected(false);
            mSendGift.setSelected(false);
            mReceived.setSelected(true);
        } else {
            mRequest.setSelected(false);
            mSendGift.setSelected(false);
            mReceived.setSelected(false);
        }
    }

    private void updateRecentConsumption(Integer value) {
        if(value != null && value.equals(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_ONE)) {
            mOneMonth.setSelected(true);
            mThreeMonth.setSelected(false);
            mSixMonth.setSelected(false);
        } else if(value != null && value.equals(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_THREE)) {
            mOneMonth.setSelected(false);
            mThreeMonth.setSelected(true);
            mSixMonth.setSelected(false);
        } else if(value != null && value.equals(MemberSearchConditionDTO.RECENTLY_CONSUME_TIME_SIX)) {
            mOneMonth.setSelected(false);
            mThreeMonth.setSelected(false);
            mSixMonth.setSelected(true);
        } else {
            mOneMonth.setSelected(false);
            mThreeMonth.setSelected(false);
            mSixMonth.setSelected(false);
        }
    }

    private void selectMemberType(TextView view, int value) {
        String str1 = value + ",";
        String str2 = "," + value;
        String str3 = value + "";
        if(view.isSelected()) {
            view.setSelected(false);
            if(searchCondition.groupId == null || searchCondition.groupId.isEmpty()) {
                return;
            } else if(searchCondition.groupId.contains(str1)) {
                searchCondition.groupId = searchCondition.groupId.replace(str1, "");
            } else if(searchCondition.groupId.contains(str2)) {
                searchCondition.groupId = searchCondition.groupId.replace(str2, "");
            } else if(searchCondition.groupId.contains(str3)) {
                searchCondition.groupId = searchCondition.groupId.replace(str3, "");
            }
         } else {
            view.setSelected(true);
            if(searchCondition.groupId == null || searchCondition.groupId.isEmpty()) {
                searchCondition.groupId = str3;
            } else {
                searchCondition.groupId += str2;
            }
        }
    }

    private void chooseDate(final TextView mView) {
        String string = mView.getText().toString().trim();
        Date date;
        if(string.equals(getResources().getString(R.string.select))) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = DateFormatter.stringToDate(string);
        }
        FragmentManager manager = getSupportFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(date);
        dialog.show(manager, Constant.DIALOG_DATE);
        dialog.setOnDialogListener(new DatePickerFragment.OnDialogListener() {
            @Override
            public void onDialogClick(Date date) {
                mView.setText(new DateFormatter().DateFormat(date));
            }
        });
    }

    private void initFilterProductType() {
        mFilterAdapter = new FilterProductTypeAdapter(R.layout.item_product_type_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false);
        mProductType.setLayoutManager(gridLayoutManager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mProductType.setAdapter(mFilterAdapter);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex = 2;
                requestData();
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
        if(memberBean == null || memberBean.getPageInfo() == null ){
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
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
                    searchCondition.storeId = storeId;
                    searchCondition.page = pageIndex;
                    mPresenter.getMoreMemberListData(searchCondition);
                }
            }
        },1000);
    }

    @Override
    public void onBackPressed() {
        if(mDlLeft.isDrawerOpen(Gravity.RIGHT)) {
            mDlLeft.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
