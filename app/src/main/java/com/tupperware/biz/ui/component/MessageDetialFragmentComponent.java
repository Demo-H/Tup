package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.MessageDetailPresenterModule;
import com.tupperware.biz.ui.fragment.MessageDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/26.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MessageDetailPresenterModule.class)
public interface MessageDetialFragmentComponent {
    void inject(MessageDetialFragment fragment);
}