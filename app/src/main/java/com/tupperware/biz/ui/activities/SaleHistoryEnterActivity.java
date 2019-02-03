package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.listener.ISaleEnterListener;
import com.tupperware.biz.ui.fragment.SaleHandFragment;
import com.tupperware.biz.ui.fragment.SaleHistoryFragment;
import com.tupperware.biz.ui.fragment.SaleScanFragment;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.data.ProductHistoryProvider;
import com.tupperware.biz.utils.data.ProductProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/8.
 * 销售历史的录入
 */

public class SaleHistoryEnterActivity  extends BaseActivity {

    private static final String TAG = "SaleHistoryEnterActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeft;
    @BindView(R.id.right_rl)
    RelativeLayout mRightIcon;
    @BindView(R.id.right_image)
    ImageView mRightImage;
    @BindView(R.id.enter_count)
    TextView mEnterCount;

    private SaleScanFragment mScanFragment;
    private SaleHandFragment mHandFragment;
    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;
    private String mChooseString;
    private String currentDate; //选择的日期
    private int scanType; // 0为扫描添加，1为手动添加
    private ProductHistoryProvider mHistoryInstance;
    private boolean isHistory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistoryInstance = ProductHistoryProvider.getInstance(getApplicationContext());
        currentDate = getIntent().getStringExtra(Constant.SELECT_DATE);
        scanType = getIntent().getIntExtra(Constant.SELECT_SCAN_TYPE, 0);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SELECT_DATE, currentDate);
        isHistory = DateFormatter.isHistory(currentDate);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState != null) {
            mScanFragment = (SaleScanFragment) fragmentManager.findFragmentByTag(SaleScanFragment.class.getName());
            mHandFragment = (SaleHandFragment) fragmentManager.findFragmentByTag(SaleHistoryFragment.class.getName());
            fragmentManager.beginTransaction().show(mScanFragment).hide(mHandFragment);
            //把当前显示的fragment记录下来
            currentFragment = mScanFragment;
        } else {
            mScanFragment = mScanFragment.newInstance(bundle);
            mHandFragment = mHandFragment.newInstance(bundle);
            if(scanType == 0) {
                showFragment(mScanFragment);
                mRightIcon.setVisibility(View.VISIBLE);
                refreshCount();
            } else {
                showFragment(mHandFragment);
                mScanFragment.setUserHint();
                mRightIcon.setVisibility(View.GONE);
            }
        }
        if(mScanFragment != null) {
            mScanFragment.setmEnterListener(new ISaleEnterListener() {
                @Override
                public void onClick(View view) {
                    showFragment(mHandFragment);
                    mScanFragment.setUserHint();
                    mRightIcon.setVisibility(View.GONE);
                }
            });
            mScanFragment.setListCount(mEnterCount);
        }
        if(mHandFragment != null) {
            mHandFragment.setmEnterListener(new ISaleEnterListener() {
                @Override
                public void onClick(View view) {
                    showFragment(mScanFragment);
                    mScanFragment.setUserVisible();
                    mRightIcon.setVisibility(View.VISIBLE);
                    refreshCount();
                }
            });
        }
        initLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentFragment == mHandFragment) {
            mHandFragment.refreshCount();
        } else {
            refreshCount();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_history_enter;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void requestData() {

    }


    @OnClick({R.id.left_back, R.id.right_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.right_rl:
                Intent intent = new Intent(this, EnterListActivity.class);
                intent.putExtra(Constant.SELECT_DATE, currentDate);
                startActivity(intent);
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

    public void refreshCount() {
        int count;
        if(isHistory) {
            count = mHistoryInstance.getInstance(mContext.getApplicationContext()).getListSize();
        } else {
            count = ProductProvider.getInstance(mContext.getApplicationContext()).getListSize();
        }
        if(count == 0) {
            mEnterCount.setVisibility(View.GONE);
        } else {
            if(mEnterCount.getVisibility() == View.GONE) {
                mEnterCount.setVisibility(View.VISIBLE);
            }
            mEnterCount.setText(count + "");
        }

    }
}
