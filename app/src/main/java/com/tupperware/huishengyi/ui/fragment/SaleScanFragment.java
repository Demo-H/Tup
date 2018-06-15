package com.tupperware.huishengyi.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.qrcode.assit.BeepManager;
import com.android.dhunter.common.qrcode.core.QRCodeView;
import com.android.dhunter.common.qrcode.zxing.ZXingView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.component.DaggerSaleScanFragmentComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.interfaces.ISaleEnterListener;
import com.tupperware.huishengyi.module.SaleScanPresenterModule;
import com.tupperware.huishengyi.ui.contract.SaleScanContract;
import com.tupperware.huishengyi.ui.presenter.SaleScanPresenter;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.utils.StringUtils;
import com.tupperware.huishengyi.utils.data.ProductHistoryProvider;
import com.tupperware.huishengyi.utils.data.ProductProvider;
import com.tupperware.huishengyi.widget.FunctionDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by dhunter on 2018/5/25.
 * 销售录入扫描
 */

public class SaleScanFragment extends BaseFragment implements QRCodeView.Delegate, SaleScanContract.View {

    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;
    @BindView(R.id.scan_tip)
    TextView mScanTip;
    @BindView(R.id.enter_by_hand)
    TextView mEnterHand;
    @BindView(R.id.capture_containter)
    RelativeLayout scanContainer;

    private View rootView;
    private BeepManager beepManager;
    private ISaleEnterListener mEnterListener;
    private Context mContext;
    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private TextView mEnterCount; //Activity上的清单个数
    private String selectDate;
    private boolean isHistory = false;

    @Inject
    SaleScanPresenter mPresenter;

    public static SaleScanFragment newInstance(Bundle bundle) {
        SaleScanFragment fragment = new SaleScanFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectDate = bundle.getString(Constant.SELECT_DATE);
        } else {
            selectDate = new DateFormatter().timestampToDate(System.currentTimeMillis());
        }
        isHistory = DateFormatter.isHistory(selectDate);
        toast(selectDate);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mContext = getContext();
        if(isHistory) {
            mHistoryInstance = ProductHistoryProvider.getInstance(mContext.getApplicationContext());
        } else {
            mInstance = ProductProvider.getInstance(mContext.getApplicationContext());
        }
        initLayout();
        initLayoutData();
        return rootView;
    }

    @Override
    public void initLayout() {
        mQRCodeView.setDelegate(this);
        beepManager = new BeepManager(getActivity(), true, true); //后面两个参数表示允许响一声，允许震动
        mScanTip.setText(Html.fromHtml("请扫描 <b>产品条形码</b> 进行识别"));
        DaggerSaleScanFragmentComponent.builder()
                .appComponent(getAppComponent())
                .saleScanPresenterModule(new SaleScanPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        refreshCount();
    }



    @Override
    public void initLayoutData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sale_scan;
    }

    @Override
    public void onResume() {
        super.onResume();
        beepManager.updatePrefs();
    }

    @Override
    public void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
//        mQRCodeView.startSpot();
        mQRCodeView.startSpotDelay(500);
    }

    @Override
    public void onPause() {
        super.onPause();
        beepManager.close();
    }

    public void setUserVisible() {
        if(mQRCodeView != null) {
            mQRCodeView.startCamera();
            mQRCodeView.showScanRect();
            mQRCodeView.startSpotDelay(500);
        }
    }

    public void setUserHint() {
        if(mQRCodeView != null) {
            mQRCodeView.stopSpot();
            mQRCodeView.stopCamera();
        }
    }


    @Override
    public void onStop() {
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        beepManager.playBeepSoundAndVibrate();
        vibrate();
        showDialog();
        mPresenter.getSaleScanData(storeCode, selectDate, result);
//        toast(result + "");
//        mQRCodeView.startSpotDelay(500);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        toast("打开相机出错");
    }

    @OnClick({R.id.enter_by_hand})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_by_hand:
                if(mEnterListener != null) {
                    mEnterListener.onClick(view);
                }
                break;
        }
    }

    private void showFunctionDialog(View view, final SaleEnterBean mBean) {
        final FunctionDialog dialog = new FunctionDialog(view.getContext());
        if(isHistory) {
            if (mHistoryInstance.isExitList(mBean.model)) {
                dialog.setInvText(mBean.model.localStockNum);
                dialog.setShipText(mBean.model.localSaleNum);
            } else {
                dialog.setInvText(mBean.model.stockNum);
                dialog.setShipText(mBean.model.saleNum);
            }
        } else {
            if (mInstance.isExitList(mBean.model)) {
                dialog.setInvText(mBean.model.localStockNum);
                dialog.setShipText(mBean.model.localSaleNum);
            } else {
                dialog.setInvText(mBean.model.stockNum);
                dialog.setShipText(mBean.model.saleNum);
            }
        }
        dialog.setProductImage(mBean.model.url);
        dialog.setProductName(mBean.model.name);
        dialog.setProductCode(mBean.model.code);
        dialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQRCodeView.startSpotDelay(500);
                int localStockNum = StringUtils.StringChangeToInt(dialog.getInvText());
                int localSaleNum = StringUtils.StringChangeToInt(dialog.getShipText());
                if(localStockNum != (mBean.model.stockNum) || localSaleNum != (mBean.model.saleNum)) {
                    mBean.model.setLocalStockNum(localStockNum);
                    mBean.model.setLocalSaleNum(localSaleNum);
                    if(isHistory) {
                        mHistoryInstance.put(mBean.model);
                    } else {
                        mInstance.put(mBean.model);
                    }
                } else {
                    if(isHistory) {
                        mHistoryInstance.delete(mBean.model);
                    } else {
                        mInstance.delete(mBean.model);
                    }
                }
                refreshCount();
            }
        });
        dialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQRCodeView.startSpotDelay(500);
            }
        });

        dialog.build().show();
    }

    @Override
    public void setSaleScanData(SaleEnterBean mBean) {
        hideDialog();
        showFunctionDialog(rootView, mBean);
    }

    @Override
    public void setError() {
        hideDialog();
        mQRCodeView.startSpotDelay(500);
    }

    public void setListCount(TextView mEnterCount) {
        this.mEnterCount = mEnterCount;
    }

    public void refreshCount() {
        if(isHistory) {
            if (mHistoryInstance.getListSize() == 0) {
                mEnterCount.setVisibility(View.GONE);
            } else {
                if (mEnterCount.getVisibility() == View.GONE) {
                    mEnterCount.setVisibility(View.VISIBLE);
                }
                mEnterCount.setText(mHistoryInstance.getListSize() + "");
            }
        } else {
            if (mInstance.getListSize() == 0) {
                mEnterCount.setVisibility(View.GONE);
            } else {
                if (mEnterCount.getVisibility() == View.GONE) {
                    mEnterCount.setVisibility(View.VISIBLE);
                }
                mEnterCount.setText(mInstance.getListSize() + "");
            }
        }
    }

    public ISaleEnterListener getmEnterListener() {
        return mEnterListener;
    }

    public void setmEnterListener(ISaleEnterListener mEnterListener) {
        this.mEnterListener = mEnterListener;
    }
}
