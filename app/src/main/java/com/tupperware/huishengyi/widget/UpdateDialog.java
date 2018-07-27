package com.tupperware.huishengyi.widget;

import android.app.ProgressDialog;
import android.content.Context;

import com.tupperware.huishengyi.R;

/**
 * Created by dhunter on 2018/7/20.
 */

public class UpdateDialog {
    private Context context;
    private ProgressDialog progressDialog;

    public UpdateDialog(Context context) {
        this.context = context;
    }

    public void showHorizontal() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIcon(R.mipmap.ic_launcher); // 设置图标
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false); // 不能够返回
        progressDialog.setTitle("版本更新"); // 不设置标题的话图标不会显示
        progressDialog.setMessage("下载进度");
        progressDialog.setCanceledOnTouchOutside(false); // 点击外部返回
//        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                progressDialog.dismiss();
//            }
//        });
        progressDialog.show();
    }
    public void setProgress(int progress){
        if(progressDialog!=null){
            progressDialog.setProgress(progress);
        }
    }
    public void dismissDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
