package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.OnlineOrderDetialPresenterModule;
import com.tupperware.biz.ui.activities.OnlineOrderDetialActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/17.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = OnlineOrderDetialPresenterModule.class)
public interface OnlineOrderDetialActivityComponent {
    void inject(OnlineOrderDetialActivity activity);
}