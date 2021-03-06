package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.StaffManagerPresenterModule;
import com.tupperware.biz.ui.fragment.StaffManagerFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/22.
 */


@PerFragment
@Component(dependencies = AppComponent.class , modules = StaffManagerPresenterModule.class)
public interface StaffManagerFragmentComponent {
    void inject(StaffManagerFragment fragment);
}
