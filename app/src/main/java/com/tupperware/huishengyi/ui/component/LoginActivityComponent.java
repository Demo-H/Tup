package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.activities.LoginActivity;
import com.tupperware.huishengyi.ui.module.LoginPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/4.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = LoginPresenterModule.class)
public interface LoginActivityComponent {
    void inject(LoginActivity activity);
}