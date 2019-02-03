package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.SaleScanPresenterModule;
import com.tupperware.biz.ui.fragment.SaleScanFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/4.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleScanPresenterModule.class)
public interface SaleScanFragmentComponent {
    void inject(SaleScanFragment fragment);
}