package com.tupperware.biz.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.utils.StringUtils;

/**
 * Created by dhunter on 2018/3/23.
 */

public class FunctionDialog {
    private Context context;
    private int themeResId;
    private View layout;
    private View.OnClickListener sureClickListener, cancelClickListener;
    private ImageView mStockAdd, mStockReduce, mSaleAdd, mSaleReduce;
    private EditText mStockText, mSaleText;
    private String mStockConunt, mSaleCount;
    private SimpleDraweeView mProductImage;
    private TextView mProductName;
    private TextView mProductCode;
    private TextWatcher mStockWatcher, mSaleWatcher;

    public FunctionDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public FunctionDialog(Context context, int themeResId) {
        this(context, themeResId, ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_function_layout, null));

    }

    public FunctionDialog(Context context, int themeResId, View layout) {
        this.context = context;
        this.themeResId = themeResId;
        this.layout = layout;

        mProductImage = (SimpleDraweeView) layout.findViewById(R.id.product_img);
        mProductName = (TextView) layout.findViewById(R.id.title_goods);
        mProductCode = (TextView) layout.findViewById(R.id.goods_number);

        mStockAdd = (ImageView) layout.findViewById(R.id.inventory_in_count_add);
        mStockReduce = (ImageView) layout.findViewById(R.id.inventory_in_count_reduce);
        mSaleAdd = (ImageView) layout.findViewById(R.id.goods_in_count_add);
        mSaleReduce = (ImageView) layout.findViewById(R.id.goods_in_count_reduce);
        mStockText = (EditText) layout.findViewById(R.id.inventory_in_count_text);
        mSaleText = (EditText) layout.findViewById(R.id.goods_in_count_text);
        mStockAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStockConunt = mStockText.getText().toString().trim();
                mStockText.setText(StringUtils.StringChangeToInt(mStockConunt) + 1 + "");
            }
        });
        mStockReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStockConunt = mStockText.getText().toString().trim();
                if(StringUtils.StringChangeToInt(mStockConunt) > 0) {
                    mStockText.setText(StringUtils.StringChangeToInt(mStockConunt) - 1 + "");
                } else {
                    mStockText.setText("0");
                }
            }
        });
        mSaleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaleCount = mSaleText.getText().toString().trim();
                mSaleText.setText(StringUtils.StringChangeToInt(mSaleCount) + 1 + "");
            }
        });
        mSaleReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaleCount = mSaleText.getText().toString().trim();
                if(StringUtils.StringChangeToInt(mSaleCount) > 0) {
                    mSaleText.setText(StringUtils.StringChangeToInt(mSaleCount) - 1 + "");
                } else {
                    mSaleText.setText("0");
                }
            }
        });
    }


    public FunctionDialog setStockWatcherListener(TextWatcher mStockWatcher) {
        this.mStockWatcher = mStockWatcher;
        return this;
    }

    public FunctionDialog setSaleWatcherListener(TextWatcher mSaleWatcher) {
        this.mSaleWatcher = mSaleWatcher;
        return this;
    }

    public FunctionDialog setSureOnClickListener(View.OnClickListener listener) {
        this.sureClickListener = listener;
        return this;
    }

    public FunctionDialog setCancelOnClickListener(View.OnClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }


    public FunctionDialog setInvText(int count) {
        mStockText.setText(count + "");
        return this;
    }

    public FunctionDialog setShipText(int count) {
        mSaleText.setText(count + "");
        return this;
    }

    public FunctionDialog setProductImage(String url) {
        mProductImage.setImageURI(url);
        return this;
    }

    public FunctionDialog setProductName(String name) {
        mProductName.setText(name);
        return this;
    }

    public FunctionDialog setProductCode(String code) {
        mProductCode.setText("货号：" + code);
        return this;
    }

    public String getInvText() {
        return mStockText.getText().toString().trim();
    }

    public String getShipText() {
        return mSaleText.getText().toString().trim();
    }


    public Dialog build() {
        final Dialog dialog = new Dialog(context, themeResId);
        dialog.addContentView(layout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //设置显不显示
        dialog.setCancelable(false);
        //设置点击监听
        if (sureClickListener != null) {
            layout.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
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

        if(mStockWatcher != null) {
            ((EditText)layout.findViewById(R.id.inventory_in_count_text)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mStockWatcher.onTextChanged(s, start, before, count);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        if(mSaleWatcher != null) {
            ((EditText)layout.findViewById(R.id.goods_in_count_text)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mSaleWatcher.onTextChanged(s, start, before, count);
                }

                @Override
                public void afterTextChanged(Editable s) {

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
