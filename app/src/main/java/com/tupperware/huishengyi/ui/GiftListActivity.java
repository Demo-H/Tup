package com.tupperware.huishengyi.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.GiftListAdapter;
import com.tupperware.huishengyi.component.DaggerGiftListActivityComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.module.GiftListPresenterModule;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.ui.contract.GiftListContract;
import com.tupperware.huishengyi.ui.presenter.GiftListPresenter;
import com.tupperware.huishengyi.utils.logutils.LogF;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tupperware.huishengyi.config.Constants.REQUEST_CODE_MEMBER_GIFT_LISTL_MORE;

/**
 * Created by dhunter on 2018/6/6.
 */

public class GiftListActivity extends BaseActivity implements GiftListContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener, TupVolley.TupVolleyErrorListener, TupVolley.TupVolleyListener {

    private static final String TAG = "GiftListActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;
    @Inject
    GiftListPresenter mPresenter;
    private GiftListAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;
    private String member_id;
    private int pageIndex = 2;  //分页加载更多，从第二页开始

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_server); //复用
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.logistics_schedule));
        mRightNext.setVisibility(View.GONE);
        DaggerGiftListActivityComponent.builder()
                .appComponent(getAppComponent())
                .giftListPresenterModule(new GiftListPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new GiftListAdapter(R.layout.item_logistics_schedule_recycleview);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
    }

    @Override
    protected void initLayoutData() {
        if(Constant.Demo) {
            member_id = "2069506";
        } else {
            member_id = getIntent().getStringExtra(Constant.MEMBER_PHONE);
        }
        showDialog();
//        mPresenter.getGiftListData(member_id);
        getGiftListData(Constant.FIRST_PAGE_INDEX);
    }

    private void getGiftListData(int pageindex) {
        Uri.Builder builder = Uri.parse(ServerURL.MEMBER_GIFT_LIST).buildUpon();
        builder.appendQueryParameter("memberId", member_id);
        builder.appendQueryParameter("pageNo", pageindex +"");
        builder.appendQueryParameter("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE + "");
        String url = builder.toString();
        if (pageindex == Constant.FIRST_PAGE_INDEX) {
            mTupVolley.get(Constants.REQUEST_CODE_MEMBER_GIFT_LISTL, url, this, this, headerparams);
        } else {
            mTupVolley.get(REQUEST_CODE_MEMBER_GIFT_LISTL_MORE, url, this, this, headerparams);
        }
    }

    @Override
    public void ok(int requestCode, String json) {
        LogF.i(TAG, "json" + json);
        hideDialog();
        GiftBean mBean = GiftBean.createInstanceByJson(json);
        if (requestCode == Constants.REQUEST_CODE_MEMBER_GIFT_LISTL) {
            if (mBean == null) {
                toast(getString(R.string.system_error));
                return;
            }
            if (!mBean.isSuccess()) {
                toast(mBean.getMessage());
                return;
            }
            if (mBean.models == null || mBean.models.isEmpty()) {
                setEmptyView();
            } else {
                setGiftListData(mBean);
            }
        } else if (requestCode == REQUEST_CODE_MEMBER_GIFT_LISTL_MORE) {
            pageIndex++;
            setMoreGiftListData(mBean);
        }
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        setNetErrorView();
        return false;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        pageIndex = 2;
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mPresenter.getGiftListData(member_id);
                getGiftListData(Constant.FIRST_PAGE_INDEX);
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

    @OnClick({R.id.left_back, R.id.error_layout })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.error_layout:
                getGiftListData(Constant.FIRST_PAGE_INDEX);
                break;
        }
    }

    @Override
    public void setGiftListData(GiftBean mBean) {
        mAdapter.setNewData(mBean.models);
        if(!mBean.pageInfo.hasMore) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreGiftListData(GiftBean mBean) {
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
//                    mPresenter.getMoreGiftListData(member_id, pageIndex);
                    getGiftListData(pageIndex);
                }
            }
        },1000);
    }
}
