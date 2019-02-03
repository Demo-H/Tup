package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.PurFollowDetialPresenterModule;
import com.tupperware.biz.ui.fragment.PurFollowDetialFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = PurFollowDetialPresenterModule.class)
public interface PurFollowDetialFragmentComponent {
    void inject(PurFollowDetialFragment fragment);
}
