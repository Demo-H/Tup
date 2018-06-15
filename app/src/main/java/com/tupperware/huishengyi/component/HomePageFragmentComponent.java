package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.HomePagePresenterModule;
import com.tupperware.huishengyi.ui.fragment.HomePageFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/12.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = HomePagePresenterModule.class)
public interface HomePageFragmentComponent {
    void inject(HomePageFragment fragment);
}
