package com.tupperware.huishengyi.ui.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.qrcode.zxing.QRCodeEncoder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.PersonalQrBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.component.DaggerPersonalQrActivityComponent;
import com.tupperware.huishengyi.ui.contract.PersonalQrContract;
import com.tupperware.huishengyi.ui.module.PersonalQrPresenterModule;
import com.tupperware.huishengyi.ui.presenter.PersonalQrPresenter;
import com.tupperware.huishengyi.utils.BitmapUtils;
import com.tupperware.huishengyi.utils.RxAsyncHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Func1;

/**
 * Created by dhunter on 2018/3/19.
 */

public class PersonalQrActivity extends BaseActivity implements PersonalQrContract.View {

    private static final String TAG = "PersonalQrActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightImage;

    @BindView(R.id.personal_img)
    SimpleDraweeView mStoreImage;
    @BindView(R.id.group_qr_name)
    TextView mStoreTitle;
    @BindView(R.id.group_qr_code)
    TextView mStoreCode;
    @BindView(R.id.personal_qr_icon)
    SimpleDraweeView mQrIcon;
    @BindView(R.id.group_qr_date)
    TextView mQrTip;
    @BindView(R.id.personal_qr_layout)
    RelativeLayout mQrRelativeLayout;

    private TextView mSaveImage;
    private TextView mCancelImage;
    private Dialog dialog;
    private View inflate;

    private String storeId;
    private String createFrom;
    @Inject
    PersonalQrPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_qrcode;
    }

    @TargetApi(16)
    @Override
    protected void initLayout() {
        createFrom = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        initToolBar();
//        if(Constant.DemoTest) {
//            storeId = "200001";
//        } else {
            storeId = storeCode;
//        }
        DaggerPersonalQrActivityComponent.builder()
                .appComponent(getAppComponent())
                .personalQrPresenterModule(new PersonalQrPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    @Override
    protected void requestData() {
        mPresenter.getPersonQrData(storeId);
    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        if(createFrom.equals(Constant.HOME)) {
            mTitle.setText(getResources().getString(R.string.personal_store));
        } else if(createFrom.equals(Constant.PERSONAL_INFO)) {
            mTitle.setText(getResources().getString(R.string.personal_qr_code));
        }
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.shop_more_ic);
    }

    private void showQrImage(final String imageurl) {
        RxAsyncHelper helper = new RxAsyncHelper("");
        helper.runInThread(new Func1() {
            @Override
            public Object call(Object o) {
                Bitmap bitmaplogo = BitmapUtils.getBitMap(imageurl);
                return bitmaplogo;
            }
        }).runInThread(new Func1<Bitmap,Bitmap>() {
            @Override
            public Bitmap call(Bitmap bitmaplogo) {
                /**
                 * 同步创建指定前景色、白色背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
                 *
                 * @param content         要生成的二维码图片内容
                 * @param size            图片宽高，单位为px
                 * @param foregroundColor 二维码图片的前景色
                 * @param logo            二维码图片的logo
                 */
                int margin =  getResources().getDimensionPixelSize(R.dimen.app_title);
//                Bitmap bitmaplogo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(storeId, ScreenUtil.dip2px(mContext, ScreenUtil.getWidth(mContext) - margin*2),
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
    }


    private void showQrImage() {
        RxAsyncHelper helper = new RxAsyncHelper("");
        helper.runInThread(new Func1() {
            @Override
            public Bitmap call(Object o) {
                /**
                 * 同步创建指定前景色、白色背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
                 *
                 * @param content         要生成的二维码图片内容
                 * @param size            图片宽高，单位为px
                 * @param foregroundColor 二维码图片的前景色
                 * @param logo            二维码图片的logo
                 */
                int margin =  getResources().getDimensionPixelSize(R.dimen.app_title);
                Bitmap bitmaplogo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(storeId, ScreenUtil.dip2px(mContext, ScreenUtil.getWidth(mContext) - margin*2),
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
    }

    private void showChooseDialog() {
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_choose_layout, null);
        mSaveImage = (TextView) inflate.findViewById(R.id.saveImage);
        mCancelImage = (TextView) inflate.findViewById(R.id.cancel_save);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
        mSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveQrImage(mQrRelativeLayout, mContext);
                dialog.dismiss();
            }
        });
        mCancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();//显示对话框
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                showChooseDialog();
                break;
        }
    }

    @Override
    public void setPersonQrData(PersonalQrBean mBean) {
//        mStoreImage.setImageURI(mBean.model.logo);
        mStoreTitle.setText(mBean.getModel().getName());
        mStoreCode.setText(mBean.getModel().getCode());
//        showQrImage(mBean.model.logo);
        mQrIcon.setImageURI(mBean.getModel().getLogo());
    }

    @Override
    public void setNetErrorView() {
        mQrTip.setText(getResources().getString(R.string.scan_and_scan_error));
    }

    @Override
    public void setNormalView() {
        mQrTip.setText(getResources().getString(R.string.scan_and_scan_tip));
    }

    @Override
    public void setEmptyView() {
        mQrTip.setText(getResources().getString(R.string.scan_and_scan_empty));
        showQrImage();
    }
}
