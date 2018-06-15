package com.tupperware.huishengyi.entity.order;

/**
 * Created by dhunter on 2018/5/16.\
 * 共用ConditionRequest
 */

public class OrderRequest {
    public Condition condition;
    public PageQuery pagingQuery;

    public class Condition{
        private String code;  //门店编码
        private String status;  //订单状态
        private String orderCode;  //订单编码

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
    }

    public class PageQuery{
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
}
