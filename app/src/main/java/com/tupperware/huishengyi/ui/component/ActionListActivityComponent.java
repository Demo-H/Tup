package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.ActionListPresenterModule;
import com.tupperware.huishengyi.ui.activities.ActionListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/7.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ActionListPresenterModule.class)
public interface ActionListActivityComponent {
    void inject(ActionListActivity activity);
}