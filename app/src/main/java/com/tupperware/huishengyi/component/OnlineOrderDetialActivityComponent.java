package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.OnlineOrderDetialPresenterModule;
import com.tupperware.huishengyi.ui.OnlineOrderDetialActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/17.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = OnlineOrderDetialPresenterModule.class)
public interface OnlineOrderDetialActivityComponent {
    void inject(OnlineOrderDetialActivity activity);
}