package com.tupperware.biz.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.tupperware.biz.R;

/**
 * Created by dhunter on 2018/11/9.
 */

public class SimpleDialog {
    private Context context;
    private int themeResId;
    private View layout;
    private boolean cancelable = true;
    private CharSequence message,cancelText, sureText;//除了message的所有文本，不写则Gone。
    private View.OnClickListener sureClickListener, cancelClickListener;

    public SimpleDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public SimpleDialog(Context context, int themeResId) {
        this(context, themeResId, ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_dialog_layout, null));

    }

    public SimpleDialog(Context context, int themeResId, View layout) {
        this.context = context;
        this.themeResId = themeResId;
        this.layout = layout;
    }

    //能否返回键取消
    public SimpleDialog setCancelable(Boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public SimpleDialog setmessage(CharSequence message) {
        this.message = message;
        return this;
    }


    //取消按钮文本
    public SimpleDialog setcancelText(CharSequence str) {
        this.cancelText = str;
        return this;
    }

    //确定按钮文本
    public SimpleDialog setsureText(CharSequence str) {
        this.sureText = str;
        return this;
    }

    public SimpleDialog setSureOnClickListener(View.OnClickListener listener) {
        this.sureClickListener = listener;
        return this;
    }

    public SimpleDialog setCancelOnClickListener(View.OnClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public Dialog build() {
        final Dialog dialog = new Dialog(context, themeResId);
        dialog.setCancelable(cancelable);
        dialog.addContentView(layout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //设置显不显示
        setText(message, R.id.message);
        setText(cancelText, R.id.cancel);
        setText(sureText, R.id.sure);
        //一行居中
        final TextView textView = (TextView)layout.findViewById(R.id.message);
        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(textView.getLineCount() ==1){
                    textView.setGravity(Gravity.CENTER);
                }
                return true;
            }
        });
        //设置点击监听
        if (sureClickListener != null) {
            layout.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sureClickListener.onClick(view);
                    dialog.dismiss();
                }
            });
        }
        if (cancelClickListener != null) {
            layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
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

    private void setText(CharSequence text, int id) {
        if (isValid(text)) {
            TextView textView = (TextView) layout.findViewById(id);
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setTextColor(CharSequence text, int id) {
        TextView textView = (TextView) layout.findViewById(id);
        if(View.VISIBLE == textView.getVisibility()) {
            textView.setTextColor(context.getResources().getColor(R.color.color_ff7000));
        }
    }

    private void setCancelTextColor(CharSequence text, int id) {
        TextView textView = (TextView) layout.findViewById(id);
        if(View.VISIBLE == textView.getVisibility()) {
            textView.setTextColor(context.getResources().getColor(R.color.color_43484b));
        }
    }

    private boolean isValid(CharSequence text) {
        return text != null && !"".equals(text.toString().trim());
    }
}
