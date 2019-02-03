package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.ResOrderServerPresenterModule;
import com.tupperware.biz.ui.fragment.ResOrderServerFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/8.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = ResOrderServerPresenterModule.class)
public interface ResOrderServerFragmentComponent {
    void inject(ResOrderServerFragment fragment);
}