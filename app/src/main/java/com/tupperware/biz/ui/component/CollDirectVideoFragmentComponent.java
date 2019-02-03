package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.CollDirectVideoPresenterModule;
import com.tupperware.biz.ui.fragment.CollDirectVideoFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/18.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = CollDirectVideoPresenterModule.class)
public interface CollDirectVideoFragmentComponent {
    void inject(CollDirectVideoFragment fragment);
}