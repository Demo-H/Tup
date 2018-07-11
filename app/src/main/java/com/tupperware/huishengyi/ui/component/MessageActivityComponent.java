package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.MessagePresenterModule;
import com.tupperware.huishengyi.ui.activities.MessageActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/22.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MessagePresenterModule.class)
public interface MessageActivityComponent {
    void inject(MessageActivity activity);
}