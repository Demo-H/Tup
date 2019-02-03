package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.RemarksPresenterModule;
import com.tupperware.biz.ui.activities.RemarksActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/6.
 */
@PerActivity
@Component(dependencies = AppComponent.class , modules = RemarksPresenterModule.class)
public interface RemarksActivityComponent {
    void inject(RemarksActivity activity);
}