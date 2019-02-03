package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.RegisterActivity;
import com.tupperware.biz.ui.module.RegisterPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/10.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RegisterPresenterModule.class)
public interface RegisterActivityComponent {
    void inject(RegisterActivity activity);
}