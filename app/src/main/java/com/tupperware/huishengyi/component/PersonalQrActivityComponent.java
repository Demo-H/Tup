package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.PersonalQrPresenterModule;
import com.tupperware.huishengyi.ui.PersonalQrActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/4.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PersonalQrPresenterModule.class)
public interface PersonalQrActivityComponent {
    void inject(PersonalQrActivity activity);
}