package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.ForgetPasswordActivity;
import com.tupperware.biz.ui.module.ForgetPasswordPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/6.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ForgetPasswordPresenterModule.class)
public interface ForgetPasswordActivityComponent {
    void inject(ForgetPasswordActivity activity);
}