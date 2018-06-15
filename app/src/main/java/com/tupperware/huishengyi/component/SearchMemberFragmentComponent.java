package com.tupperware.huishengyi.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.huishengyi.module.SearchMemberPresenterModule;
import com.tupperware.huishengyi.ui.fragment.SearchMemberFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/5/30.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = SearchMemberPresenterModule.class)
public interface SearchMemberFragmentComponent {
    void inject(SearchMemberFragment fragment);
}