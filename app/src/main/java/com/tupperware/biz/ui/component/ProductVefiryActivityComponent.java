package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.ProductVefiryActivity;
import com.tupperware.biz.ui.module.ProductVefiryPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/10.
 */
@PerActivity
@Component(dependencies = AppComponent.class , modules = ProductVefiryPresenterModule.class)
public interface ProductVefiryActivityComponent {
    void inject(ProductVefiryActivity activity);
}