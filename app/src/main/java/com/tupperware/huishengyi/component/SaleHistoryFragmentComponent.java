package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.SaleHistoryPresenterModule;
import com.tupperware.huishengyi.ui.fragment.SaleHistoryFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/28.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = SaleHistoryPresenterModule.class)
public interface SaleHistoryFragmentComponent {
    void inject(SaleHistoryFragment fragment);
}