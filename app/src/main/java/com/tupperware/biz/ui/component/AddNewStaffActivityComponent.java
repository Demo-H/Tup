package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.AddNewStaffActivity;
import com.tupperware.biz.ui.module.AddNewStaffPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/10/24.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = AddNewStaffPresenterModule.class)
public interface AddNewStaffActivityComponent {
    void inject(AddNewStaffActivity activity);
}