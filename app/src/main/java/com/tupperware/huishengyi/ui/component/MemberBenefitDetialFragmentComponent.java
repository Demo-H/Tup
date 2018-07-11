package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.MemberBenefitDetialPresenterModule;
import com.tupperware.huishengyi.ui.fragment.MemberBenefitDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/2.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MemberBenefitDetialPresenterModule.class)
public interface MemberBenefitDetialFragmentComponent {
    void inject(MemberBenefitDetialFragment fragment);
}
