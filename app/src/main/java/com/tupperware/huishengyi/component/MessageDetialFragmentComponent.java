package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.MessageDetailPresenterModule;
import com.tupperware.huishengyi.ui.fragment.MessageDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/26.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MessageDetailPresenterModule.class)
public interface MessageDetialFragmentComponent {
    void inject(MessageDetialFragment fragment);
}