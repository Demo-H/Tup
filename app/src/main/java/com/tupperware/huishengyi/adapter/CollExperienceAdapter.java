package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.ui.activities.WebViewActivity;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollExperienceAdapter extends BaseQuickAdapter<CollegeBean.CollegeModel,BaseViewHolder> {


    public CollExperienceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollegeBean.CollegeModel bean , int position) {
        helper.setText(R.id.title_text, bean.title);
        helper.setText(R.id.author_name, bean.answerName);
//        helper.setText(R.id.answer_content, bean.summary);
        helper.setText(R.id.pageView, Html.fromHtml(bean.readNum + "<font color=#9b9b9b>人阅读</font>"));
        helper.setText(R.id.used_count, Html.fromHtml(bean.likeNum + "<font color=#9b9b9b>人觉得有用</font>"));
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.auth_icon);
        simpleDraweeView.setImageURI(bean.imgUrl);

        helper.addOnClickListener(R.id.experience_item_layout);

        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                String url = getData().get(position).link;
                long id = getData().get(position).id;
                int likeNum = getData().get(position).likeNum;
                Bundle bundle = new Bundle();
                bundle.putString(Constant.OPEN_URL,url);
                bundle.putString(Constant.URL_TITLE,"问题详情");
                bundle.putString(Constant.URL_TYPE, Constant.SUPPORT_LIKE);
                bundle.putLong(Constant.ANSWER_ID, id);
                bundle.putInt(Constant.LIKE_COUNT, likeNum);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }

}