package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.MessageDetialAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.component.DaggerMessageDetialFragmentComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.msg.MsgItemBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.module.MessageDetailPresenterModule;
import com.tupperware.biz.ui.contract.MessageDetialContract;
import com.tupperware.biz.ui.presenter.MessageDetailPresenter;
import com.tupperware.biz.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MessageDetialFragment extends BaseFragment implements MessageDetialContract.View,
        PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "MessageDetialFragment";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorLayout;

    @Inject
    MessageDetailPresenter mPresenter;

    private MessageDetialAdapter mAdapter;
    private long msgTagId;
    private long timestamp;  //按时间加载更多
    private View rootView;
    private View emptyView;
    private TextView mEmptyText;
//    @BindView(R.id.msg_detial_content_view)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.edit_ll)
//    LinearLayout mEditll;
//    @BindView(R.id.set_read)
//    TextView mSetRead;
//    @BindView(R.id.delete)
//    TextView mDelete;
//    private String msgType;
//    private TextView mTextViewEdit;
//    private MsgOrderRemindingAdapter mOrdReAdapter;
//    private MsgZixunUpdateAdapter mZixunUpdateAdapter;
//    private MsgSysAdapter mSysAdapter;
//    private RelativeLayout mCheckRl;

    public static MessageDetialFragment newInstance() {
        MessageDetialFragment fragment = new MessageDetialFragment();
        return fragment;
    }

//    public void setTextViewEdit(TextView textViewEdit) {
//        mTextViewEdit = textViewEdit;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        emptyView = inflater.inflate(R.layout.view_empty_recycleview, null);
        mEmptyText = (TextView) emptyView.findViewById(R.id.empty_text);
        mEmptyText.setText(getResources().getString(R.string.no_data));
        unbinder = ButterKnife.bind(this, rootView);
//        msgType = getArguments().getString(Constant.MSG_TYPE);
        msgTagId = getArguments().getLong(Constant.MSG_TAG_ID);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerMessageDetialFragmentComponent.builder()
                .appComponent(getAppComponent())
                .messageDetailPresenterModule(new MessageDetailPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new MessageDetialAdapter(R.layout.item_message_zixun_recyclerview);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

//        if(Constant.ORDER_REMINDING.equals(msgType)) {
//            mOrdReAdapter = new MsgOrderRemindingAdapter(R.layout.item_order_reminding_recyclerview);
//            mOrdReAdapter.setEnableLoadMore(true);
//            mRecyclerView.setAdapter(mOrdReAdapter);
//        } else if(Constant.ZIXUN_UPDATE.equals(msgType)) {
//            mZixunUpdateAdapter = new MsgZixunUpdateAdapter(R.layout.item_message_zixun_recyclerview);
//            mZixunUpdateAdapter.setEnableLoadMore(true);
//            mRecyclerView.setAdapter(mZixunUpdateAdapter);
//        } else if(Constant.Sys_MSG.equals(msgType)) {
//            mSysAdapter = new MsgSysAdapter(R.layout.item_message_sys_recyclerview);
//            mSysAdapter.setEnableLoadMore(true);
//            mRecyclerView.setAdapter(mSysAdapter);
//        }

    }

    @Override
    public void requestData() {
        mPresenter.getMessageDetialData(msgTagId);
//        mTextViewEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if((getResources().getString(R.string.edit)).equals(mTextViewEdit.getText().toString().trim())) {
//                    mTextViewEdit.setText(getResources().getString(R.string.complete));
//                    mEditll.setVisibility(View.VISIBLE);
//                } else {
//                    mTextViewEdit.setText(getResources().getString(R.string.edit));
//                    mEditll.setVisibility(View.GONE);
//                }
//
//                if(Constant.ORDER_REMINDING.equals(msgType)) {
//                    mOrdReAdapter.setShowBox();
//                    mOrdReAdapter.notifyDataSetChanged();
//                } else if(Constant.ZIXUN_UPDATE.equals(msgType)) {
//                    mZixunUpdateAdapter.setShowBox();
//                    mZixunUpdateAdapter.notifyDataSetChanged();
//                } else if(Constant.Sys_MSG.equals(msgType)) {
//                    mSysAdapter.setShowBox();
//                    mSysAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMessageDetialData(msgTagId);
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
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_detial;
    }

    @Override
    public void setMessageDetialData(MsgItemBean msg) {
        mAdapter.setNewData(msg.models);
        timestamp = msg.timelineInfo.timestamp;  //设置时间戳来加载更多
//        if(Constant.ORDER_REMINDING.equals(msgType)) {
//            mOrdReAdapter.addData(msg.content);
//        } else if(Constant.ZIXUN_UPDATE.equals(msgType)) {
//            mZixunUpdateAdapter.addData(msg.content);
//        } else if(Constant.Sys_MSG.equals(msgType)) {
//            mSysAdapter.addData(msg.content);
//        }
        if(!msg.timelineInfo.hasNext) {
            mAdapter.loadMoreEnd(false); //所有数据加载结束
        }
    }

    @Override
    public void setMoreMessageDetialData(MsgItemBean msg) {
        mAdapter.getData().addAll(msg.models);
        mAdapter.loadMoreComplete();
        timestamp = msg.timelineInfo.timestamp;  //设置时间戳来加载更多
        if(msg.models.size() == 0 || !msg.timelineInfo.hasNext) {
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
                } else{
                    mPresenter.getMoreMessageDetialData(msgTagId, timestamp);
                }
            }
        },1000);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getMessageDetialData(msgTagId);
                break;
        }
    }

}
