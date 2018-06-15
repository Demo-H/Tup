package com.tupperware.huishengyi.ui.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by dhunter on 2018/3/6.
 */

public class BasePresenter {
    protected CompositeDisposable disposables;

    public void addDisposabe(Disposable disposable){
        if(disposables == null){
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);

    }

    public void clearDisposable() {
        if(disposables != null) {
            disposables.clear();
            disposables = null;
        }
    }
}
