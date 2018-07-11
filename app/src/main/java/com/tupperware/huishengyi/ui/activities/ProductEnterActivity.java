package com.tupperware.huishengyi.ui.activities;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.ui.fragment.ProductEnterFragment;

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
