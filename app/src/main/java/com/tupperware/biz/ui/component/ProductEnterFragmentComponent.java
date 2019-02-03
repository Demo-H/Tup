package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.ProductEnterPresenterModule;
import com.tupperware.biz.ui.fragment.ProductEnterFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/23.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = ProductEnterPresenterModule.class)
public interface ProductEnterFragmentComponent {
    void inject(ProductEnterFragment fragment);
}