package com.tupperware.huishengyi.entity.college;

import java.util.List;

/**
 * Created by dhunter on 2018/5/2.
 * 共用有条件的请求
 */

public class ConditionRequest {
    public Condition condition;
    public PageQuery pagingQuery;   //按页分进行更多请求
    public long tagId;
    public TimelineQuery timelineQuery;  //按时间分进行更多请求

    private List<String> tagCodes;


    public static class Condition{
        public int tagId;
        private String code;  //门店编码
        private String status;  //订单状态
        private String orderCode;  //订单编码
        private String mobile;
        private String storeCode;

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }
    }

    public static class PageQuery{
        public int pageIndex;   //第几页
        public int pageSize;    //每页显示个数
        public int totalRows;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }
    }

    public static class TimelineQuery {
        public int pageSize;
        public long timestamp;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public TimelineQuery getTimelineQuery() {
        return timelineQuery;
    }

    public void setTimelineQuery(TimelineQuery timelineQuery) {
        this.timelineQuery = timelineQuery;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public PageQuery getPagingQuery() {
        return pagingQuery;
    }

    public void setPagingQuery(PageQuery pagingQuery) {
        this.pagingQuery = pagingQuery;
    }

    public List<String> getTagCodes() {
        return tagCodes;
    }

    public void setTagCodes(List<String> tagCodes) {
        this.tagCodes = tagCodes;
    }
}
