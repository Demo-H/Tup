package com.tupperware.huishengyi.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dhunter.common.utils.LeakUtils;
import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.EasyTopPopup;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.base.BaseApplication;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.listener.ISaleEnterListener;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.SaleHandFragment;
import com.tupperware.huishengyi.ui.fragment.SaleHistoryFragment;
import com.tupperware.huishengyi.ui.fragment.SaleScanFragment;
import com.tupperware.huishengyi.utils.data.ProductHistoryProvider;
import com.tupperware.huishengyi.utils.data.ProductProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/24.
 * 销售录入界面
 */

public class SaleEnterActivity extends BaseActivity {

    private static final String TAG = "SaleEnterActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeft;
    @BindView(R.id.sale_scan_enter)
    TextView mScanEnter;
    @BindView(R.id.sale_history)
    TextView mSaleHistory;
    @BindView(R.id.right_rl)
    RelativeLayout mRightIcon;
    @BindView(R.id.right_image)
    ImageView mRightImage;
    @BindView(R.id.right_add_image)
    ImageView mAddRightImage;
    @BindView(R.id.enter_count)
    TextView mEnterCount;

    private SaleScanFragment mScanFragment;
    private SaleHistoryFragment mHistoryFragment;
    private SaleHandFragment mHandFragment;
    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;
    private BaseFragment firstFragment; //标记是扫描页还是手动录入页
    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private EasyTopPopup mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkCameraPermission(this);
        mInstance = ProductProvider.getInstance(getApplicationContext());
        mHistoryInstance = ProductHistoryProvider.getInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState != null) {
            mScanFragment = (SaleScanFragment) fragmentManager.findFragmentByTag(SaleScanFragment.class.getName());
            mHistoryFragment = (SaleHistoryFragment) fragmentManager.findFragmentByTag(SaleHistoryFragment.class.getName());
            mHandFragment = (SaleHandFragment) fragmentManager.findFragmentByTag(SaleHistoryFragment.class.getName());
            fragmentManager.beginTransaction().show(mScanFragment).hide(mHistoryFragment).hide(mHandFragment);
            //把当前显示的fragment记录下来
            currentFragment = mScanFragment;
            firstFragment = mScanFragment;
        } else {
            mScanFragment = mScanFragment.newInstance(null);
            mHistoryFragment = mHistoryFragment.newInstance();
            mHandFragment = mHandFragment.newInstance(null);
            firstFragment = mScanFragment;
            showFragment(mScanFragment);
            mRightIcon.setVisibility(View.VISIBLE);
            mRightImage.setVisibility(View.VISIBLE);
            mAddRightImage.setVisibility(View.GONE);
            refreshCount();
        }
        mScanFragment.setmEnterListener(new ISaleEnterListener() {
            @Override
            public void onClick(View view) {
                if(mHandFragment == null) {
                    mHandFragment = mHandFragment.newInstance(null);
                }
                firstFragment = mHandFragment;
                showFragment(mHandFragment);
                mScanFragment.setUserHint();
                mRightIcon.setVisibility(View.GONE);
            }
        });
        mScanFragment.setListCount(mEnterCount);
        mHandFragment.setmEnterListener(new ISaleEnterListener() {
            @Override
            public void onClick(View view) {
                if(mScanFragment == null) {
                    mScanFragment = mScanFragment.newInstance(null);
                }
                firstFragment = mScanFragment;
                showFragment(mScanFragment);
                mScanFragment.setUserVisible();
                mRightIcon.setVisibility(View.VISIBLE);
                mRightImage.setVisibility(View.VISIBLE);
                mAddRightImage.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentFragment == mHandFragment) {
            mHandFragment.refreshCount();
        } else if(currentFragment == mHistoryFragment) {
            mHistoryFragment.refreshFootListCount();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_enter;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void requestData() {

    }


    @OnClick({R.id.left_back, R.id.sale_scan_enter, R.id.sale_history, R.id.right_image, R.id.right_add_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.sale_scan_enter:
                mScanEnter.setTextColor(getResources().getColor(R.color.color_ff7000));
                mScanEnter.setBackground(getResources().getDrawable(R.drawable.bg_sale_enter_title_white_left));
                mSaleHistory.setTextColor(getResources().getColor(R.color.white));
                mSaleHistory.setBackground(getResources().getDrawable(R.drawable.bg_sale_enter_title_yellow_right));
                showFragment(firstFragment);
                if(firstFragment == mHandFragment) {
                    mRightIcon.setVisibility(View.GONE);
                    mHandFragment.refreshCount();
                } else {
                    mScanFragment.setUserVisible();
                    mRightIcon.setVisibility(View.VISIBLE);
                    mRightImage.setVisibility(View.VISIBLE);
                    mAddRightImage.setVisibility(View.GONE);
                    refreshCount();
                }
                break;
            case R.id.sale_history:
                mSaleHistory.setTextColor(getResources().getColor(R.color.color_ff7000));
                mSaleHistory.setBackground(getResources().getDrawable(R.drawable.bg_sale_enter_title_white_right));
                mScanEnter.setTextColor(getResources().getColor(R.color.white));
                mScanEnter.setBackground(getResources().getDrawable(R.drawable.bg_sale_enter_title_yellow_left));
                showFragment(mHistoryFragment);
                mRightIcon.setVisibility(View.VISIBLE);
                mRightImage.setVisibility(View.GONE);
                mAddRightImage.setVisibility(View.VISIBLE);
                mEnterCount.setVisibility(View.GONE);
                if(firstFragment == mScanFragment) {
                    mScanFragment.setUserHint();
                }
                break;
            case R.id.right_image:
                Intent intent = new Intent(this, EnterListActivity.class);
                startActivity(intent);
                break;
            case R.id.right_add_image:
                mPopup = new EasyTopPopup(this, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
                    }
                }, getAddItemsName(), Config.RIGHT);
                mPopup.show(view);
                break;
        }
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(BaseFragment fg){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .add(R.id.saleEnterFrame, fg, fg.getClass().getName());  //第三个参数为添加当前的fragment时绑定一个tag，即绑定fragment的类名
            } else {
                transaction.add(R.id.saleEnterFrame, fg, fg.getClass().getName());
            }
        }else{
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .show(fg);
            } else {
                transaction.show(fg);
            }
        }
        currentFragment = fg;
        transaction.commit();
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        if(currentFragment.equals(mHandFragment)) {
            showFragment(mScanFragment);
            mScanFragment.setUserVisible();
            mRightIcon.setVisibility(View.VISIBLE);
            mRightImage.setVisibility(View.VISIBLE);
            mAddRightImage.setVisibility(View.GONE);
            refreshCount();
        } else {
            mHistoryInstance.deleteAll();
            finish();
        }
    }

    public void refreshCount() {
        if(mInstance.getListSize() == 0 ) {
            mEnterCount.setVisibility(View.GONE);
        } else {
            if(mEnterCount.getVisibility() == View.GONE) {
                mEnterCount.setVisibility(View.VISIBLE);
            }
            mEnterCount.setText(mInstance.getListSize() + "");
        }
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getAddItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.add_scan));
        items.add(getResources().getString(R.string.add_hand));
        return items;
    }

    private void selection(int position) {
        switch (position) {
            case 0:
            case 1:
                String currentDate = mHistoryFragment.getCurrentSelectDate();
                Intent intent = new Intent(SaleEnterActivity.this, SaleHistoryEnterActivity.class);
                intent.putExtra(Constant.SELECT_DATE, currentDate);
                intent.putExtra(Constant.SELECT_SCAN_TYPE, position);
                startActivity(intent);
                break;
//            case 0:
//
//                break;
//            case 1:
//                toast("" + position);
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakUtils.fixInputMethodManagerLeak(this);
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
