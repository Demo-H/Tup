package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.RegisterChooseActivity;
import com.tupperware.biz.ui.module.RegisterChoosePresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/10.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RegisterChoosePresenterModule.class)
public interface RegisterChooseActivityComponent {
    void inject(RegisterChooseActivity activity);
}