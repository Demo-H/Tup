package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.PersonalQrPresenterModule;
import com.tupperware.biz.ui.activities.PersonalQrActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/4.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PersonalQrPresenterModule.class)
public interface PersonalQrActivityComponent {
    void inject(PersonalQrActivity activity);
}