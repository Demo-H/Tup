package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.MemberListActivity;
import com.tupperware.biz.ui.module.MemberListPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/10/22.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MemberListPresenterModule.class)
public interface MemberListActivityComponent {
    void inject(MemberListActivity activity);
}