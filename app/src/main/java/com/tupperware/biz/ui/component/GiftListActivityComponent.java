package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.module.GiftListPresenterModule;
import com.tupperware.biz.ui.activities.GiftListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/7.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = GiftListPresenterModule.class)
public interface GiftListActivityComponent {
    void inject(GiftListActivity activity);
}