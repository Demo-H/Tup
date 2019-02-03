package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.EnterListPresenterModule;
import com.tupperware.biz.ui.activities.EnterListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/5.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = EnterListPresenterModule.class)
public interface EnterListActivityComponent {
    void inject(EnterListActivity activity);
}