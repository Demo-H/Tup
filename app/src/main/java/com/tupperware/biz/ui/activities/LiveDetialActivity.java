package com.tupperware.biz.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/18.
 * 直播课堂
 */

public class LiveDetialActivity extends BaseActivity {

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightIcon;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_detial;
    }

    @Override
    protected void initLayout() {
        initToolBar();
    }

    @Override
    protected void requestData() {

    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.biz_share_btn);
        mTitle.setText(getResources().getString(R.string.live_video));
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                toast("share");
                break;
        }
    }
}
