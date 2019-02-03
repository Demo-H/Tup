package com.tupperware.biz.ui.activities;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.fragment.ProductEnterFragment;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterActivity extends BaseActivity {

    private static final String TAG = "ProductEnterActivity";
    private ProductEnterFragment mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_enter;
    }

    @Override
    protected void initLayout() {
        mFragment = new ProductEnterFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.productEnterContentFrame, mFragment, "productEnter").commit();
    }

    @Override
    protected void requestData() {

    }

}
