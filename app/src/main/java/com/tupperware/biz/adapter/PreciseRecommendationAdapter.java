package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.android.dhunter.common.widget.imageview.ExpandImageView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.home.HomeIndexBean;
import com.tupperware.biz.ui.activities.BrowserActivity;

import java.util.List;


/**
 * Created by dhunter on 2018/3/12.
 */

public class PreciseRecommendationAdapter extends BaseQuickAdapter<HomeIndexBean.ItemInfoListBean.ItemContentListBean,BaseViewHolder> {



    public PreciseRecommendationAdapter(int layoutResId, List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean.ItemContentListBean item, int position) {

//        ((ExpandImageView) helper.getView(R.id.precise_recommendation_img)).setImageResource(item.image);
//        helper.setText(R.id.precise_recom_description,item.itemTitle);

        ((ExpandImageView) helper.getView(R.id.precise_recommendation_img)).setImageURI(item.imgUrl);
        helper.setText(R.id.precise_recom_description,item.title);
        helper.addOnClickListener(R.id.homerecycle_precise_recommendation_ll);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = getData().get(position).link;
                intent.putExtra(Constant.OPEN_URL,url);
                intent.putExtra(Constant.URL_TITLE,"精准推荐");
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.homerecycle_precise_recommendation_content,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (0.3 * ScreenUtil.getScreenWidth(mContext)), LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return view;

    }



}
