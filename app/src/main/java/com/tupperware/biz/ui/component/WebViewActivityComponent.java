package com.tupperware.biz.ui.component;

import com.android.dhunter.common.AppComponent;
import com.android.dhunter.common.PerActivity;
import com.tupperware.biz.ui.activities.WebViewActivity;
import com.tupperware.biz.ui.module.WebViewPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/7/9.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = WebViewPresenterModule.class)
public interface WebViewActivityComponent {
    void inject(WebViewActivity activity);
}