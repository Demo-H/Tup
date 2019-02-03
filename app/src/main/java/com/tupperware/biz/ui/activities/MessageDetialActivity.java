package com.tupperware.biz.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.fragment.MessageDetialFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MessageDetialActivity extends BaseActivity {

    private static final String TAG = "MessageDetialActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRight;

    private MessageDetialFragment mFragment;
//    private String msgType;
    private long msgTagId;
    private String mTitleStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detial;
    }

    @Override
    protected void initLayout() {
        msgTagId = getIntent().getLongExtra(Constant.MSG_TAG_ID, 0);
        mTitleStr = getIntent().getStringExtra(Constant.MSG_TITLE);
        initToolbar();
        Bundle bundle = new Bundle();
        bundle.putLong(Constant.MSG_TAG_ID, msgTagId);
        mFragment = new MessageDetialFragment();
        mFragment.setArguments(bundle);
//        mFragment.setTextViewEdit(mRight);
        getSupportFragmentManager().beginTransaction().add(R.id.msg_detial_frame, mFragment, "messageDetial").commit();
    }

    @Override
    protected void requestData() {

    }

    private void initToolbar() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(mTitleStr);
//        mRight.setText(getResources().getString(R.string.edit));
//        if(Constant.ORDER_REMINDING.equals(msgType)) {
//            mTitle.setText(getResources().getString(R.string.order_reminding));
//        } else if(Constant.ZIXUN_UPDATE.equals(msgType)) {
//            mTitle.setText(getResources().getString(R.string.zixun_update));
//        } else if(Constant.Sys_MSG.equals(msgType)) {
//            mTitle.setText(getResources().getString(R.string.sytem_message));
//        }
    }

    @OnClick(R.id.left_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }
}
