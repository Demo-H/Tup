package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.PersonalSettingActivity;
import com.tupperware.biz.ui.module.PersonalSettingPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/5.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PersonalSettingPresenterModule.class)
public interface PersonalSettingActivityComponent {
    void inject(PersonalSettingActivity activity);
}