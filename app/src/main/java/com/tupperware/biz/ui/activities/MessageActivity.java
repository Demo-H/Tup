package com.tupperware.biz.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.MessageAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.msg.MsgBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.component.DaggerMessageActivityComponent;
import com.tupperware.biz.ui.contract.MessageContract;
import com.tupperware.biz.ui.module.MessagePresenterModule;
import com.tupperware.biz.ui.presenter.MessagePresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MessageActivity extends BaseActivity implements MessageContract.View,
        PtrHandler  {

    private static final String TAG = "MessageActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
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
    MessagePresenter mPresenter;
    private MessageAdapter mAdapter;
    private View emptyView;
    private TextView mEmptyText;

//    @BindView(R.id.order_reminding_rl)
//    RelativeLayout mOrderRemindingRl;
//    @BindView(R.id.order_reminding)
//    TextView mOrderRemindingTitle;
//    @BindView(R.id.order_reminding_red_tip)
//    ImageView mOrderRedTip;
//    @BindView(R.id.order_number)
//    TextView mOrderNumber;
//    @BindView(R.id.order_user)
//    TextView mOrderUser;
//    @BindView(R.id.order_time)
//    TextView mOrderTime;
//    @BindView(R.id.order_status)
//    TextView mOrderStatus;
//
//    @BindView(R.id.zixun_update_rl)
//    RelativeLayout mZixunUpdateRl;
//    @BindView(R.id.zixun_update)
//    TextView mZixunUpdateTitle;
//    @BindView(R.id.zixun_update_red_tip)
//    ImageView mZixunRedTip;
//    @BindView(R.id.zixun_update_title)
//    TextView mZixunSubTitle;
//    @BindView(R.id.zixun_update_content)
//    TextView mZixunContent;
//    @BindView(R.id.zixun_update_time)
//    TextView mZixunUpdateTime;
//
//    @BindView(R.id.system_message_rl)
//    RelativeLayout mSystemMessageRl;
//    @BindView(R.id.system_message)
//    TextView mSystemMessageTitle;
//    @BindView(R.id.system_message_red_tip)
//    ImageView mSystemRedTip;
//    @BindView(R.id.system_message_title)
//    TextView mSystemSubTitle;
//    @BindView(R.id.system_message_content)
//    TextView mSystemContent;
//    @BindView(R.id.system_message_time)
//    TextView mSysMsgTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initLayout() {
        initEmptylayout();
        initToolBar();
        DaggerMessageActivityComponent.builder()
                .appComponent(getAppComponent())
                .messagePresenterModule(new MessagePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new MessageAdapter(R.layout.item_msg_recyclerview);
//        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        mPresenter.getMsgData();
//        mOrderRemindingTitle.setText(String.format(getResources().getString(R.string.order_reminding_count), 12));
//        mZixunUpdateTitle.setText(String.format(getResources().getString(R.string.zixun_update_count), 2));
//        mSystemMessageTitle.setText(String.format(getResources().getString(R.string.sytem_message_count), 5));
    }

    private void initEmptylayout() {
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_msg));
    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.message));
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMsgData();
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setNormalView() {
        mRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void setNetErrorView() {
        mRefreshHeader.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        mRefreshHeader.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }



    @OnClick({R.id.left_back, R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.error_layout:
                mPresenter.getMsgData();
                break;
//            case R.id.order_reminding_rl:
//                jumptoActivity(view.getContext(), MessageDetialActivity.class, Constant.ORDER_REMINDING);
//                break;
//            case R.id.zixun_update_rl:
//                jumptoActivity(view.getContext(), MessageDetialActivity.class, Constant.ZIXUN_UPDATE);
//                break;
//            case R.id.system_message_rl:
//                jumptoActivity(view.getContext(), MessageDetialActivity.class, Constant.Sys_MSG);
//                break;
        }
    }

//    private void jumptoActivity(Context context, Class<?> _cls, String msgType) {
//        Intent intent = new Intent();
//        intent.setClass(context, _cls);
//        intent.putExtra(Constant.MSG_TYPE, msgType);
//        context.startActivity(intent);
//    }

    @Override
    public void setMsgData(MsgBean msg) {
        mAdapter.setNewData(msg.models);
    }
}
