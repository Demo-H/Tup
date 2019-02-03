package com.tupperware.biz.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/31.
 * 会员礼品寄送进度详情
 */

public class GiftDetialActivity extends BaseActivity {
    private static final String TAG = "GiftDetialActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.photo)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.order_number)
    TextView mOrderNumber;
    @BindView(R.id.order_status)
    TextView mOrderStatus;
    @BindView(R.id.request_time)
    TextView mRequestTime;
    @BindView(R.id.send_goods_time)
    TextView mSendGoodsTime;
    @BindView(R.id.express_company_t)
    TextView mExpressCompany;
    @BindView(R.id.express_code_t)
    TextView mExpressCode;
    @BindView(R.id.cancle_reason)
    TextView mCancelReason;
    @BindView(R.id.receiver_name)
    TextView mReceiverName;
    @BindView(R.id.receiver_tel)
    TextView mReceiverTel;
    @BindView(R.id.reveiver_addr_t)
    TextView mReceiverAddr;
    @BindView(R.id.gift_name)
    TextView mGiftName;

    private GiftBean.GiftInfo mGiftInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics_schedule;
    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.logistics_schedule));
        mRightNext.setVisibility(View.GONE);
    }

    @Override
    protected void requestData() {
        Bundle bundle = this.getIntent().getExtras();
        mGiftInfo = (GiftBean.GiftInfo)bundle.getSerializable(Constant.MEMBER_GIFT_DATA);
        refreshUI(mGiftInfo);
    }

    @OnClick({R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
        }
    }

    private void refreshUI(GiftBean.GiftInfo mBean) {
        if(mBean!= null) {
            try {
                String imageUrl = StringUtils.unescapeJava(mBean.getProductImage());
                simpleDraweeView.setImageURI(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mGiftName.setText(mBean.getRedundantProductNameCn());
            mOrderNumber.setText(mBean.getOrderSn());
            mOrderStatus.setText(mBean.getOrderStatusName());
            mRequestTime.setText(DateFormatter.timeSecondToDateDay(mBean.getCreateTime()));
            if(mBean.getDeliverTime() == 0) {
                mSendGoodsTime.setText("");
            } else {
                mSendGoodsTime.setText(DateFormatter.DatetoString(mBean.getDeliverTime()));
            }
            mExpressCompany.setText(mBean.getLogisticCompany());
            mExpressCode.setText(mBean.getPostId());
            mCancelReason.setText(mBean.getGiftOrderReason());
            mReceiverName.setText(mBean.getGiftOrderConsignee());
            mReceiverTel.setText(mBean.getGiftOrderTel());
            mReceiverAddr.setText(mBean.getGiftOrderProvince() + mBean.getGiftOrderCity() + mBean.getGiftOrderDistrict() + mBean.getGiftOrderAddress());
        }
    }
}
