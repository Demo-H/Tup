package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.huishengyi.module.GiftListPresenterModule;
import com.tupperware.huishengyi.ui.GiftListActivity;

import dagger.Component;

/**
 * Created by dhunter on 2018/6/7.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = GiftListPresenterModule.class)
public interface GiftListActivityComponent {
    void inject(GiftListActivity activity);
}