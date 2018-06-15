package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.VersionCheckPresenterModule;
import com.tupperware.huishengyi.ui.MainActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/25.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = VersionCheckPresenterModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
