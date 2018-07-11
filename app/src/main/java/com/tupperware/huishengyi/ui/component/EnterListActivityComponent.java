package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.EnterListPresenterModule;
import com.tupperware.huishengyi.ui.activities.EnterListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/5.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = EnterListPresenterModule.class)
public interface EnterListActivityComponent {
    void inject(EnterListActivity activity);
}