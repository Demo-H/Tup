package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.StoreSwitchPresenterModule;
import com.tupperware.huishengyi.ui.fragment.StoreSwitchFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/19.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = StoreSwitchPresenterModule.class)
public interface StoreSwitchFragmentComponent {
    void inject(StoreSwitchFragment fragment);
}
