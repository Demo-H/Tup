package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.SaleDataDetialPresenterModule;
import com.tupperware.huishengyi.ui.fragment.SaleDataDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/11.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleDataDetialPresenterModule.class)
public interface SaleDataDetialFragmentComponent {
    void inject(SaleDataDetialFragment fragment);
}