package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.ReservationActionPresenterModule;
import com.tupperware.huishengyi.ui.ReservationActionActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/14.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ReservationActionPresenterModule.class)
public interface ReservationActionActivityComponent {
    void inject(ReservationActionActivity activity);
}