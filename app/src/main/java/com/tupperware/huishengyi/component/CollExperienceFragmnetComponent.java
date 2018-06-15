package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.CollExperiencePresenterModule;
import com.tupperware.huishengyi.ui.fragment.CollExperienceFragmnet;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/18.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = CollExperiencePresenterModule.class)
public interface CollExperienceFragmnetComponent {
    void inject(CollExperienceFragmnet fragment);
}