package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.ZixunPresenterModule;
import com.tupperware.huishengyi.ui.fragment.ZixunFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/6.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = ZixunPresenterModule.class)
public interface ZixunFragmentComponent {
    void inject(ZixunFragment fragment);
}
