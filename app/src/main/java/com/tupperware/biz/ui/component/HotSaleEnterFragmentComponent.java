package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.fragment.HotSaleEnterFragment;
import com.tupperware.biz.ui.module.HotSaleEnterPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/12/3.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = HotSaleEnterPresenterModule.class)
public interface HotSaleEnterFragmentComponent {
    void inject(HotSaleEnterFragment fragment);
}