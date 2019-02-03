package com.tupperware.biz.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.tupperware.biz.R;

/**
 * Created by dhunter on 2018/3/26.
 */

public class SystemDialog {
    private Context context;
    private int themeResId;
    private View layout;
    private View.OnClickListener sureClickListener, cancelClickListener;

    public SystemDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public SystemDialog(Context context, int themeResId) {
        this(context, themeResId, ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_system_layout, null));

    }

    public SystemDialog(Context context, int themeResId, View layout) {
        this.context = context;
        this.themeResId = themeResId;
        this.layout = layout;
    }


    public SystemDialog setSureOnClickListener(View.OnClickListener listener) {
        this.sureClickListener = listener;
        return this;
    }

    public SystemDialog setCancelOnClickListener(View.OnClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }


    public Dialog build() {
        final Dialog dialog = new Dialog(context, themeResId);
        dialog.addContentView(layout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //设置显不显示

        //设置点击监听
//        if (sureClickListener != null) {
//            layout.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sureClickListener.onClick(view);
//                    dialog.dismiss();
//                }
//            });
//        }

        if (cancelClickListener != null) {
            layout.findViewById(R.id.cancel_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelClickListener.onClick(view);
                    dialog.dismiss();
                }
            });
        }
        //设置宽度
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        dialog.getWindow().setAttributes(params);
        return dialog;
    }
}
