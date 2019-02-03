package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.ServerManagerDetialPresenterModule;
import com.tupperware.biz.ui.fragment.ServerManagerDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = ServerManagerDetialPresenterModule.class)
public interface ServerManagerDetialFragmentComponent {
    void inject(ServerManagerDetialFragment fragment);
}