package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.OnlineOrderDetialPresenterModule;
import com.tupperware.huishengyi.ui.activities.OnlineOrderDetialActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/17.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = OnlineOrderDetialPresenterModule.class)
public interface OnlineOrderDetialActivityComponent {
    void inject(OnlineOrderDetialActivity activity);
}