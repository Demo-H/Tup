package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.utils.ImageUrl;
import com.tupperware.huishengyi.widget.CustomDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/15.
 * 验证优惠券页面
 */

public class CouponVerifyActivity extends BaseActivity implements TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener {

    private static final String TAG = "CouponVerifyActivity";
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

    @BindView(R.id.activity_couponvefiry_code_edit)
    EditText codeEditText;
    @BindView(R.id.activity_couponvefiry_scansuccess_layout)
    LinearLayout scanSuccessLayout;
    @BindView(R.id.activity_couponvefiry_scanfail_layout)
    RelativeLayout scanFailLayout;
    @BindView(R.id.activity_couponvefiry_imagebutton)
    ImageButton imageVefiryButton;
    @BindView(R.id.activity_couponvefiry_lin)
    RelativeLayout explainLinLayout;
    @BindView(R.id.activity_couponvefiry_next_linearlayout)
    LinearLayout mNextLinearLayout;
    @BindView(R.id.activity_couponvefiry_next_btn)
    Button nextButton;
    @BindView(R.id.activity_couponvefiry_code_text)
    TextView codeTextView;
    @BindView(R.id.activity_couponvefiry_image)
    SimpleDraweeView couponImageView;
    @BindView(R.id.activity_couponvefiry_msg)
    TextView msgTextview;
    @BindView(R.id.no_coupon_image_text)
    TextView mNoimageText;
    @BindView(R.id.activity_couponvefiry_rel)
    RelativeLayout couponMesRel;
    @BindView(R.id.separation_line)
    View lineView;

    private int isScan = 0;
    private String code;//惠金币或优惠券唯一码
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_verify);
        //将相关activity暂时保存，等到扫描完成后再一次性finish

        ActivityManager.getInstance().addActivity(this);
        mContext = this;
        initLayout();

    }


    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        Intent intent = getIntent();
        isScan = intent.getIntExtra(Constant.ISVERIFY, 0); //1扫描成功，2为扫描不成功需手动输入验证
        initToolBar();
        if(isScan == Constant.AFTER_SCAN_SUCCESS) {
            //获得唯一码
            code = Constants.SCAN_COUPON_ONLY_CODE;
            codeTextView.setText(code);
            scanSuccessLayout.setVisibility(View.VISIBLE);
            scanFailLayout.setVisibility(View.GONE);
            mRightNext.setVisibility(View.GONE);
            imageVefiryButton.setVisibility(View.VISIBLE);
            initLayoutData();
        } else if(isScan == Constant.CAN_NOT_SCAN) {
            scanFailLayout.setVisibility(View.VISIBLE);
            scanSuccessLayout.setVisibility(View.GONE);
            mNextLinearLayout.setVisibility(View.GONE);
//            nextButton.setVisibility(View.GONE);
//            nextButton.setEnabled(true);
        }
    }

    @Override
    protected void initLayoutData() {
        setCouponData();
    }

    private void setCouponData() {
        if(Constant.DemoF) {
            codeTextView.setText("1834 1465 2093 8922");
            return;
        }
        String json = (String) mSharePreDate.getParam(Constants.KEY_DATA_SCAN_COUPON, "");
        VerifyCoupon verifyCoupon = VerifyCoupon.createInstanceByJson(json);
        if (verifyCoupon != null && verifyCoupon.model != null) {
            if (TextUtils.isEmpty(verifyCoupon.model.getDescription())) {
                msgTextview.setText("暂无");
//            } else if("INTEGRAL".equals(verifyCoupon.model.getType())){
//                msgTextview.setText("您正在使用" + verifyCoupon.model.getDescription() + "换购" + "\n" +
//                        verifyCoupon.model.getProduct_code() + " " + verifyCoupon.model.getProduct_name());
            } else {
                msgTextview.setText(Html.fromHtml("<font color=#43484b>说明：</font>" + verifyCoupon.model.getDescription() + "每款产品只能使用一张优惠券，尽在购物时用于折扣货款，不可兑换现金或产品。") );
            }

            explainLinLayout.setVisibility(View.VISIBLE);

//            final int width = MainApplication.ScreenWidth;

//            if("INTEGRAL".equals(verifyCoupon.model.getType())){
//                couponImageView.setImageResource(R.mipmap.banner_2_img);
//            } else {
//                couponImageView.setImageResource(R.mipmap.banner_3_img);
//            }
            String url = ImageUrl.parseUrl(verifyCoupon.model.getImage());
            couponImageView.setImageURI(url);

        }
    }

    /**
     * 异步加载数据数据,完成请求的url与筛选条件的添加，把请求添加到请求队列
     */
    private void asyncLoading(int requestCode) {
        Map<String, String> params = new HashMap<>();
        params.put("qrCode", code);
        mTupVolley.post(requestCode, ServerURL.VEFIRY_COUPON, params, this, this, headerparams);
    }

    @Override
    public void ok(int requestCode, String json) {
        hideDialog();

        Constants.SCAN_COUPON_ONLY_CODE = code;

        VerifyCoupon verifyCoupon = VerifyCoupon.createInstanceByJson(json);
        if (verifyCoupon == null) {
            toast(getString(R.string.system_error));
            return;
        } else if(!verifyCoupon.success) {
            toast(verifyCoupon.message + "");
            return;
        }

        mSharePreDate.setParam(Constants.KEY_DATA_SCAN_COUPON, json);
        initLayoutData();
        setUIchange();
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        showNotExistDialog();
        return false;
    }

    private void initToolBar() {
        mRightText.setText(getResources().getString(R.string.verify));
        mTitle.setText(getResources().getString(R.string.coupon_verification));
        imageVefiryButton.setVisibility(View.GONE);
    }

    @OnClick({R.id.left_back, R.id.next, R.id.activity_couponvefiry_imagebutton, R.id.activity_couponvefiry_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ActivityManager.getInstance().removeActivity(this);
                finish();
                break;
            case R.id.next:
                if(Constant.DemoF) {
                    if(codeEditText.getText().toString().isEmpty()) {
                        //测试券码不存在
                        showNotExistDialog();
                    } else {
                        View view1 = getWindow().peekDecorView();
                        if (view1 != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        setUIchange();
                        setCouponData();
                    }
                } else {
                    if (codeEditText.getText() != null) {
                        code = codeEditText.getText().toString();
                    }
                    if (!android.text.TextUtils.isEmpty(code)) {
                        //强制关闭软键盘
                        View view1 = getWindow().peekDecorView();
                        if (view1 != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        codeTextView.setText(code);
                        //联网验证
                        asyncLoading(Constants.REQUEST_CODE_CHECK_COUPON);
                        showDialog();
                    } else {
                        toast("请输入优惠券/积分码 !");
                    }
                }
                break;
            case R.id.activity_couponvefiry_imagebutton:
                Animation alphaAnimation = null;
                if (couponMesRel.getVisibility() == View.VISIBLE) {

                    alphaAnimation = AnimationUtils.loadAnimation(CouponVerifyActivity.this, R.anim.left_rotate);
                    imageVefiryButton.startAnimation(alphaAnimation);
                    couponMesRel.setVisibility(View.GONE);
                    lineView.setVisibility(View.GONE);
                    explainLinLayout.setVisibility(View.GONE);
                } else if (couponMesRel.getVisibility() == View.GONE) {

                    alphaAnimation = AnimationUtils.loadAnimation(CouponVerifyActivity.this, R.anim.right_rotate);
                    imageVefiryButton.startAnimation(alphaAnimation);
                    couponMesRel.setVisibility(View.VISIBLE);
                    lineView.setVisibility(View.VISIBLE);
                    explainLinLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_couponvefiry_next_btn:
                Intent intent = new Intent();
                intent.setClass(CouponVerifyActivity.this, ScanCouponActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.PRODUCT_SCAN);
                startActivity(intent);
                break;
        }
    }

    private void setUIchange() {
        mRightNext.setVisibility(View.GONE);
        imageVefiryButton.setVisibility(View.VISIBLE);
        Animation alphaAnimation = AnimationUtils.loadAnimation(CouponVerifyActivity.this, R.anim.right_rotate);
        imageVefiryButton.startAnimation(alphaAnimation);
        mTitle.setText(getResources().getString(R.string.youhuiquan_verification));
        scanSuccessLayout.setVisibility(View.VISIBLE);
        scanFailLayout.setVisibility(View.GONE);
//        nextButton.setEnabled(true);
//        nextButton.setVisibility(View.VISIBLE);
        mNextLinearLayout.setVisibility(View.VISIBLE);
    }

    private void showNotExistDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setImageResource(R.mipmap.pop_failed_ic);
        customDialog.setImageShow(true);
        customDialog.setmessage(getResources().getString(R.string.coupon_not_exist));
        customDialog.setsureText(getResources().getString(R.string.sure));
        customDialog.setCancelable(false);
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        customDialog.build().show();
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

}
