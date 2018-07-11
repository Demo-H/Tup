package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.MemberDataPresenterModule;
import com.tupperware.huishengyi.ui.fragment.MemberDataFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/12.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MemberDataPresenterModule.class)
public interface MemberDataFragmentComponent {
    void inject(MemberDataFragment fragment);
}