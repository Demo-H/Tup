package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.RemindActivity;
import com.tupperware.biz.ui.module.RemindPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/29.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RemindPresenterModule.class)
public interface RemindActivityComponent {
    void inject(RemindActivity activity);
}