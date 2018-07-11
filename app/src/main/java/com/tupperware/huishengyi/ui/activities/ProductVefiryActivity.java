package com.tupperware.huishengyi.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.entity.VerifyProduct;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.component.DaggerProductVefiryActivityComponent;
import com.tupperware.huishengyi.ui.contract.ProductVerifyContract;
import com.tupperware.huishengyi.ui.module.ProductVefiryPresenterModule;
import com.tupperware.huishengyi.ui.presenter.ProductVefiryPresenter;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.utils.ImageUrl;
import com.tupperware.huishengyi.widget.CustomDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dhunter on 2018/3/15.
 * 产品唯一码验证
 */

public class ProductVefiryActivity extends BaseActivity implements ProductVerifyContract.View {

    private static final String TAG = "ProductVefiryActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.activity_couponvefiry_scansuccess_layout)
    LinearLayout scancouponSuccessLayout;
    @BindView(R.id.activity_couponvefiry_imagebutton)
    ImageButton couponImgBtn;
    @BindView(R.id.activity_couponvefiry_rel)
    RelativeLayout couponMesRel;
    @BindView(R.id.activity_productvefiry_scansuccess_layout)
    LinearLayout scanSuccessLayout;
    @BindView(R.id.activity_productvefiry_scanfail_layout)
    RelativeLayout scanFailLayout;
    @BindView(R.id.activity_productvefiry_affirm_btn)
    Button affirmBtn;
    @BindView(R.id.activity_productvefiry_code_edit)
    EditText productCodeEdit;
    @BindView(R.id.activity_productvefiry_code_text)
    TextView productCodeTextView;
    @BindView(R.id.activity_couponvefiry_code_text)
    TextView couponCodeTextView;
    @BindView(R.id.activity_couponvefiry_image)
    SimpleDraweeView couponImageView;
    @BindView(R.id.activity_productvefiry_product_image)
    SimpleDraweeView productImageView;
    @BindView(R.id.activity_productvefiry_product_name)
    TextView productNameTextView;
    @BindView(R.id.activity_productvefiry_product_price)
    TextView productPriceTextView;
//    @BindView(R.id.activity_productvefiry_vefiry_text)
//    TextView productVerifyTextView;
    @BindView(R.id.activity_productvefiry_affirm_linearlayout)
    LinearLayout mSureNextLinearLayout;
    @BindView(R.id.activity_couponvefiry_lin)
    RelativeLayout explainLinLayout;
    @BindView(R.id.no_coupon_image_text)
    TextView mDaijinquanText;
    @BindView(R.id.activity_productvefiry_content_rel)
    View parentView;
    @BindView(R.id.activity_couponvefiry_msg)
    TextView msgTextview;
    @BindView(R.id.separation_line)
    View lineView;

    @Inject
    ProductVefiryPresenter mPresenter;

    private int isScan;
    //验证成功后的弹出框
    private PopupWindow okPopupWindow;
    //是否确认使用
    private boolean isUse = false;
    //是否可以使用该优惠券
    private boolean isCanUse = false;
    private String productCode;//产品唯一码
    private VerifyProduct verifyProduct;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_vefiry;
    }

    @Override
    protected void initLayout() {
        //将相关activity暂时保存，等到扫描完成后再一次性finish
        ActivityManager.getInstance().addActivity(this);
        DaggerProductVefiryActivityComponent.builder()
                .appComponent(getAppComponent())
                .productVefiryPresenterModule(new ProductVefiryPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        Intent intent = getIntent();
        isScan = intent.getIntExtra(Constant.ISVERIFY, 0); //1扫描成功，2为扫描不成功需手动输入验证，3 为产品录入
        initToolBar();
//        couponMesRel.setVisibility(View.GONE);
        couponCodeTextView.setText(Constants.SCAN_COUPON_ONLY_CODE);
        setCouponData();
        //1扫描成功，2为扫描不成功需手动输入验证
        if (isScan == 1) {
            setProductData();
            productCode = Constants.SCAN_PRODUCT_ONLY_CODE;
            productCodeTextView.setText(productCode);
            scanSuccessLayout.setVisibility(View.VISIBLE);
            scanFailLayout.setVisibility(View.GONE);

        } else if (isScan == 2) {
            scanFailLayout.setVisibility(View.VISIBLE);
            scanSuccessLayout.setVisibility(View.GONE);
            affirmBtn.setEnabled(false);
        } else if (isScan == Constant.ENTER_PRODUCT_SCAN) {
            scanFailLayout.setVisibility(View.VISIBLE);
            scanSuccessLayout.setVisibility(View.GONE);
            affirmBtn.setEnabled(false);
        }
        initPopWindow();

    }

    //设置优惠券数据
    private void setCouponData() {
        /** 测试代码 **/
        if(Constant.DemoF) {
//            couponImageView.setImageResource(R.mipmap.banner_2_img);
//            mDaijinquanText.setVisibility(View.GONE);
            couponCodeTextView.setText("1834 1465 2093 8922");
            return;
        }

        /** 测试代码结束 **/

//        String json = (String) mSharePreDate.getParam(Constants.KEY_DATA_SCAN_COUPON, "");
        String json = mDataManager.getSPData(Constants.KEY_DATA_SCAN_COUPON);
        VerifyCoupon verifyCoupon = VerifyCoupon.createInstanceByJson(json);
        if (verifyCoupon != null && verifyCoupon.model != null) {
            if (TextUtils.isEmpty(verifyCoupon.model.getDescription())) {
                msgTextview.setText("暂无");
            } else {
                msgTextview.setText(verifyCoupon.model.getDescription() + "每款产品只能使用一张优惠券，尽在购物时用于折扣货款，不可兑换现金或产品。");
            }
            explainLinLayout.setVisibility(View.VISIBLE);
            String url = ImageUrl.parseUrl(verifyCoupon.model.getImage());
            couponImageView.setImageURI(url);
        }
    }

    //设置产品券数据
    private void setProductData() {
        /** 测试代码 **/
        if(Constant.DemoF) {
            productNameTextView.setText("晶彩茶韵400ml塑料杯-炫酷黑");
            productPriceTextView.setText("￥89");
            productImageView.setImageResource(R.mipmap.order_prd_img);
//            productVerifyTextView.setText("您所验证的优惠券可以用于该产品 , 确认使用即完成验证 。");
            affirmBtn.setEnabled(true);

            return;
        }
        /** 测试代码结束 **/
        if(verifyProduct == null) {
//            String json = (String) mSharePreDate.getParam(Constants.KEY_DATA_SCAN_PRODUCT_JSON, "");
            String json = mDataManager.getSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON);
            verifyProduct = VerifyProduct.createInstanceByJson(json);
        }
        if (verifyProduct != null && verifyProduct.model != null) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dimen_120dp);
            String url = verifyProduct.model.getProduct_image();
            if(!url.isEmpty()) {
                url = ImageUrl.parseUrl(verifyProduct.model.getProduct_image());
                productImageView.setImageURI(url);
            } else {
                productImageView.setImageResource(R.mipmap.order_prd_img);
            }
            productCodeTextView.setText(productCode);
            productNameTextView.setText(verifyProduct.model.getProduct_name());
            productPriceTextView.setText("￥ " + String.format("%.2f", verifyProduct.model.getUnit_price()).toString());

            affirmBtn.setEnabled(true);

            /*isCanUse = verifyProduct.isCoupon_can_be_used();
            if (isCanUse) {
                productVerifyTextView.setText("您所验证的优惠券可以用于该产品 , 确认使用即完成验证 。");
                affirmBtn.setEnabled(true);
            } else {
                productVerifyTextView.setText(verifyProduct.getCant_be_used_reason());
                affirmBtn.setEnabled(false);
            }*/
        }
    }

    //初始化弹出框
    private void initPopWindow() {
        View view = getLayoutInflater().inflate(R.layout.dialog_affirm_vefiry, null, false);
        Button okBtn = (Button) view.findViewById(R.id.activity_productvefiry_dialog_btn);
        okPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okPopupWindow.isShowing()) {
                    okPopupWindow.dismiss();
                }
                ActivityManager.getInstance().exit();
            }
        });


    }

    @Override
    protected void requestData() {

    }

    @Override
    public void setErrorShow() {
        affirmBtn.setEnabled(false);
        productCodeEdit.setText(productCode);
        scanSuccessLayout.setVisibility(View.GONE);
        scanFailLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProductVerifyResult(VerifyProduct verifyProduct) {
        Constants.SCAN_PRODUCT_ONLY_CODE = productCode;
        setUIChange();
        if (verifyProduct.getModel() != null) {
            String url = verifyProduct.getModel().getProduct_image();
            if(!url.isEmpty()) {
                url = ImageUrl.parseUrl(verifyProduct.getModel().getProduct_image());
                productImageView.setImageURI(url);
            } else {
                productImageView.setImageResource(R.mipmap.order_prd_img);
            }
            productCodeTextView.setText(productCode);
            productNameTextView.setText(verifyProduct.model.getProduct_name());
            productPriceTextView.setText("￥ " + String.format("%.2f", verifyProduct.model.getUnit_price()).toString());

            affirmBtn.setEnabled(true);
        }
    }

    @Override
    public void setUseCouponResult() {
        okPopupWindow.setAnimationStyle(R.style.popup_window_anim_style);//设置动画样式
        okPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        isUse = true;
    }

//    @Override
//    public void ok(int requestCode, String json) {
//        hideDialog();
//
//        Constants.SCAN_PRODUCT_ONLY_CODE = productCode;
//
//        //验证产品唯一码
//        if (requestCode == Constants.REQUEST_CODE_CHECK_PRODUCT) {
//            verifyProduct = VerifyProduct.createInstanceByJson(json);
//            if (verifyProduct == null) {
//                toast(getString(R.string.system_error));
//                return;
//            } else if(!verifyProduct.success) {
//                toast(verifyProduct.message + "");
//                return;
//            } else if(!verifyProduct.model.isCoupon_can_be_used()) {
//                toast(verifyProduct.model.getCant_be_used_reason() + "");
//                return;
//            }
////            mSharePreDate.setParam(Constants.KEY_DATA_SCAN_PRODUCT_JSON, json);
////            mSharePreDate.setParam(Constants.FANS_MEMBER_ID, verifyProduct.model.getMembers().getMember_id());
//            mDataManager.saveSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON, json);
//            mDataManager.saveSPObjectData(Constants.FANS_MEMBER_ID, verifyProduct.model.getMembers().getMember_id());
//            setUIChange();
//            setProductData();
//
//        }
//        //使用优惠券
//        else if (requestCode == Constants.REQUEST_CODE_USE_COUPON) {
//            ResponseHeader mHeader = ResponseHeader.createInstanceByJson(json);
//            if (mHeader == null) {
//                toast(getString(R.string.system_error));
//                return;
//            }
//            if(!mHeader.isSuccess()){
//                toast(mHeader.getMessage());
//                return;
//            }
//            okPopupWindow.setAnimationStyle(R.style.popup_window_anim_style);//设置动画样式
//            okPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
//            toast(mHeader.getMessage());
//            isUse = true;
//        }
//    }
//
//    @Override
//    public boolean error(int requestCode, ResponseBean errorCode) {
//        hideDialog();
//
//        //验证产品唯一码
//        if (requestCode == Constants.REQUEST_CODE_CHECK_PRODUCT) {
//            affirmBtn.setEnabled(false);
//            productCodeEdit.setText(productCode);
//            scanSuccessLayout.setVisibility(View.GONE);
//            scanFailLayout.setVisibility(View.VISIBLE);
//        } else if(requestCode == Constants.REQUEST_CODE_USE_COUPON) {
//            toast(errorCode.getMessage() + "");
//        }
//        return false;
//    }


    private void initToolBar() {
        if(isScan == 1) {
            setUIChange();
        } else if(isScan == 2) {
            mRightNext.setVisibility(View.VISIBLE);
            mRightText.setText(getResources().getString(R.string.verify));
            mTitle.setText(getResources().getString(R.string.product_verification));
            scancouponSuccessLayout.setVisibility(View.GONE);
            scanSuccessLayout.setVisibility(View.GONE);
            scanFailLayout.setVisibility(View.VISIBLE);
            mSureNextLinearLayout.setVisibility(View.GONE);
        } else if (isScan == Constant.ENTER_PRODUCT_SCAN) {
            mRightNext.setVisibility(View.VISIBLE);
            mRightText.setText(getResources().getString(R.string.verify));
            mTitle.setText(getResources().getString(R.string.product_verification));
            scancouponSuccessLayout.setVisibility(View.GONE);
            scanSuccessLayout.setVisibility(View.GONE);
            scanFailLayout.setVisibility(View.VISIBLE);
            mSureNextLinearLayout.setVisibility(View.GONE);
        }

    }

    private void setUIChange() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.youhuiquan_verification));
        scancouponSuccessLayout.setVisibility(View.VISIBLE);
        //默认展开
        Animation alphaAnimation = AnimationUtils.loadAnimation(ProductVefiryActivity.this, R.anim.right_rotate);
        couponImgBtn.startAnimation(alphaAnimation);
        scanSuccessLayout.setVisibility(View.VISIBLE);
        scanFailLayout.setVisibility(View.GONE);
        mSureNextLinearLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.left_back, R.id.activity_couponvefiry_imagebutton, R.id.activity_productvefiry_affirm_btn,
    R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ActivityManager.getInstance().removeActivity(this);
                finish();
                break;
            case R.id.activity_couponvefiry_imagebutton:
                Animation alphaAnimation = null;
                if (couponMesRel.getVisibility() == View.VISIBLE) {
                    alphaAnimation = AnimationUtils.loadAnimation(ProductVefiryActivity.this, R.anim.left_rotate);
                    couponImgBtn.startAnimation(alphaAnimation);
                    couponMesRel.setVisibility(View.GONE);
                    lineView.setVisibility(View.GONE);
                    explainLinLayout.setVisibility(View.GONE);
                } else if (couponMesRel.getVisibility() == View.GONE) {

                    alphaAnimation = AnimationUtils.loadAnimation(ProductVefiryActivity.this, R.anim.right_rotate);
                    couponImgBtn.startAnimation(alphaAnimation);
                    couponMesRel.setVisibility(View.VISIBLE);
                    lineView.setVisibility(View.VISIBLE);
                    explainLinLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_productvefiry_affirm_btn:
                //粉丝注册
                if(Constant.DemoF) {
                    showNotExistDialog();
                } else {
                    if(isFansRegister()) {
                        Intent intent = new Intent(this, RegisterChooseActivity.class);
                        startActivity(intent);
                    } else {
                        //联网确认使用
//                        asyncLoading(Constants.REQUEST_CODE_USE_COUPON, productCode);
                        mPresenter.useProductCoupon(Constants.SCAN_COUPON_ONLY_CODE, productCode);
                        showDialog();
                    }
                }
                break;
            case R.id.next:
                if (productCodeEdit.getText() != null) {
                    productCode = productCodeEdit.getText().toString();
                }
                if (!TextUtils.isEmpty(productCode)) {
                    //强制关闭软键盘
                    View view1 = getWindow().peekDecorView();
                    if (view1 != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

//                        productCodeTextView.setText(productCode);
                    //联网验证
//                        asyncLoadingProduct(Constants.REQUEST_CODE_CHECK_PRODUCT, productCode);
                    mPresenter.verifyProductCode(Constants.SCAN_COUPON_ONLY_CODE, productCode);
                    showDialog();

                } else {
                    toast("请输入产品唯一码");
                }
                break;
        }

    }

    //判断是不是粉丝
    private boolean isFansRegister() {
        if(verifyProduct == null) {
//            String json = (String) mSharePreDate.getParam(Constants.KEY_DATA_SCAN_PRODUCT_JSON, "");
            String json = mDataManager.getSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON);
            verifyProduct = VerifyProduct.createInstanceByJson(json);
        }

        if(verifyProduct != null) {
            if(verifyProduct.model.getMembers().getGroup_id() == 2) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 异步加载数据数据,完成请求的url与筛选条件的添加，把请求添加到请求队列,使用优惠券
     */
//    private void asyncLoading(int requestCode, String code) {
//
//        Map<String, String> params = new HashMap<>();
//        params.put("qrCode", Constants.SCAN_COUPON_ONLY_CODE);
//        params.put("uniqueCode", code);
//        mTupVolley.post(requestCode, ServerURL.USE_COUPONS, params, this, this, headerparams);
//    }

    //修改产品唯一码
    public void modifyProductClick(View view) {
        if (productCodeTextView.getText().toString().equals(productCode)) {
            toast("产品唯一码未修改");
            return;
        }
        if (productCodeTextView.getText() != null) {
            productCode = productCodeTextView.getText().toString();
        }
        if (!android.text.TextUtils.isEmpty(productCode)) {
            //强制关闭软键盘
            View view1 = getWindow().peekDecorView();
            if (view1 != null) {
                InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            productCodeTextView.setText(productCode);
            //联网验证
            mPresenter.verifyProductCode(Constants.SCAN_COUPON_ONLY_CODE, productCode);
            showDialog();

        } else {
            toast("请输入产品唯一码");
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (okPopupWindow.isShowing()) {
            okPopupWindow.dismiss();
        }
        if (isUse) {
            ActivityManager.getInstance().exit();
        } else {
            ActivityManager.getInstance().removeActivity(this);
            finish();
        }

    }

    private void showNotExistDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setImageResource(R.mipmap.pop_success_ic);
        customDialog.setImageShow(true);
        customDialog.setmessage(getResources().getString(R.string.coupon_not_exist));
        customDialog.setsureText(getResources().getString(R.string.sure));
        customDialog.setCancelable(false);
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constant.DemoTest) {
                    Intent intent = new Intent(view.getContext(), RegisterChooseActivity.class);
                    startActivity(intent);
                }
            }
        });
        customDialog.build().show();
    }

}
