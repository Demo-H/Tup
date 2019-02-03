package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.DevProductSelectPresenterModule;
import com.tupperware.biz.ui.activities.DevProductSelectActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/6.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = DevProductSelectPresenterModule.class)
public interface DevProductSelectActivityComponent {
    void inject(DevProductSelectActivity activity);
}