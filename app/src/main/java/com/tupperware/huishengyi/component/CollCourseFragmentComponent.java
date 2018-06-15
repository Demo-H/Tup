package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.CollCoursePresenterModule;
import com.tupperware.huishengyi.ui.fragment.CollCourseFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/17.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = CollCoursePresenterModule.class)
public interface CollCourseFragmentComponent {
    void inject(CollCourseFragment fragment);
}