package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.ReservationServerPresenterModule;
import com.tupperware.huishengyi.ui.ReservationServerActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/31.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ReservationServerPresenterModule.class)
public interface ReservationServerActivityComponent {
    void inject(ReservationServerActivity activity);
}