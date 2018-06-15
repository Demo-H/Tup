package com.tupperware.huishengyi.entity.college;

import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/2.
 */

public class CollegeBean extends BaseData {

    public List<CollegeModel> models;
    public PageInfo pageInfo;

    public static class CollegeModel{
        public long id;
        public String title;
        public int readNum;
        public String imgUrl;
        public String link;
        public String answerName;
        public int likeNum;
        public int learnNum;
        public int refId;  //关联ID
    }

    public static class PageInfo{
        public int pageNum;
        public int pageSize;
    }

}
