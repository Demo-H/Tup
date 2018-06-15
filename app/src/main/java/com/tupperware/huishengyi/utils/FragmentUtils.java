package com.tupperware.huishengyi.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by dhunter on 2018/3/26.
 */

public class FragmentUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
    public static void addFragmentUseStack(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId,String tag){
        if(!fragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }
}
