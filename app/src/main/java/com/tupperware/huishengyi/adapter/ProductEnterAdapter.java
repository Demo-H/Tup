package com.tupperware.huishengyi.adapter;

import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.ProductEnterBean;
import com.tupperware.huishengyi.widget.FunctionDialog;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterAdapter extends BaseQuickAdapter<ProductEnterBean.ProductEnterContentBean,BaseViewHolder> {

    public ProductEnterAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductEnterBean.ProductEnterContentBean item, int position) {
        helper.setText(R.id.title_goods, item.goodsName);
        SimpleDraweeView imageView = helper.getView(R.id.product_img);
        imageView.setImageResource(R.mipmap.order_prd_img);
        helper.setText(R.id.goods_number, "货号：137210" + position);
        helper.setText(R.id.current_inventory, (position*2 + 20) + "");

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog(view);
            }
        });
    }

    private void showDialog(View view) {
        FunctionDialog dialog = new FunctionDialog(view.getContext());
        dialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.build().show();
    }

}
