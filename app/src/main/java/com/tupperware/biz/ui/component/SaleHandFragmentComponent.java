package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.SaleHandPresenterModule;
import com.tupperware.biz.ui.fragment.SaleHandFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/25.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleHandPresenterModule.class)
public interface SaleHandFragmentComponent {
    void inject(SaleHandFragment fragment);
}