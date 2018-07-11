package com.tupperware.huishengyi.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.ui.module.SearchOrderPresenterModule;
import com.tupperware.huishengyi.ui.fragment.SearchOrderFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SearchOrderPresenterModule.class)
public interface SearchOrderFragmentComponent {
    void inject(SearchOrderFragment fragment);
}