package com.tupperware.huishengyi.model;

/**
 * Created by dhunter on 2018/4/20.
 */

public interface OnLoadDataListListener<T> {
    void onSuccess(T data);

    void onFailure(Throwable e);
}