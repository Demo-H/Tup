package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.PersonalQrPresenterModule;
import com.tupperware.huishengyi.ui.activities.PersonalQrActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/4.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PersonalQrPresenterModule.class)
public interface PersonalQrActivityComponent {
    void inject(PersonalQrActivity activity);
}