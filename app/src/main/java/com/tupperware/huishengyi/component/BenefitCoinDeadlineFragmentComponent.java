package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.BenefitCoinDeadlinePresenterModule;
import com.tupperware.huishengyi.ui.fragment.BenefitCoinDeadlineFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/2.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = BenefitCoinDeadlinePresenterModule.class)
public interface BenefitCoinDeadlineFragmentComponent {
    void inject(BenefitCoinDeadlineFragment fragment);
}