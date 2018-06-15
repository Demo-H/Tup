package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.VideoPresenterModule;
import com.tupperware.huishengyi.ui.fragment.VideoFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/6.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = VideoPresenterModule.class)
public interface VideoFragmentComponent {
    void inject(VideoFragment fragment);
}
