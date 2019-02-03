package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerFragment;
import com.tupperware.biz.ui.module.CollArticlePresenterModule;
import com.tupperware.biz.ui.fragment.CollArticleFragment;

import dagger.Component;

/**
 * Created by dhunter on 2018/4/17.
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = CollArticlePresenterModule.class)
public interface CollArticleFragmentComponent {
    void inject(CollArticleFragment fragment);
}