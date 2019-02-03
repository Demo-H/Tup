package com.tupperware.biz.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.fragment.StoreSwitchFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreSwitchActivity extends BaseActivity {

    private static final String TAG = "StoreSwitchActivity";

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

    private FragmentManager fragmentManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_switch;
    }

    @Override
    protected void initLayout() {
        initToolBar();
        fragmentManager = this.getSupportFragmentManager();
        StoreSwitchFragment mFragment = (StoreSwitchFragment) fragmentManager.findFragmentById(R.id.storeContentFrame);
        if(mFragment == null) {
            mFragment = StoreSwitchFragment.newInstance();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.storeContentFrame, mFragment, "storeswitch");
        transaction.commit();

    }

    @Override
    protected void requestData() {

    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.change_store));
    }

    @OnClick({R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }

}
