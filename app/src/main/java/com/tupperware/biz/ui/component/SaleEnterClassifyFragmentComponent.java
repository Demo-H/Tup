package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.SaleEnterClassifyPresenterModule;
import com.tupperware.biz.ui.fragment.SaleEnterClassifyFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/28.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleEnterClassifyPresenterModule.class)
public interface SaleEnterClassifyFragmentComponent {
    void inject(SaleEnterClassifyFragment fragment);
}