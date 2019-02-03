package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.MemberSearchActivity;
import com.tupperware.biz.ui.module.MemberListPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/10/26.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MemberListPresenterModule.class)
public interface MemberSearchActivityComponent {
    void inject(MemberSearchActivity activity);
}