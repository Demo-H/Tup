package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.BusinessCollegePresenterModule;
import com.tupperware.biz.ui.fragment.BusinessCollegeFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/2.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = BusinessCollegePresenterModule.class)
public interface BusinessCollegeFragmentComponent {
    void inject(BusinessCollegeFragment fragment);
}