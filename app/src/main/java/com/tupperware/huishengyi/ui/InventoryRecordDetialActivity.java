package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.progressbar.BGAProgressBar;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class InventoryRecordDetialActivity extends BaseActivity {

    private static final String TAG = "InventoryRecordDetialActivity";

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

    @BindView(R.id.progressbar)
    BGAProgressBar mProgressbar;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String goodsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_record_detial);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        goodsName = getIntent().getStringExtra("GoodName");
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
//        mGoodsName.setText(goodsName);
        mProgressbar.setProgress(54);
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setText(getResources().getString(R.string.complete));
        mTitle.setText(getResources().getString(R.string.inventory_record));
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                toast("完成");
                break;
        }
    }
}
