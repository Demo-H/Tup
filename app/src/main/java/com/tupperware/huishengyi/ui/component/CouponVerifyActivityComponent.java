package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.ui.activities.CouponVerifyActivity;
import com.tupperware.huishengyi.ui.module.CouponVerifyPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/10.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CouponVerifyPresenterModule.class)
public interface CouponVerifyActivityComponent {
    void inject(CouponVerifyActivity activity);
}