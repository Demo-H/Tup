package com.tupperware.huishengyi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/31.
 * 会员礼品寄送进度详情
 */

public class GiftDetialActivity extends BaseActivity /*implements TupVolley.TupVolleyErrorListener, TupVolley.TupVolleyListener*/{
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

    private GiftBean.GiftModel mGiftInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_schedule);
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
    }

    @Override
    protected void initLayoutData() {
        Bundle bundle = this.getIntent().getExtras();
        mGiftInfo = (GiftBean.GiftModel)bundle.getSerializable(Constant.MEMBER_GIFT_DATA);
        refreshUI(mGiftInfo);
//        Uri.Builder builder = Uri.parse(ServerURL.MEMBER_GIFT_LIST_DETIAL).buildUpon();
//        builder.appendQueryParameter("orderId", order_id);
//        String url = builder.toString();
//        mTupVolley.get(Constants.REQUEST_CODE_MEMBER_GIFT_LISTL_DETIAL, url, this, this, headerparams);
    }

    @OnClick({R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
        }
    }

//    @Override
//    public void ok(int requestCode, String json) {
//        LogF.i(TAG, "json" + json);
//        hideDialog();
//        GiftBean mBean = GiftBean.createInstanceByJson(json);
//        if (requestCode == Constants.REQUEST_CODE_MEMBER_GIFT_LISTL_DETIAL) {
//            if (mBean == null) {
//                toast(getString(R.string.system_error));
//                return;
//            }
//            if (!mBean.isSuccess()) {
//                toast(mBean.getMessage());
//                return;
//            }
//            refreshUI(mBean);
//        }
//    }
//
//
//    @Override
//    public boolean error(int requestCode, ResponseBean errorCode) {
//        hideDialog();
//        return false;
//    }


    private void refreshUI(GiftBean.GiftModel mBean) {
        if(mBean!= null) {
            try {
                String imageUrl = StringUtils.unescapeJava(mBean.product_image);
                simpleDraweeView.setImageURI(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mGiftName.setText(mBean.redundant_product_name_cn);
            mOrderNumber.setText(mBean.order_sn);
            mOrderStatus.setText(mBean.order_status_name);
            mRequestTime.setText(new DateFormatter().timestampToDate(mBean.create_time));
            mSendGoodsTime.setText(new DateFormatter().DatetoString(mBean.deliver_time));
            mExpressCompany.setText(mBean.logistic_company);
            mExpressCode.setText(mBean.post_id);
            mCancelReason.setText(mBean.gift_order_reason);
            mReceiverName.setText(mBean.gift_order_consignee);
            mReceiverTel.setText(mBean.gift_order_tel);
            mReceiverAddr.setText(mBean.gift_order_province + mBean.gift_order_city + mBean.gift_order_district + mBean.gift_order_address);
        }
    }
}
