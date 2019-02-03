package com.tupperware.biz.entity.msg;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/3/26.
 * 消息查看更多的详情列表
 */

public class MsgItemBean extends BaseData {

    public List<MsgContentBean> models;
    public TimeLineInfo timelineInfo;

    public class MsgContentBean {
        public long id;
        public String title;
        public String subTitle;
        public long tagId;
        public String imgUrl;
        public String link;
        public String jumpLink;
        public int readNum;
        public long createTime;
        public int isRead;
    }

    public class TimeLineInfo {
        public boolean hasNext;
        public long timestamp;
    }
}
