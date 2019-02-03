package com.tupperware.biz.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dhunter.common.qrcode.zxing.QRCodeEncoder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseApplication;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.PersonalQrBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.activities.ActionListActivity;
import com.tupperware.biz.ui.activities.HotSaleActivity;
import com.tupperware.biz.ui.activities.PersonalSettingActivity;
import com.tupperware.biz.ui.activities.RegisterActivity;
import com.tupperware.biz.ui.activities.RemindActivity;
import com.tupperware.biz.ui.activities.SaleEnterActivity;
import com.tupperware.biz.ui.activities.ScanCouponActivity;
import com.tupperware.biz.ui.activities.SearchActivity;
import com.tupperware.biz.ui.component.DaggerHomePageFragmentComponent;
import com.tupperware.biz.ui.contract.HomePageContract;
import com.tupperware.biz.ui.module.HomePagePresenterModule;
import com.tupperware.biz.ui.presenter.HomePagePresenter;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.utils.RxAsyncHelper;
import com.tupperware.biz.utils.logutils.LogF;
import com.tupperware.biz.view.GridMenu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Func1;


/**
 * Created by dhunter on 2018/2/2.
 */

public class HomePageFragment extends BaseFragment implements HomePageContract.View {

    private static final String TAG = "HomePageFragment";

//    @BindView(R.id.scan_btn)
//    ImageView scanningImage;
//    @BindView(R.id.personal)
//    ImageView mPersonal;
//    @BindView(R.id.msg_btn)
//    ImageView msgNotiImage;
    @BindView(R.id.search)
    LinearLayout mSearchLayout;
//    @BindView(R.id.msg_red_tip)
//    ImageView msgRedTip;

//    @BindView(R.id.home_recyclerview)
//    RecyclerView recyclerView;

//    @BindView(R.id.error_layout)
//    RelativeLayout mErrorLayout;

    @BindView(R.id.personal_qr_icon)
    SimpleDraweeView mPersonalQrIcon;
    @BindView(R.id.personal_store_name)
    TextView mStoreName;
    @BindView(R.id.personal_qr_error_tip)
    TextView mQrErrorTip;
    @BindView(R.id.home_page_full_view)
    RelativeLayout mSaveQrView;

    @BindView(R.id.list_1)
    GridMenu gridMenu1;
    @BindView(R.id.list_2)
    GridMenu gridMenu2;
    @BindView(R.id.list_3)
    GridMenu gridMenu3;
    @BindView(R.id.list_4)
    GridMenu gridMenu4;
    @BindView(R.id.list_5)
    GridMenu gridMenu5;
    @BindView(R.id.list_6)
    GridMenu gridMenu6;
    @BindView(R.id.list_7)
    GridMenu gridMenu7;
//    @BindView(R.id.banner)
//    MZBannerView banner;

//    private PullHeaderView mPtrFrame;
//    private HomeMultipleRecycleAdapter adapter;

    private View rootView = null;
//    private int DEFAULT_REFRESH_RED_TIP_TIME = 1000 * 30; //30s刷新一次小红点
//    private int isShow; //消息上是否显示未读消息
//    private TupperHandlerThread thread;
//    private boolean isShowFront; // 首页是否为当前页显示
    public static final int []icon_RES = new int[]{R.mipmap.vistor_ic,R.mipmap.sale_ic,R.mipmap.qrcode_ic,R.mipmap.prod_ic,
        /*R.mipmap.entry_ic,*/  R.mipmap.invite_ic, R.mipmap.filter_ic};
    public static final String []icon_RES_des = new String[]{"发展粉丝","销售录入", "券码核销", "重点热卖",
             /*"本地重点热卖",*/ "活动邀约","滤芯提醒"};
    private Dialog dialog;
    private View inflate;
    private TextView mSaveImage;
    private TextView mCancelImage;
    private boolean isShowQr = false;

    @Inject
    HomePagePresenter mPresenter;

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        isShow = (int) mDataManager.getSpObjectData(Constant.MSG_RED_TIP, 0);
//        thread = HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initLayout() {
//        setShowMsgRedTip(isShow);
        DaggerHomePageFragmentComponent.builder()
                .appComponent(getAppComponent())
                .homePagePresenterModule(new HomePagePresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        bindIconListData();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 4, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.dip2px(getContext(),3)));
//        adapter = new HomeMultipleRecycleAdapter(getActivity());
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
//        isShowFront = true;
//        checkMsgTip();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        isShowFront = false;
        Log.i(TAG,"onStop" );
    }

//    @Override
//    public void setHomePageData(HomeBean homeBean) {
//        if(homeBean.model == null || homeBean.model.headline == null || homeBean.model.headline.size() == 0) {
//            return;
//        }
//        bindTopBannerData(homeBean.model);
//
//    }

//    @Override
//    public void setHomePageData(HomeIndexBean homeBean) {
//        if(homeBean == null){
//            return;
//        }
//        adapter.addData(homeBean);
//        adapter.getData().clear();
//        adapter.resetMaxHasLoadPosition();
//        adapter.setNewData(homeBean.itemInfoList);
//        adapter.setEnableLoadMore(false);
//    }

//    @Override
//    public void setNormalView() {
//        recyclerView.setVisibility(View.VISIBLE);
//        mErrorLayout.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void setNetErrorView() {
//        recyclerView.setVisibility(View.GONE);
//        mErrorLayout.setVisibility(View.VISIBLE);
//    }

    @Override
    public void requestData() {
        isViewCreated = true;
//        if(Constant.Demo) {
//            storeCode = "200001";
//        }
        mPresenter.getPersonQrData(storeCode);
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        LogF.i(TAG,"lazyLoadData");
        if (isViewCreated) {
//            mPresenter.getPersonQrData(storeCode);
        }
    }

/*    private void checkMsgTip() {
        if(isShow == 0) {
            thread.post(new Runnable() {
                @Override
                public void run() {
                    while (isShow == 0 && getUserVisibleHint() && isShowFront) {
                        try {
                            mPresenter.getMsgRedTip();
                            Thread.sleep(DEFAULT_REFRESH_RED_TIP_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }*/

    @OnClick({ R.id.setting, R.id.search, R.id.list_1, R.id.list_2, R.id.list_3,R.id.list_4, R.id.list_5, R.id.list_6, R.id.list_7,
            R.id.personal_qr_error_tip, R.id.personal_qr_icon, R.id.personal_qr_tip})
    public void onclick(View view) {
        switch (view.getId()) {
//            case R.id.scan_btn:
//                try{
//                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        // 申请权限
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
//                        return;
//                    }
//                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
//                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    toast("请打开使用摄像头权限");
//                }
//
//                break;
//            case R.id.personal:
//                Intent personIntent = new Intent(getActivity(), PersonalSettingActivity.class); //需求变更，个人中心需要减至个人设置页面
//                startActivity(personIntent);
//                ActivityManager.getInstance().addActivity(getActivity());
//                break;
//            case R.id.msg_btn:
//                Intent msgIntent = new Intent(getActivity(), MessageActivity.class);
//                startActivity(msgIntent);
//                break;
            case R.id.setting:
                Intent personIntent = new Intent(getActivity(), PersonalSettingActivity.class); //需求变更，个人中心需要减至个人设置页面
                startActivity(personIntent);
                ActivityManager.getInstance().addActivity(getActivity());
                break;
            case R.id.search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.HOME);
                startActivity(searchIntent);
                break;
//            case R.id.error_layout:
//                mPresenter.getHomePageData();
//                break;
            case R.id.list_1:
                jumptoActivity(getActivity(), RegisterActivity.class); //发展新会员
                //临时修改为重点热卖
//                jumptoActivity(getActivity(), HotSaleActivity.class);
//                Intent browserintent = new Intent(getActivity(), WebViewActivity.class);
//                browserintent.putExtra(Constant.BROWSER_JUMP_NAME, Constant.IMPORT_SALE);
//                startActivity(browserintent);
                break;
            case R.id.list_2:
//                jumptoActivity(v.getContext(), ProductEnterActivity.class);
                //销售录入
                try{
                    jumptoActivity(getActivity(), SaleEnterActivity.class);
                } catch (Exception e) {
                    checkCameraPermission(mContext);
                }
                break;

                //临时修改为券码核销
//                try{
//                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
//                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    checkCameraPermission(mContext);
//                }
//                break;
            case R.id.list_3:
                //券码核销
                try{
                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
                    startActivity(intent);
                } catch (Exception e) {
                    checkCameraPermission(mContext);
                }
                //临时修改为滤芯提醒
//                jumptoActivity(getActivity(), RemindActivity.class);
//                jumptoActivity(getActivity(), HotSaleActivity.class);
                break;
            case R.id.list_4:
                //重点热卖
                jumptoActivity(getActivity(), HotSaleActivity.class);
                break;
            case R.id.list_5:
                Intent actIntent = new Intent(getActivity(), ActionListActivity.class);
                actIntent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.PERSONAL_SETTING);
                startActivity(actIntent);
                //旧版重点热卖
//                Intent browintent = new Intent(getActivity(), WebViewActivity.class);
//                browintent.putExtra(Constant.BROWSER_JUMP_NAME, Constant.IMPORT_SALE);
//                startActivity(browintent);
                break;
            case R.id.list_6:
                //滤芯提醒
                jumptoActivity(getActivity(), RemindActivity.class);
                break;
            case R.id.list_7:
                jumptoActivity(getActivity(), RemindActivity.class);
                break;
            case R.id.personal_qr_error_tip:
                mPresenter.getPersonQrData(storeCode);
                break;
            case R.id.personal_qr_icon:
            case R.id.personal_qr_tip:
                if(isShowQr) {
                    showChooseDialog();
                } else {
                    toast("尚未开启eTup门店，暂无二维码");
                }
                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM,Constant.HOME);
        context.startActivity(intent);
    }

//    @Override
//    public void setShowMsgRedTip(int unread) {
//        mDataManager.saveSPObjectData(Constant.MSG_RED_TIP, unread);
//        if(msgRedTip != null) {
//            if(unread == 1) {
//                isShow = 1;
//                msgRedTip.setVisibility(View.VISIBLE);
//            } else {
//                isShow = 0;
//                msgRedTip.setVisibility(View.GONE);
//            }
//        }
//    }


    /**
     * 初始化下拉刷新
     */
//    private void initPtrFrame() {
//        mPtrFrame = (PullHeaderView) rootView.findViewById(R.id.find_pull_refresh_header);
//        mPtrFrame.setEnabled(false);
////        mPtrFrame.setOnRefreshDistanceListener(this);
//        mPtrFrame.setPtrHandler(new PtrHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                toast("onRefreshBegin");
//            }
//        });
//    }


//    @Override
//    public void showToast(String string) {
//        toast(string);
//    }

    @Override
    public void setPersonQrData(PersonalQrBean mBean) {
        if(mBean != null && mBean.getModel() != null) {
            if(mBean.getModel().getLink() != null) {
                showQrImage(mBean.getModel().getLink());
                mStoreName.setText(mBean.getModel().getName());
                isShowQr = true;
//                mPersonalQrIcon.setImageURI(mBean.getModel().getLogo());
            } else {
                isShowQr = false;
                setEmptyView();
            }
        }
    }

    @Override
    public void setNetErrorView() {
        mQrErrorTip.setVisibility(View.VISIBLE);
        mQrErrorTip.setText(getResources().getString(R.string.scan_and_scan_error));
    }

    @Override
    public void setNormalView() {
        mQrErrorTip.setVisibility(View.GONE);
    }

    @Override
    public void setEmptyView() {
        mQrErrorTip.setVisibility(View.VISIBLE);
        mQrErrorTip.setText(getResources().getString(R.string.scan_and_scan_empty));
    }

    private void checkCameraPermission(Context context) {
        if(Build.VERSION.SDK_INT>=23) {
            if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                Toast.makeText(context,"请打开使用摄像头权限",Toast.LENGTH_SHORT).show();
                mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
                    startActivity(intent);
                } else {
                    // 被禁止授权
                    toast("请至权限中心打开本应用的相机访问权限");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null) {
            mPresenter.clearDisposable();
        }
        super.onDestroy();
    }

    /**
     * 绑定icon数据
     */

    private void bindIconListData() {
        List<GridMenu> gridMenus=new ArrayList<>();
        gridMenus.add(gridMenu1);
        gridMenus.add(gridMenu2);
        gridMenus.add(gridMenu3);
        gridMenus.add(gridMenu4);
        gridMenus.add(gridMenu5);
        gridMenus.add(gridMenu6);
//        gridMenus.add(gridMenu7);
        /** 图片文字初始化 **/
        for(int i = 0; i < gridMenus.size(); i++) {
            gridMenus.get(i).setAttr(icon_RES[i],icon_RES_des[i]);
        }
    }

    private void showChooseDialog() {
        dialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_layout, null);
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
                mPresenter.saveQrImage(mSaveQrView, mContext);
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

    /**
     * 绑定banner数据
     * @param
     */
/*    private void bindTopBannerData(final HomeBean.Model item) {

        banner.setIndicatorVisible(false);
//        banner.setIndicatorRes(R.color.colorAccent,R.color.colorPrimary);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = item.headline.get(position).link;
                intent.putExtra(Constant.OPEN_URL,url);
                intent.putExtra(Constant.URL_TITLE,"今日头条");
                view.getContext().startActivity(intent);
//                Toast.makeText(view.getContext(), item.itemContentList.get(position).link, Toast.LENGTH_SHORT).show();
            }
        });
        if(item.headline.size() > 0) {
            banner.setPages(item.headline, new MZHolderCreator<BannerHomeViewHolder>() {
                @Override
                public BannerHomeViewHolder createViewHolder() {
                    return new BannerHomeViewHolder();
                }
            });
        }
        banner.start();
    }*/

    private void showQrImage(final String qrString) {
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
//                Bitmap bitmaplogo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(qrString, ScreenUtil.dip2px(mContext, ScreenUtil.getWidth(mContext) - margin*2),
//                        Color.parseColor("#ff7000"), bitmaplogo);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(qrString, ScreenUtil.dip2px(mContext, ScreenUtil.getWidth(mContext) - margin*2),
                        Color.parseColor("#43484b"), null);
                return bitmap;
            }
        }).runOnMainThread(new Func1<Bitmap,String>() {
            @Override
            public String call(Bitmap bitmap) {
                mPersonalQrIcon.setBackground(new BitmapDrawable(mContext.getResources(),bitmap));
                return null;
            }
        }).subscribe();
    }
}
