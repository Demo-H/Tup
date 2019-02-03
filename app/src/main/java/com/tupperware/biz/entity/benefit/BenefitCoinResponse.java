package com.tupperware.biz.entity.benefit;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/11/1.
 */

public class BenefitCoinResponse extends BaseData{
    private PageInfo pageInfo;

    public class PageInfo {
        private boolean hasNextPage;
        private List<BenefitCoinInfo> list;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<BenefitCoinInfo> getList() {
            return list;
        }

        public void setList(List<BenefitCoinInfo> list) {
            this.list = list;
        }
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
