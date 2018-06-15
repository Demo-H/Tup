package com.tupperware.huishengyi.ui;

import android.os.Bundle;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.ui.fragment.ProductEnterFragment;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterActivity extends BaseActivity {

    private static final String TAG = "ProductEnterActivity";
    private ProductEnterFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_enter);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mFragment = new ProductEnterFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.productEnterContentFrame, mFragment, "productEnter").commit();
    }

    @Override
    protected void initLayoutData() {

    }

}
