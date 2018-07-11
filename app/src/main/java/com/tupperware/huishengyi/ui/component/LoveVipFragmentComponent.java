package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.LoveVipPresenterModule;
import com.tupperware.huishengyi.ui.fragment.LoveVipFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/8.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = LoveVipPresenterModule.class)
public interface LoveVipFragmentComponent {
    void inject(LoveVipFragment fragment);
}