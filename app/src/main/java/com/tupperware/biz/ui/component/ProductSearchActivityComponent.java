package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.ProductSearchPresenterModule;
import com.tupperware.biz.ui.activities.ProductSearchActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/29.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ProductSearchPresenterModule.class)
public interface ProductSearchActivityComponent {
    void inject(ProductSearchActivity activity);
}