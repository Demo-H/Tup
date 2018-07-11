package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.activities.RegisterChooseActivity;
import com.tupperware.huishengyi.ui.module.RegisterChoosePresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/10.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RegisterChoosePresenterModule.class)
public interface RegisterChooseActivityComponent {
    void inject(RegisterChooseActivity activity);
}