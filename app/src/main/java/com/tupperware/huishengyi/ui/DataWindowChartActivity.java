package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.DataWindowChartFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/9.
 * 数据窗所有的数据分析点击都来到这个界面
 */

public class DataWindowChartActivity extends BaseActivity{

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightIcon;
    @BindView(R.id.toolbar_title)
    TextView mToolTitle;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private DataWindowChartFragment mFragment;
    private String titleFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_zhaomu);  //复用
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        titleFlag = getIntent().getStringExtra(Constant.ACTIVITY_TITLE);
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ACTIVITY_TITLE, titleFlag);
        mFragment = DataWindowChartFragment.newInstance(bundle);
        mFragment.setRightIconText(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.zhaomuFrame, mFragment).commit();
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.date_btn);
        if(Constant.SINGLE_VIP_COUNT_PRECENT.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_vip_count_precent));
        } else if(Constant.SINGLE_VIP_STATUS.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_vip_status));
        } else if(Constant.SINGLE_PRODUCT_TOP.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_product_top10));
        } else if(Constant.SINGLE_PRODUCT_SALE_TOP.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_product_sale_top10));
        } else if(Constant.SINGLE_SALE_ATTRIBUTE.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_sale_attribute));
        } else if(Constant.SINGLE_MAIN_PRODUCT.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.single_main_product));
        } else if(Constant.STORE_SALE_MONEY_LIST.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_sale_money_list));
        } else if(Constant.STORE_SALE_LIST.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_sale_list));
        } else if(Constant.STORE_SALE_ANALYSIS.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_sale_analysis));
        } else if(Constant.STORE_VIP_SALE_PRECENT.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_vip_sale_precent));
        } else if(Constant.STORE_VIP_STATUS_ANALYSIS.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_vip_status_analysis));
        } else if(Constant.STORE_SALE_ATTRIBUTE.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.store_sale_attribute));
        } else if(Constant.PRODUCT_SALE_TOP.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.product_sale_top10));
        } else if(Constant.PRODUCT_SALE_MONEY_TOP.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.product_sale_money_top10));
        } else if(Constant.MAIN_PRODUCT_LIST.equals(titleFlag)) {
            mToolTitle.setText(getResources().getString(R.string.main_product_list));
        }
    }


    @OnClick({R.id.left_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
        }

    }

}
