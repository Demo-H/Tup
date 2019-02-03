package com.tupperware.biz.entity.msg;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/22.
 */

public class MsgBean extends BaseData {

    public List<MsgModel> models;

    public class MsgModel{
        public long id;
        public String name;
        public int num;
        public int unReadNum;
        public MsgVoItem messageVO;
        public class MsgVoItem{
            public long id;
            public String title;
            public String subTitle;
            public long tagId;
            public String imgUrl;
            public String link;
            public String jumpLink;
            public int readNum;
            public long createTime;
        }
    }
}
