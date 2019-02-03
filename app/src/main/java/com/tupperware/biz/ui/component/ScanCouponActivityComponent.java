package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.ScanCouponActivity;
import com.tupperware.biz.ui.module.ScanCouponPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/11.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ScanCouponPresenterModule.class)
public interface ScanCouponActivityComponent {
    void inject(ScanCouponActivity activity);
}