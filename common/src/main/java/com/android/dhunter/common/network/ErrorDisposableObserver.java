package com.android.dhunter.common.network;

import android.widget.Toast;

import com.android.dhunter.common.GlobalAppComponent;

import io.reactivex.observers.DisposableObserver;

/**
 * @author：admin on 2017/4/18 15:14.
 */

public abstract class ErrorDisposableObserver<T> extends DisposableObserver<T> {


    @Override
    public void onError(Throwable e) {
        NetWorkCodeException.ResponseThrowable responseThrowable = NetWorkCodeException.getResponseThrowable(e);
        Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "Code = " + responseThrowable.code + ", " + responseThrowable.message, Toast.LENGTH_SHORT).show();

        //此处可按状态码解析或error类型，进行分别处理其他error,此处只处理一种
//        if (e instanceof NoNetWorkException) {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.i("Tupperware", "网络连接失败，请重试！");
//        } else if (e instanceof ConnectException) {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "连接失败", Toast.LENGTH_SHORT).show();
//        } else if (e instanceof HttpException) {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
//        } else if (e instanceof JsonParseException
//                || e instanceof JSONException
//                || e instanceof ParseException) {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "解析错误", Toast.LENGTH_SHORT).show();
//        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "证书验证失败", Toast.LENGTH_SHORT).show();
//        } else if (e instanceof io.reactivex.exceptions.CompositeException) {
////            CompositeException composite = (CompositeException) e;
//            List<Throwable> exceptions = ((CompositeException) e).getExceptions();
//            for(int i = 0; i < exceptions.size(); i++) {
//                if(exceptions.get(i) instanceof HttpException) {
//                    String msg = exceptions.get(i).getMessage();
//                    Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), msg, Toast.LENGTH_SHORT).show();
//                    break;
//                } else if(exceptions.get(i) instanceof RuntimeException) {
//                    String msg = exceptions.get(i).getMessage();
//                    Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), msg, Toast.LENGTH_SHORT).show();
//                    break;
//                } else {
//
//                    Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "更新服务器数据错误", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else {
//            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "未知错误", Toast.LENGTH_SHORT).show();
//        }
    }


}
