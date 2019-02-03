package com.tupperware.biz.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.OnlineOrderDetialAdapter;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.component.DaggerOnlineOrderDetialActivityComponent;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.ResOrderPendingBean;
import com.tupperware.biz.entity.order.OrderItemBean;
import com.tupperware.biz.http.OrderDataManager;
import com.tupperware.biz.ui.module.OnlineOrderDetialPresenterModule;
import com.tupperware.biz.ui.contract.OnlineOrderDetialContract;
import com.tupperware.biz.ui.presenter.OnlineOrderDetialPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/8.
 */

public class OnlineOrderDetialActivity extends BaseActivity implements OnlineOrderDetialContract.View {

    private static final String TAG = "OnlineOrderDetialActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.order_status_icon)
    ImageView mOrderStatusIcon;
    @BindView(R.id.order_status)
    TextView mOrderStatus;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_tel)
    TextView mUserTel;
    @BindView(R.id.receiver_addr)
    TextView mReceiverAddr;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.order_number)
    TextView mOrderNumber;
    @BindView(R.id.copy)
    TextView mCopy;
    @BindView(R.id.order_date)
    TextView mOrderDate;
    @BindView(R.id.order_style)
    TextView mOrderStyle;
    @BindView(R.id.shipping)
    TextView mShipping;
    @BindView(R.id.remarks)
    TextView mRemarks;
    @BindView(R.id.product_money)
    TextView mProductMoney;
    @BindView(R.id.carriage)
    TextView mCarriageMoney;
    @BindView(R.id.total)
    TextView mTotalMoney;
    @BindView(R.id.call_contacts)
    TextView mCallContacts;
    @BindView(R.id.express_company)
    TextView mExpressCompany;
    @BindView(R.id.express_code)
    EditText mExpressCode;
    @BindView(R.id.affirm_send)
    TextView mAffirmSend;
    @BindView(R.id.express_code_result)
    TextView mExpressCodeResult;


    @BindView(R.id.order_status_ll)
    LinearLayout mOrderStatusLl;
    @BindView(R.id.call_contacts_rl)
    RelativeLayout mCallContactsRl;
    @BindView(R.id.express_ll)
    LinearLayout mExpressLl;
    @BindView(R.id.affirm_send_ll)
    LinearLayout mAffirmOrderLl;
    @BindView(R.id.modified)
    TextView mModified;
    @BindView(R.id.express_code_result_ll)
    LinearLayout mExpressCodeResultLl;

    private int position;
    private long order_id;
    private String telNum;
    private OnlineOrderDetialAdapter mAdapter;
    private List<ResOrderPendingBean.OrderContentBean> beanList = new ArrayList<>();

    @Inject
    OnlineOrderDetialPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_order_detial;
    }

    @Override
    protected void initLayout() {
        position = getIntent().getIntExtra(Constant.POSITION, 0);
        order_id = getIntent().getLongExtra(Constant.ORDER_ID, 0);
        initBaseLayout();
        DaggerOnlineOrderDetialActivityComponent.builder()
                .appComponent(getAppComponent())
                .onlineOrderDetialPresenterModule(new OnlineOrderDetialPresenterModule(this, OrderDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OnlineOrderDetialAdapter(R.layout.item_resorder_product_detial_recyclerview);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void requestData() {
        mPresenter.getOrderItemData(order_id);
    }

    private void initBaseLayout() {
        mTitle.setText(getResources().getString(R.string.order_detial));
        mRightNext.setVisibility(View.GONE);
        refreshbyOrderStatus(position);
//        showOrder(beanList);
    }

    @OnClick({R.id.left_back, R.id.copy, R.id.call_contacts, R.id.modified, R.id.affirm_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.copy:
                copyOrderInfo();
                break;
            case R.id.call_contacts:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    toast("请设置APP拨打电话权限");
                }
                break;
            case R.id.modified:
                toast("modified");
                break;
            case R.id.affirm_send:
                toast("正在准备发货");
                finish();
                break;
        }
    }


    @Override
    public void setOrderItemData(OrderItemBean orderItemBean) {
        refreshUIwithData(orderItemBean);
        mAdapter.setNewData(orderItemBean.model.goodsList);
    }

    @Override
    public void setNormalView() {

    }

    @Override
    public void setNetErrorView() {

    }

    @Override
    public void setEmptyView() {

    }

    private void refreshbyOrderStatus(int orderStatus) {
        switch(orderStatus) {
            case Constant.WAIT_PAY:
                mOrderStatusIcon.setImageResource(R.mipmap.order_unpaid_ic);
                mOrderStatus.setText(getResources().getString(R.string.order_wait_pay));
                mOrderStatusLl.setVisibility(View.VISIBLE);
                mCallContactsRl.setVisibility(View.VISIBLE);
                mExpressLl.setVisibility(View.GONE);
                mAffirmOrderLl.setVisibility(View.GONE);
                mModified.setVisibility(View.GONE);
                mExpressCodeResultLl.setVisibility(View.GONE);
                break;
            case Constant.WAIT_SEND:
                mOrderStatusIcon.setImageResource(R.mipmap.order_notship_ic);
                mOrderStatus.setText(getResources().getString(R.string.order_wait_send));
                mOrderStatusLl.setVisibility(View.VISIBLE);
                mCallContactsRl.setVisibility(View.GONE);
                mExpressLl.setVisibility(View.VISIBLE);
                mAffirmOrderLl.setVisibility(View.VISIBLE);
                mModified.setVisibility(View.VISIBLE);
                mExpressCodeResultLl.setVisibility(View.GONE);
                break;
            case Constant.SENDED:
                mOrderStatusIcon.setImageResource(R.mipmap.order_ship_ic);
                mOrderStatus.setText(getResources().getString(R.string.order_sended));
                mOrderStatus.setTextColor(getResources().getColor(R.color.color_22ac38));
                mOrderStatusLl.setVisibility(View.VISIBLE);
                mCallContactsRl.setVisibility(View.GONE);
                mExpressLl.setVisibility(View.GONE);
                mAffirmOrderLl.setVisibility(View.GONE);
                mModified.setVisibility(View.GONE);
                mExpressCodeResultLl.setVisibility(View.VISIBLE);
                break;
//            case Constant.ROLLBACK:
//            case Constant.COMPLETED:
//            case Constant.CLOSED:
            default:
                mOrderStatusLl.setVisibility(View.GONE);
                mCallContactsRl.setVisibility(View.GONE);
                mExpressLl.setVisibility(View.GONE);
                mAffirmOrderLl.setVisibility(View.GONE);
                mModified.setVisibility(View.GONE);
                mExpressCodeResultLl.setVisibility(View.GONE);
        }
    }

    private void refreshUIwithData(OrderItemBean orderItemBean) {
        if(orderItemBean != null && orderItemBean.model != null) {
            refreshbyOrderStatus(StringUtils.StringChangeToInt(orderItemBean.model.orderStatus));
            mUserName.setText(orderItemBean.model.recName);
            telNum = orderItemBean.model.recMobile;
            mUserTel.setText(telNum.substring(0, 3) + "********");
            mReceiverAddr.setText(orderItemBean.model.recAddress);
            mOrderNumber.setText(orderItemBean.model.code);
            mOrderDate.setText(DateFormatter.DatetoString(orderItemBean.model.buytime));
            mOrderStyle.setText(orderItemBean.model.orderTypeValue);
            mShipping.setText(orderItemBean.model.takeTypeValue);
            mRemarks.setText(orderItemBean.model.remark);
            mProductMoney.setText("￥" + orderItemBean.model.totalMny);
            mCarriageMoney.setText("￥" + orderItemBean.model.postage);
            mTotalMoney.setText("￥" + orderItemBean.model.realMny);
        }
    }

    private void copyOrderInfo() {
        String string = mOrderNumber.getText().toString().trim();
//        string = getResources().getString(R.string.order_number) + mOrderNumber.getText().toString().trim() + "\n";
//        string = string + getResources().getString(R.string.order_time) + mOrderDate.getText().toString().trim() + "\n";
//        string = string + getResources().getString(R.string.order_style) + mOrderStyle.getText().toString().trim() + "\n";
//        string = string + getResources().getString(R.string.shipping) + mShipping.getText().toString().trim() + "\n";
//        string = string + getResources().getString(R.string.remarks) + mRemarks.getText().toString().trim();
        ClipboardManager clipboardManager  = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null,string));
        if (clipboardManager.hasPrimaryClip()){
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
        if(!string.isEmpty()) {
            toast(getResources().getString(R.string.copy_success));
        }
    }
}
