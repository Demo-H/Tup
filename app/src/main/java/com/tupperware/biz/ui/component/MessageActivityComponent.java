package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.MessagePresenterModule;
import com.tupperware.biz.ui.activities.MessageActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/22.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MessagePresenterModule.class)
public interface MessageActivityComponent {
    void inject(MessageActivity activity);
}