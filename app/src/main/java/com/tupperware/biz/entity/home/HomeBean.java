package com.tupperware.biz.entity.home;

import com.android.dhunter.common.baserecycleview.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.tupperware.biz.config.Constant;

import java.util.List;

/**
 * Created by dhunter on 2018/4/24.
 * 获取从服务器返回的首页数据
 */

public class HomeBean {

    public static HomeBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, HomeBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean success;
    public String resultCode;
    public String message;
    public Model model;

    public static class Model implements MultiItemEntity  {
        public List<HeadlineModel> headline;
        public List<ArticleModel> article;
        public List<IconModel> icon;
        public List<BusinessModel> school;

        public static class HeadlineModel {
            public int id;
            public String title;
            public String subTitle;
            public String imgUrl;
            public String link;

        }

        public static class ArticleModel {
            public int id;
            public String title;
            public String subTitle;
            public String imgUrl;
            public String link;
        }

        public static class IconModel {
            public int image;
            public String itemTitle;
        }

        public static class BusinessModel{
            public int id;
            public int image;
            public String title;
            public String imgUrl;
            public int readNum;
            public String link;
            public int forwardNum;
            public String author;
        }

        @Override
        public int getItemType() {
            return Constant.TYPE_TOP_BANNER;
        }

        public int getSpanSize() {
            return 4;
        }

    }
}
