package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.ActionInvitatePresenterModule;
import com.tupperware.huishengyi.ui.activities.ActionInvitateActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/14.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ActionInvitatePresenterModule.class)
public interface ActionInvitateActivityComponent {
    void inject(ActionInvitateActivity activity);
}