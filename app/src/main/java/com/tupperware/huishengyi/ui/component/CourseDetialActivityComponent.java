package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.module.CourseDetialPresenterModule;
import com.tupperware.huishengyi.ui.activities.CourseDetialActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/9.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CourseDetialPresenterModule.class)
public interface CourseDetialActivityComponent {
    void inject(CourseDetialActivity activity);
}
