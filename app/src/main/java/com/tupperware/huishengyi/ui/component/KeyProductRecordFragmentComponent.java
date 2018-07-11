package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.KeyProductRecordPresenterModule;
import com.tupperware.huishengyi.ui.fragment.KeyProductRecordFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/3/22.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = KeyProductRecordPresenterModule.class)
public interface KeyProductRecordFragmentComponent {
    void inject(KeyProductRecordFragment fragment);
}
