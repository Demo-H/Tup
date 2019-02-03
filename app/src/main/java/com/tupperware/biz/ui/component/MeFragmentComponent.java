package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.fragment.MeFragment;
import com.tupperware.biz.ui.module.MePresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/9/6.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MePresenterModule.class)
public interface MeFragmentComponent {
    void inject(MeFragment fragment);
}