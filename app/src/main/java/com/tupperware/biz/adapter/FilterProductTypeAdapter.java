package com.tupperware.biz.adapter;

import android.view.View;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.product.ProductType;

/**
 * Created by dhunter on 2018/10/22.
 */

public class FilterProductTypeAdapter extends BaseQuickAdapter<ProductType.ProductItem, BaseViewHolder> {

    private MemberSearchConditionDTO searchCondition = MemberSearchConditionDTO.getInstance();

    public FilterProductTypeAdapter(int layoutResId) {
        super(layoutResId);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                selectProductType(view, getData().get(position).getSalesCatalogId());
                return false;
            }
        });
    }

    @Override
    protected void convert(final BaseViewHolder helper, ProductType.ProductItem item, int position) {
        TextView mProductType = helper.getView(R.id.product_type);
        mProductType.setText(item.getSalesCatalog());
        if(searchCondition.productType.contains(item.getSalesCatalogId())) {
            mProductType.setSelected(true);
        } else {
            mProductType.setSelected(false);
        }

        helper.addOnClickListener(R.id.product_type_item);
    }

    private void selectProductType(View view, String ID) {
        String str1 = ID + ",";
        String str2 = "," + ID;
        if(view.isSelected()) {
            view.setSelected(false);
            if(searchCondition.productType == null || searchCondition.productType.isEmpty()) {
                return;
            } else if(searchCondition.productType.contains(str1)) {
                searchCondition.productType = searchCondition.productType.replace(str1, "");
            } else if(searchCondition.productType.contains(str2)) {
                searchCondition.productType = searchCondition.productType.replace(str2, "");
            } else if(searchCondition.productType.contains(ID)) {
                searchCondition.productType = searchCondition.productType.replace(ID, "");
            }
        } else {
            view.setSelected(true);
            if(searchCondition.productType == null || searchCondition.productType.isEmpty()) {
                searchCondition.productType = ID;
            } else {
                searchCondition.productType += str2;
            }
        }
    }
}
