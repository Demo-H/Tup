package com.tupperware.huishengyi.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tupperware.huishengyi.R;

/**
 * Created by dhunter on 2018/3/2.
 */

public class CustomDialog {

    private Context context;
    private int themeResId;
    private View layout;
    private boolean cancelable = true;
    private CharSequence title, message, message2, message3, cancelText, sureText;//除了message的所有文本，不写则Gone。
    private int imageResourceId; //默认为gone
    private boolean isshowImage = false;
    private boolean isshowSplit = false;
    private View.OnClickListener sureClickListener, cancelClickListener;

    public CustomDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public CustomDialog(Context context, int themeResId) {
        this(context, themeResId, ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_layout, null));

    }

    public CustomDialog(Context context, int themeResId, View layout) {
        this.context = context;
        this.themeResId = themeResId;
        this.layout = layout;
    }

    //能否返回键取消
    public CustomDialog setCancelable(Boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public CustomDialog settitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public CustomDialog setmessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public CustomDialog setmessage2(CharSequence message2) {
        this.message2 = message2;
        return this;
    }

    public CustomDialog setmessage3(CharSequence message3) {
        this.message3 = message3;
        return this;
    }

    public CustomDialog setImageResource(int imageResource) {
        this.imageResourceId = imageResource;
        return this;
    }

    //设置显示图片
    public CustomDialog setImageShow(boolean isshow) {
        this.isshowImage = isshow;
        return this;
    }

    //设置显示msg1和msg2之间分割线
    public CustomDialog setSplitShow(boolean isshow) {
        this.isshowSplit = isshow;
        return this;
    }


    //取消按钮文本
    public CustomDialog setcancelText(CharSequence str) {
        this.cancelText = str;
        return this;
    }

    //确定按钮文本
    public CustomDialog setsureText(CharSequence str) {
        this.sureText = str;
        return this;
    }

    public CustomDialog setSureOnClickListener(View.OnClickListener listener) {
        this.sureClickListener = listener;
        return this;
    }

    public CustomDialog setCancelOnClickListener(View.OnClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public Dialog build() {
        final Dialog dialog = new Dialog(context, themeResId);
        dialog.setCancelable(cancelable);
        dialog.addContentView(layout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //设置显不显示
        setText(title, R.id.title);
        setImage(imageResourceId, R.id.image);
        setText(message, R.id.message);
        setText(message2, R.id.message2);
        setText(message3, R.id.message3);
        setText(cancelText, R.id.cancel);
        setText(sureText, R.id.sure);
        if (isValid(cancelText) || isValid(sureText)) {
            layout.findViewById(R.id.line2).setVisibility(View.VISIBLE);
            setTextColor(cancelText, R.id.cancel);
            setTextColor(sureText, R.id.sure);
        }
        if (isValid(cancelText) && isValid(sureText)) {
            layout.findViewById(R.id.line).setVisibility(View.VISIBLE);
        }
        if(isValid(message2)) {
            layout.findViewById(R.id.message2).setVisibility(View.VISIBLE);
        }
        if(isValid(message3)) {
            layout.findViewById(R.id.message3).setVisibility(View.VISIBLE);
        }
        if(isshowSplit) {
            layout.findViewById(R.id.line1).setVisibility(View.VISIBLE);
        }
        //没有title时message变大
        if(!isValid(title)){
            ((TextView)layout.findViewById(R.id.message)).setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        }
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

    private void setImage(int imageResourceId, int id) {
        if(isshowImage) {
            ImageView imageView = (ImageView) layout.findViewById(R.id.image);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(imageResourceId);
        }
    }

    private boolean isValid(CharSequence text) {
        return text != null && !"".equals(text.toString().trim());
    }
}
