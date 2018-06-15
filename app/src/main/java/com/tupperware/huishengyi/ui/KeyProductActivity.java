package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.KeyProductFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductActivity extends BaseActivity {

    private static final String TAG = "KeyProductActivity";

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
    @BindView(R.id.right_image)
    ImageView mRightImage;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private KeyProductFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_product);
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
        initToolbar();
        mFragment = new KeyProductFragment();
        mFragment.setRightIconText(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.keyproductFrame, mFragment).commit();
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolbar() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.date_btn);
        mTitle.setText(getResources().getString(R.string.key_product_upload));
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
