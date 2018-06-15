package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.SaleEnterClassifyPresenterModule;
import com.tupperware.huishengyi.ui.fragment.SaleEnterClassifyFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/28.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleEnterClassifyPresenterModule.class)
public interface SaleEnterClassifyFragmentComponent {
    void inject(SaleEnterClassifyFragment fragment);
}