package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.fragment.InventoryFragment;
import com.tupperware.biz.ui.module.InventoryPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = InventoryPresenterModule.class)
public interface InventoryFragmentComponent {
    void inject(InventoryFragment fragment);
}