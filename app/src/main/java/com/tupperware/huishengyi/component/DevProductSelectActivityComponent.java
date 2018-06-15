package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.DevProductSelectPresenterModule;
import com.tupperware.huishengyi.ui.DevProductSelectActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/6.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = DevProductSelectPresenterModule.class)
public interface DevProductSelectActivityComponent {
    void inject(DevProductSelectActivity activity);
}