package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.StoreSwitchFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

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

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_switch);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        mContext = this;
        initLayout();
    }


    @Override
    protected void findViews() {

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
    protected void initLayoutData() {

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
