package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.android.dhunter.common.utils.SharePreferenceData;
import com.tupperware.huishengyi.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class SaleRecordDetialActivity extends BaseActivity {

    private static final String TAG = "SaleRecordDetialActivity";

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

    @BindView(R.id.goods_name)
    TextView mGoodsName;
    @BindView(R.id.goods_in_count_add)
    ImageView mGoodsInAdd;
    @BindView(R.id.goods_in_count_text)
    TextView mGoodsInText;
    @BindView(R.id.goods_in_count_reduce)
    ImageView mGoodsInReduce;
    @BindView(R.id.goods_out_count_add)
    ImageView mGoodsOutAdd;
    @BindView(R.id.goods_out_count_text)
    TextView mGoodsOutText;
    @BindView(R.id.goods_out_count_reduce)
    ImageView mGoodsOutReduce;
    @BindView(R.id.goods_origin_count_add)
    ImageView mGoodsOriginAdd;
    @BindView(R.id.goods_origin_count_text)
    TextView mGoodsOriginText;
    @BindView(R.id.goods_origin_count_reduce)
    ImageView mGoodsOriginReduce;


    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String goodsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_record_detial);
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
        mGoodsName.setText(goodsName);
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setText(getResources().getString(R.string.complete));
        mTitle.setText(getResources().getString(R.string.sold_record));
    }

    @OnClick({R.id.left_back, R.id.next, R.id.goods_in_count_add, R.id.goods_in_count_reduce, R.id.goods_out_count_add,
            R.id.goods_out_count_reduce, R.id.goods_origin_count_add, R.id.goods_origin_count_reduce})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                toast("完成");
                break;
            case R.id.goods_in_count_add:
                addCount(mGoodsInText);
                break;
            case R.id.goods_in_count_reduce:
                reduceCount(mGoodsInText);
                break;
            case R.id.goods_out_count_add:
                addCount(mGoodsOutText);
                break;
            case R.id.goods_out_count_reduce:
                reduceCount(mGoodsOutText);
                break;
            case R.id.goods_origin_count_add:
                addCount(mGoodsOriginText);
                break;
            case R.id.goods_origin_count_reduce:
                reduceCount(mGoodsOriginText);
                break;
        }
    }

    private void addCount(TextView text) {
        String count = text.getText().toString();
        int number = StringUtils.StringChangeToInt(count) + 1;
        text.setText(number + "");
    }
    private void reduceCount(TextView text) {
        String count = text.getText().toString();
        int number = StringUtils.StringChangeToInt(count) - 1;
        if(number < 0) {
            number = 0;
        }
        text.setText(number + "");
    }


}
