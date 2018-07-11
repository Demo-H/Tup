package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.RemarksPresenterModule;
import com.tupperware.huishengyi.ui.activities.RemarksActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/6.
 */
@PerActivity
@Component(dependencies = AppComponent.class , modules = RemarksPresenterModule.class)
public interface RemarksActivityComponent {
    void inject(RemarksActivity activity);
}