package com.tupperware.biz.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dhunter.common.qrcode.assit.BeepManager;
import com.android.dhunter.common.qrcode.core.QRCodeView;
import com.android.dhunter.common.qrcode.zxing.ZXingView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.base.BaseApplication;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.config.Constants;
import com.tupperware.biz.entity.VerifyCoupon;
import com.tupperware.biz.entity.VerifyProduct;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.component.DaggerScanCouponActivityComponent;
import com.tupperware.biz.ui.contract.ScanCouponContract;
import com.tupperware.biz.ui.module.ScanCouponPresenterModule;
import com.tupperware.biz.ui.presenter.ScanCouponPresenter;
import com.tupperware.biz.utils.ActivityManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/15.
 */

public class ScanCouponActivity extends BaseActivity implements QRCodeView.Delegate,
        ScanCouponContract.View {

    private static final String TAG = "ScanCouponActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.activity_scancoupon_tip)
    TextView mScanTipText;
    @BindView(R.id.activity_can_not_scan)
    TextView mNoScanText;
    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;
    @BindView(R.id.capture_containter)
    RelativeLayout scanContainer;

//    private QRCodeView mQRCodeView;
//    private TextView mStart;
//    private TextView mStop;

    //判断这个页面时扫描优惠券还是扫描产品唯一码
    private String flag;

    private PopupWindow okPopupWindow;
    private TextView errorMesTextView;
    private BeepManager beepManager;
    @Inject
    ScanCouponPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //使屏幕不暗、不关闭
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        initPopWindow();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_scan;
    }

    @Override
    protected void initLayout() {
        checkCameraPermission(this);
        ActivityManager.getInstance().addActivity(this);
        flag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        DaggerScanCouponActivityComponent.builder()
                .appComponent(getAppComponent())
                .scanCouponPresenterModule(new ScanCouponPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mQRCodeView.setDelegate(this);
        beepManager = new BeepManager(this, true, true); //后面两个参数表示允许响一声，允许震动
        mRightNext.setVisibility(View.GONE);
        if(Constant.COUPON_SCAN.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.coupon_verification));
            mScanTipText.setText(Html.fromHtml("把 <b>专享礼券</b>二维码放入扫描框内 , 即可自动扫描进行验证"));
        } else if(Constant.PRODUCT_SCAN.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.product_verification));
            mScanTipText.setText(Html.fromHtml("把 <b>产品唯一码</b> 的二维码放入扫描框内 , 即可自动扫描进行验证"));
        } else if(Constant.ENTER_PRODUCT.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.scan_code));
            mScanTipText.setText(Html.fromHtml("把 <b>产品唯一码</b> 的二维码放入扫描框内 , 即可自动扫描进行验证"));
        } else if(Constant.WATER_SAFE.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.water_safe_mark));
            mScanTipText.setText(Html.fromHtml("请会员出示 <b>微信二维码</b> 进行预约登记"));
        } else if(Constant.PERSONAL_TAILOR.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.personal_tailor_mark));
            mScanTipText.setText(Html.fromHtml("请会员出示 <b>微信二维码</b> 进行预约登记"));
        } else if(Constant.CARNIVAL.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.carnival_mark));
            mScanTipText.setText(Html.fromHtml("请会员出示 <b>微信二维码</b> 进行预约登记"));
        }

        mNoScanText.setText(Html.fromHtml("<font color=#ffffff>扫描不到？ 进入</font><u><font color=#ff7000>人工验证</font></u>"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        beepManager.updatePrefs();
    }

    //初始化弹出框
    private void initPopWindow() {
        View view = getLayoutInflater().inflate(R.layout.dialog_scan_error, null, false);
        errorMesTextView = (TextView) view.findViewById(R.id.activity_scan_error_mes);
        Button okBtn = (Button) view.findViewById(R.id.activity_scan_error_dialog_btn);
        okPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出框获取焦点，监听返回按钮

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okPopupWindow.isShowing()) {
                    okPopupWindow.dismiss();
                    //连续扫描，不发送此消息扫描一次结束后就不能再次扫描
                    mQRCodeView.startSpotDelay(500);
//                    handler.sendEmptyMessage(R.id.restart_preview);
                }
            }
        });
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
//        mQRCodeView.startSpotDelay(500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beepManager.close();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        toast("扫描结果" + result);
        beepManager.playBeepSoundAndVibrate();
        vibrate();
        //根据扫描的结果判断扫描的是那种二维码
        if (Constant.COUPON_SCAN.equals(flag)) {
            //设置扫描的优惠唯一码
            Constants.SCAN_COUPON_ONLY_CODE = result;
            //联网
//            asyncLoading(Constants.REQUEST_CODE_CHECK_COUPON, result);
            showDialog();
            mPresenter.checkCoupon(result);
        } else if (Constant.PRODUCT_SCAN.equals(flag)) {
            String productCode = result;
//            String[] splits = productCode.split("/");
//            productCode = splits[splits.length - 1];
            //设置扫描的产品唯一码
            Constants.SCAN_PRODUCT_ONLY_CODE = productCode;
            //联网
//            asyncLoading(Constants.REQUEST_CODE_CHECK_PRODUCT, productCode);
            showDialog();
            mPresenter.verifyProductCode(Constants.SCAN_COUPON_ONLY_CODE, productCode);
        }
        mQRCodeView.stopSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        toast("打开相机出错");
    }

    @OnClick({R.id.left_back, R.id.activity_can_not_scan, R.id.zxingview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ActivityManager.getInstance().removeActivity(this);
                finish();
                break;
            case R.id.activity_can_not_scan:
                if(Constant.COUPON_SCAN.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, CouponVerifyActivity.class, Constant.CAN_NOT_SCAN);
                } else if(Constant.PRODUCT_SCAN.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, ProductVefiryActivity.class, Constant.CAN_NOT_SCAN);
                } else if(Constant.ENTER_PRODUCT.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, ProductVefiryActivity.class, Constant.ENTER_PRODUCT_SCAN);
                } else if(Constant.WATER_SAFE.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, ReservationMarkActivity.class, Constant.WATER_SAFE);
                } else if(Constant.PERSONAL_TAILOR.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, ReservationMarkActivity.class, Constant.PERSONAL_TAILOR);
                } else if(Constant.CARNIVAL.equals(flag)) {
                    jumpFromTo(ScanCouponActivity.this, ReservationMarkActivity.class, Constant.CARNIVAL);
                }
                break;
//            case R.id.activity_scancoupon_tip:
//                mQRCodeView.startSpot();
//                break;
            case R.id.zxingview:
                mQRCodeView.startSpotDelay(0);
                break;
        }
    }

    /**
     * 异步加载数据数据,完成请求的url与筛选条件的添加，把请求添加到请求队列
     */
//    private void asyncLoading(int requestCode, String code) {
//        Map<String, String> params = new HashMap<>();
//        String url = ServerURL.VEFIRY_COUPON;
//        switch (requestCode) {
//            case Constants.REQUEST_CODE_CHECK_COUPON:
//                params.put("qrCode", code);
//                url = ServerURL.VEFIRY_COUPON;
//                break;
//            case Constants.REQUEST_CODE_CHECK_PRODUCT:
//                params.put("qrCode", Constants.SCAN_COUPON_ONLY_CODE);
//                params.put("uniqueCode", code);
//                url = ServerURL.VEFIRY_PRODUCT;
//                break;
//        }
//        mTupVolley.post(requestCode, url, params, this, this, headerparams);
//    }

    /**
     * isScan表示是否扫描成功
     */
    private void jumpFromTo(Context context, Class<?> _cls, int isScan) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ISVERIFY, isScan);
        startActivity(intent);
    }

    /**
     * fromFlag 表示从什么页面跳转过去的
     */
    private void jumpFromTo(Context context, Class<?> _cls, String fromFlag) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM, fromFlag);
        startActivity(intent);
    }

    @Override
    public void setErrorShow(String msg) {
        errorMesTextView.setText(msg);
        okPopupWindow.setAnimationStyle(R.style.popup_window_anim_style);//设置动画样式
        okPopupWindow.showAtLocation(scanContainer, Gravity.CENTER, 0, 0);
    }

    @Override
    public void setCouponCheckResult(VerifyCoupon verifyCoupon) {
        jumpFromTo(ScanCouponActivity.this, CouponVerifyActivity.class, Constant.AFTER_SCAN_SUCCESS);
    }

    @Override
    public void setProductVerifyResult(VerifyProduct verifyProduct) {
        jumpFromTo(ScanCouponActivity.this, ProductVefiryActivity.class, Constant.AFTER_SCAN_SUCCESS);
    }

//    @Override
//    public boolean error(int requestCode, ResponseBean errorCode) {
//        hideDialog();
//        mQRCodeView.startSpotDelay(500);
//        errorMesTextView.setText(errorCode.getMessage());
//        okPopupWindow.setAnimationStyle(R.style.popup_window_anim_style);//设置动画样式
//        okPopupWindow.showAtLocation(scanContainer, Gravity.CENTER, 0, 0);
//
//        return true;
//    }
//
//    @Override
//    public void ok(int requestCode, String json) {
//        hideDialog();
//        LogF.v(TAG,"--------------:"+json);
//
//        switch (requestCode) {
//            case Constants.REQUEST_CODE_CHECK_COUPON:
//                VerifyCoupon verifyCoupon = VerifyCoupon.createInstanceByJson(json);
//                if (verifyCoupon == null) {
//                    toast(getString(R.string.system_error));
//                    mQRCodeView.startSpotDelay(500);
//                    return;
//                } else if(!verifyCoupon.success) {
//                    toast(verifyCoupon.message + "");
//                    mQRCodeView.startSpotDelay(500);
//                    return;
//                }
////                mSharePreDate.setParam(Constants.KEY_DATA_SCAN_COUPON, json);
//                mDataManager.saveSPData(Constants.KEY_DATA_SCAN_COUPON, json);
//                jumpFromTo(ScanCouponActivity.this, CouponVerifyActivity.class, Constant.AFTER_SCAN_SUCCESS);
//                break;
//            case Constants.REQUEST_CODE_CHECK_PRODUCT:
//                VerifyProduct verifyProduct = VerifyProduct.createInstanceByJson(json);
//                if (verifyProduct == null) {
//                    toast(getString(R.string.system_error));
//                    mQRCodeView.startSpotDelay(500);
//                    return;
//                } else if(!verifyProduct.success) {
//                    toast(verifyProduct.message + "");
//                    mQRCodeView.startSpotDelay(500);
//                    return;
//                } else if(!verifyProduct.model.isCoupon_can_be_used()) {
//                    toast(verifyProduct.model.getCant_be_used_reason() + "");
//                    mQRCodeView.startSpotDelay(500);
//                    return;
//                }
//                mDataManager.saveSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON, json);
//                mDataManager.saveSPObjectData(Constants.FANS_MEMBER_ID, verifyProduct.model.getMembers().getMember_id());
////                mSharePreDate.setParam(Constants.KEY_DATA_SCAN_PRODUCT, verifyProduct);
//                jumpFromTo(ScanCouponActivity.this, ProductVefiryActivity.class, Constant.AFTER_SCAN_SUCCESS);
//                break;
//        }
//    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

    private void checkCameraPermission(Context context) {
        if(Build.VERSION.SDK_INT>=23) {
            if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                Toast.makeText(context,"请打开使用摄像头权限",Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
                return;
            }
        }
    }

}
