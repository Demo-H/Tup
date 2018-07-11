package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.activities.ResetPasswordActivity;
import com.tupperware.huishengyi.ui.module.ResetPasswordPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/6.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ResetPasswordPresenterModule.class)
public interface ResetPasswordActivityComponent {
    void inject(ResetPasswordActivity activity);
}