package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.ProductSearchPresenterModule;
import com.tupperware.huishengyi.ui.ProductSearchActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/29.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ProductSearchPresenterModule.class)
public interface ProductSearchActivityComponent {
    void inject(ProductSearchActivity activity);
}