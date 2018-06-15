package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tupperware.huishengyi.R;

/**
 * Created by dhunter on 2018/2/9.
 */

public class URLTestActivity extends BaseActivity {

    private TextView urlText;
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_url_test);

        urlText = (TextView) findViewById(R.id.url);
        responseText = (TextView) findViewById(R.id.response);

        Intent intent  = getIntent();
        urlText.setText(intent.getStringExtra("url"));
        responseText.setText(intent.getStringExtra("response"));
    }



    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initLayoutData() {

    }
}
