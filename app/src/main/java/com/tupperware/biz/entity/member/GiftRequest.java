package com.tupperware.biz.entity.member;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftRequest {
    private String memberId;
    private int pageNo;
    private int pageSize;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
