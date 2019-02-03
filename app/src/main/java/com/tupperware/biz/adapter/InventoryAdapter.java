package com.tupperware.biz.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.android.dhunter.common.widget.progressbar.BGAProgressBar;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.hotsale.HotInventoryInfo;
import com.tupperware.biz.utils.DateFormatter;

/**
 * Created by dhunter on 2018/11/30.
 */

public class InventoryAdapter extends BaseQuickAdapter<HotInventoryInfo, BaseViewHolder> {

    private Animation leftAlphaAnimation = null;
    private Animation rightAlphaAnimation = null;
    private TranslateAnimation expandAlphaAnimation = null;
//    private TranslateAnimation hideAlphaAnimation = null;

    public InventoryAdapter(Context mContext, int layoutResId) {
        super(layoutResId);
        leftAlphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.left_rotate);
        rightAlphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.right_rotate);
        expandAlphaAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        expandAlphaAnimation.setDuration(300);
//        hideAlphaAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
//                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f);
//        hideAlphaAnimation.setDuration(500);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switchHideAndExpandChild(view);
                return false;
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, HotInventoryInfo item, int position) {
        helper.setText(R.id.parent_title, item.getGoodsName());

        helper.setText(R.id.begin_inventory, item.getOriginalStock() + "");
        helper.setText(R.id.new_update_time, DateFormatter.timestampToDate(item.getStockUpdateTime()));
        helper.setText(R.id.total_income_count, item.getJhl() + "");
        helper.setText(R.id.month_income_count, item.getMonthJhl() + "");
        helper.setText(R.id.total_outcome_count, item.getXsl() + "");
        helper.setText(R.id.month_outcome_count, item.getMonthXsl() + "");
        helper.setText(R.id.surplus_count, (item.getOriginalStock() + item.getJhl() - item.getXsl()) + "");
        if(item.getJhl() > 0) {
            long percentage = Math.round((double) 100 * item.getXsl() / item.getJhl());
            helper.setText(R.id.action_out_proportion, percentage + "%");
            if(percentage > 100) {
                ((BGAProgressBar) helper.getView(R.id.progressbar)).setProgress(100);
            } else {
                ((BGAProgressBar) helper.getView(R.id.progressbar)).setProgress((int)percentage);
            }
        } else {
            if(item.getXsl() > 0) {
                helper.setText(R.id.action_out_proportion, "100%");
                ((BGAProgressBar) helper.getView(R.id.progressbar)).setProgress(100);
            } else {
                helper.setText(R.id.action_out_proportion, "0%");
                ((BGAProgressBar) helper.getView(R.id.progressbar)).setProgress(0);
            }
        }
        helper.setText(R.id.sales_cycle, item.getDescription());
        helper.setText(R.id.dec, item.getExplain());

        helper.addOnClickListener(R.id.parent_layout);

    }

    private void switchHideAndExpandChild(View view) {
        if(view.getParent() != null) {
            LinearLayout childView = (LinearLayout) ((View) view.getParent()).findViewById(R.id.child_inventory_layout);
            ImageView switchArrow = (ImageView) view.findViewById(R.id.switch_arrow);
            if (childView.getVisibility() == View.GONE) {
                childView.startAnimation(expandAlphaAnimation);
                childView.setVisibility(View.VISIBLE);
                switchArrow.startAnimation(leftAlphaAnimation);
            } else if(childView.getVisibility() == View.VISIBLE){
//                childView.startAnimation(hideAlphaAnimation);
                childView.setVisibility(View.GONE);
                switchArrow.startAnimation(rightAlphaAnimation);
            }
        }
    }
}
