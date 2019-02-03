package com.tupperware.biz.entity.member;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindMemberResponse extends BaseData {

    public PageInfo pageInfo;

    public class PageInfo{
        private boolean hasNextPage;
        private List<RemindMemberInfo> list;
        public int total;
        public int pages;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<RemindMemberInfo> getList() {
            return list;
        }

        public void setList(List<RemindMemberInfo> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
