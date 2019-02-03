package com.tupperware.biz.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.qrcode.zxing.QRCodeEncoder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.utils.RxAsyncHelper;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Func1;

/**
 * Created by dhunter on 2018/3/27.
 */

public class DevelopMemberActivity extends BaseActivity {

    private static final String TAG = "DevelopMemberActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.store_qr_icon)
    SimpleDraweeView mQrIcon;
    @BindView(R.id.store_num)
    TextView mStoreNum;
    @BindView(R.id.register_base_info)
    TextView mRegisterInfo;
    @BindView(R.id.not_register)
    TextView mRegisterCancel;

    private String fromTag;
    private Bitmap mQRCodeBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_develop_member;
    }

    @TargetApi(16)
    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mLeftBack.setVisibility(View.GONE);
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.develop_new_member));
        RxAsyncHelper helper = new RxAsyncHelper("");
        helper.runInThread(new Func1() {
            @Override
            public Bitmap call(Object o) {
                /**
                 * 同步创建指定前景色、指定背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
                 *
                 * @param content         要生成的二维码图片内容
                 * @param size            图片宽高，单位为px
                 * @param foregroundColor 二维码图片的前景色
                 * @param backgroundColor 二维码图片的背景色
                 * @param logo            二维码图片的logo
                 */
                Bitmap bitmaplogo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(userId, ScreenUtil.dip2px(mContext, R.dimen.dimen_164dp),
                        Color.parseColor("#ff7000"), bitmaplogo);
                return bitmap;
            }
        }).runOnMainThread(new Func1<Bitmap,String>() {
            @Override
            public String call(Bitmap bitmap) {
                mQrIcon.setBackground(new BitmapDrawable(mContext.getResources(),bitmap));
                return null;
            }
        }).subscribe();
//        try{
//            Bitmap bitmaplogo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo);
//            mQRCodeBitmap = EncodingHandler.createQRImage(this, storeId, bitmaplogo);
//        } catch (Exception e) {
//
//        }
//        mQrIcon.setBackground(new BitmapDrawable(this.getResources(),mQRCodeBitmap));
        mStoreNum.setText(userId);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.register_base_info, R.id.not_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_base_info:
                Intent intent = new Intent(this, DevProductSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.not_register:
                ActivityManager.getInstance().exit();
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
