package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.activities.MemberDetialActivity;
import com.tupperware.huishengyi.ui.module.MemberDetialPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/6.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MemberDetialPresenterModule.class)
public interface MemberDetialActivityComponent {
    void inject(MemberDetialActivity activity);
}