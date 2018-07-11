package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by dhunter on 2018/2/9.
 */

public class URLTestActivity extends BaseActivity {

    @BindView(R.id.url)
    TextView urlText;
    @BindView(R.id.response)
    TextView responseText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_url_test;
    }

    @Override
    protected void initLayout() {
        Intent intent  = getIntent();
        urlText.setText(intent.getStringExtra("url"));
        responseText.setText(intent.getStringExtra("response"));
    }

    @Override
    protected void requestData() {

    }
}
