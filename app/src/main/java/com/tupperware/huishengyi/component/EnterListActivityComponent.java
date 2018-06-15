package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.EnterListPresenterModule;
import com.tupperware.huishengyi.ui.EnterListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/5.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = EnterListPresenterModule.class)
public interface EnterListActivityComponent {
    void inject(EnterListActivity activity);
}