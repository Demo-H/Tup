package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.BenefitCoinExpenditurePresenterModule;
import com.tupperware.biz.ui.fragment.BenefitCoinExpenditureFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/21.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = BenefitCoinExpenditurePresenterModule.class)
public interface BenefitCoinExpenditureFragmentComponent {
    void inject(BenefitCoinExpenditureFragment fragment);
}
