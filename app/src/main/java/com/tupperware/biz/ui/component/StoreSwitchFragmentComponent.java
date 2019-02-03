package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.StoreSwitchPresenterModule;
import com.tupperware.biz.ui.fragment.StoreSwitchFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/19.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = StoreSwitchPresenterModule.class)
public interface StoreSwitchFragmentComponent {
    void inject(StoreSwitchFragment fragment);
}
