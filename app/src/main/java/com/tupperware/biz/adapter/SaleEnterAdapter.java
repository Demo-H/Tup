package com.tupperware.biz.adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.saleenter.SaleEnterContent;
import com.tupperware.biz.utils.StringUtils;
import com.tupperware.biz.utils.data.ProductHistoryProvider;
import com.tupperware.biz.utils.data.ProductProvider;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleEnterAdapter extends BaseQuickAdapter<SaleEnterContent, BaseViewHolder>{

    private EditText mStockText, mSaleText;
    private String mStockConunt, mSaleCount;
    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private IListChangeListener mChangeListener;
    private boolean isHistory;

    public SaleEnterAdapter(int layoutResId, Context context, boolean isHistory) {
        super(layoutResId);
        this.isHistory = isHistory;
        if(isHistory) {
            mHistoryInstance = ProductHistoryProvider.getInstance(context.getApplicationContext());
        } else {
            mInstance = ProductProvider.getInstance(context.getApplicationContext());
        }

    }

    @Override
    protected void convert(final BaseViewHolder helper, final SaleEnterContent item, final int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.product_img);
        simpleDraweeView.setImageURI(item.url);
        helper.setText(R.id.title_goods, item.name);
        helper.setText(R.id.goods_number, item.code);
        if(isHistory) {
            if (mHistoryInstance.isExitList(item)) {
                helper.setText(R.id.inventory_in_count_text, item.localStockNum + "");
                helper.setText(R.id.goods_in_count_text, item.localSaleNum + "");
            } else {
                helper.setText(R.id.inventory_in_count_text, item.stockNum + "");
                helper.setText(R.id.goods_in_count_text, item.saleNum + "");
            }
        } else {
            if (mInstance.isExitList(item)) {
                helper.setText(R.id.inventory_in_count_text, item.localStockNum + "");
                helper.setText(R.id.goods_in_count_text, item.localSaleNum + "");
            } else {
                helper.setText(R.id.inventory_in_count_text, item.stockNum + "");
                helper.setText(R.id.goods_in_count_text, item.saleNum + "");
            }
        }

        helper.getView(R.id.inventory_in_count_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStockText = helper.getView(R.id.inventory_in_count_text);
                mStockConunt = mStockText.getText().toString().trim();
                mStockText.setText(StringUtils.StringChangeToInt(mStockConunt) + 1 + "");
            }
        });

        helper.getView(R.id.inventory_in_count_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStockText = helper.getView(R.id.inventory_in_count_text);
                mStockConunt = mStockText.getText().toString().trim();
                if(StringUtils.StringChangeToInt(mStockConunt) > 0) {
                    mStockText.setText(StringUtils.StringChangeToInt(mStockConunt) - 1 + "");
                } else {
                    mStockText.setText("0");
                }
            }
        });
        helper.getView(R.id.goods_in_count_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaleText = helper.getView(R.id.goods_in_count_text);
                mSaleCount = mSaleText.getText().toString().trim();
                mSaleText.setText(StringUtils.StringChangeToInt(mSaleCount) + 1 + "");
            }
        });
        helper.getView(R.id.goods_in_count_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaleText = helper.getView(R.id.goods_in_count_text);
                mSaleCount = mSaleText.getText().toString().trim();
                if(StringUtils.StringChangeToInt(mSaleCount) > 0) {
                    mSaleText.setText(StringUtils.StringChangeToInt(mSaleCount) - 1 + "");
                } else {
                    mSaleText.setText("0");
                }
            }
        });
        ((EditText)helper.getView(R.id.inventory_in_count_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSaleText = helper.getView(R.id.goods_in_count_text);
                mSaleCount = mSaleText.getText().toString().trim();
                if(!s.toString().equals(item.stockNum + "") || !mSaleCount.equals(item.saleNum + "")) {
                    item.setLocalStockNum(StringUtils.StringChangeToInt(s.toString()));
                    item.setLocalSaleNum(StringUtils.StringChangeToInt(mSaleCount));
                    if(isHistory) {
                        mHistoryInstance.put(item);
                    } else {
                        mInstance.put(item);
                    }
                } else {
                    if(isHistory) {
                        mHistoryInstance.delete(item);
                    } else {
                        mInstance.delete(item);
                    }
                }
                mChangeListener.onChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText)helper.getView(R.id.goods_in_count_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStockText = helper.getView(R.id.inventory_in_count_text);
                mStockConunt = mStockText.getText().toString().trim();
                if(!s.toString().equals(item.saleNum + "") || !mStockConunt.equals(item.stockNum + "")) {
                    item.setLocalStockNum(StringUtils.StringChangeToInt(mStockConunt));
                    item.setLocalSaleNum(StringUtils.StringChangeToInt(s.toString()));
                    if(isHistory) {
                        mHistoryInstance.put(item);
                    } else {
                        mInstance.put(item);
                    }
                } else {
                    if(isHistory) {
                        mHistoryInstance.delete(item);
                    } else {
                        mInstance.delete(item);
                    }
                }
                mChangeListener.onChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public interface IListChangeListener{
        public void onChange();
    }

    public void setOnChangeListener(IListChangeListener mChangeListener) {
        this.mChangeListener = mChangeListener;
    }

}
