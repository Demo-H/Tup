package com.tupperware.biz.listener;

/**
 * Created by dhunter on 2018/3/20.
 */

/**各回调方法提供了空实现，请覆写需要的回调方法*/
public abstract class OnPermissionResultListener {
    /** grantResult的值为PackageManager.PERMISSION_DENIED或PackageManager.PERMISSION_GRANTED*/
//        public void onResult(String[] permissions, int[] grantResults) {};

    /** 请求的所有权限获取成功*/
    public void onAllGranted(){};

    /** permissions返回用户拒绝的权限*/
    public void onAnyDenied(String[] permissions){};

    /** permissions返回被用户拒绝了,并且选中了不再提醒选项的权限*/
    public void onAlwaysDenied(String[] permissions){};
}