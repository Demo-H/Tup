package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.SearchOrderPresenterModule;
import com.tupperware.biz.ui.fragment.SearchOrderFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SearchOrderPresenterModule.class)
public interface SearchOrderFragmentComponent {
    void inject(SearchOrderFragment fragment);
}