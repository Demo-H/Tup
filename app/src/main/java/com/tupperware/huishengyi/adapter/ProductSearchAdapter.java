package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterContent;
import com.tupperware.huishengyi.utils.StringUtils;
import com.tupperware.huishengyi.utils.data.ProductHistoryProvider;
import com.tupperware.huishengyi.utils.data.ProductProvider;
import com.tupperware.huishengyi.widget.FunctionDialog;

/**
 * Created by dhunter on 2018/5/29.
 */

public class ProductSearchAdapter extends BaseQuickAdapter<SaleEnterContent, BaseViewHolder> {

    private ProductProvider mInstance;
    private ProductHistoryProvider mHistoryInstance;
    private boolean showloacal = false;
    private boolean isHistory;

    public ProductSearchAdapter(int layoutResId, Context context, boolean isHistroy) {
        super(layoutResId);
        this.isHistory = isHistroy;
        if(isHistroy) {
            mHistoryInstance = ProductHistoryProvider.getInstance(context.getApplicationContext());
        } else {
            mInstance = ProductProvider.getInstance(context.getApplicationContext());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SaleEnterContent item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.product_img);
        if(item.url != null) {
            simpleDraweeView.setImageURI(item.url);
        }
        helper.setText(R.id.goods_name, item.name);
        helper.setText(R.id.goods_code, "货号    " + item.code);
        if(isHistory) {
            if (mHistoryInstance.isExitList(item) && showloacal) {
                helper.setText(R.id.inventory, Html.fromHtml("<font color=#43484b>库存&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.localStockNum));
                helper.setText(R.id.shipment, Html.fromHtml("<font color=#43484b>出货量&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.localSaleNum));
            } else {
                helper.setText(R.id.inventory, Html.fromHtml("<font color=#43484b>库存&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.stockNum));
                helper.setText(R.id.shipment, Html.fromHtml("<font color=#43484b>出货量&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.saleNum));
            }
        } else {
            if (mInstance.isExitList(item) && showloacal) {
                helper.setText(R.id.inventory, Html.fromHtml("<font color=#43484b>库存&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.localStockNum));
                helper.setText(R.id.shipment, Html.fromHtml("<font color=#43484b>出货量&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.localSaleNum));
            } else {
                helper.setText(R.id.inventory, Html.fromHtml("<font color=#43484b>库存&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.stockNum));
                helper.setText(R.id.shipment, Html.fromHtml("<font color=#43484b>出货量&nbsp;&nbsp;&nbsp;&nbsp;</font>" + item.saleNum));
            }
        }
        helper.addOnClickListener(R.id.sale_history_item);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showFunctionDialog(view, getData().get(position));
                return false;
            }
        });
    }

    private void showFunctionDialog(View view, final SaleEnterContent itemContent) {
        final FunctionDialog dialog = new FunctionDialog(view.getContext());
        if(isHistory) {
            if(mHistoryInstance.isExitList(itemContent)) {
                dialog.setInvText(itemContent.localStockNum);
                dialog.setShipText(itemContent.localSaleNum);
            } else {
                dialog.setInvText(itemContent.stockNum);
                dialog.setShipText(itemContent.saleNum);
            }
        } else {
            if (mInstance.isExitList(itemContent)) {
                dialog.setInvText(itemContent.localStockNum);
                dialog.setShipText(itemContent.localSaleNum);
            } else {
                dialog.setInvText(itemContent.stockNum);
                dialog.setShipText(itemContent.saleNum);
            }
        }
        dialog.setProductImage(itemContent.url);
        dialog.setProductName(itemContent.name);
        dialog.setProductCode(itemContent.code);
        dialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int localStockNum = StringUtils.StringChangeToInt(dialog.getInvText());
                int localSaleNum = StringUtils.StringChangeToInt(dialog.getShipText());
                if(localStockNum != (itemContent.stockNum) || localSaleNum != (itemContent.saleNum)) {
                    itemContent.setLocalStockNum(localStockNum);
                    itemContent.setLocalSaleNum(localSaleNum);
                    if(isHistory) {
                        mHistoryInstance.put(itemContent);
                    } else {
                        mInstance.put(itemContent);
                    }
                } else {
                    if(isHistory) {
                        mHistoryInstance.delete(itemContent);
                    } else {
                        mInstance.delete(itemContent);
                    }
                }
                showloacal = true;
                notifyDataSetChanged();
            }
        });
        dialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.build().show();
    }
}
