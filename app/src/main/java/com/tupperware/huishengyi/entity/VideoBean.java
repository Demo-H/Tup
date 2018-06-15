package com.tupperware.huishengyi.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/6.
 */

public class VideoBean {
    public String typeName;
    public int updatedNum;
    public String endArticleTime;
    public String code;
    public int typeId;
    public int costTime;
    public String topArticleTime;
    public List<ContentBean> content;

    public static class ContentBean {
        public int articleId;
        public String authorName;
        public String authorPic;
        public String indexImage;
        public int pageView;
        public long publishTime;
        public String showTime;
        public String srv;
        public String summary;
        public String testId;
        public String title;
        public int top;
        public String type;
        public int videoFlag;
        public List<?> tagsList;
    }
}
