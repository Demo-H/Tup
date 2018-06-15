package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.ResOrderPendingPresenterModule;
import com.tupperware.huishengyi.ui.fragment.ResOrderPendingFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/15.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = ResOrderPendingPresenterModule.class)
public interface ResOrderPendingFragmentComponent {
    void inject(ResOrderPendingFragment fragment);
}
