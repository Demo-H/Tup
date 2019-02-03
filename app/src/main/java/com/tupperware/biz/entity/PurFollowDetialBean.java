package com.tupperware.biz.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/30.
 * 意向客户跟进数据属性
 * 与一般
 */

public class PurFollowDetialBean extends BaseData{

    private PageInfo pageInfo;

    public class PageInfo {
        private int pageNum; //第几页
        private int pageSize; // 每页显示多少
        private int size;    //多少个
        private boolean hasNextPage;
        private List<PurFollowMember> list;

        public class PurFollowMember {
            private Integer memberId;
            private boolean memberStatus;
            private String memberName;
            private String memberMobile;
            private boolean memberGender;
            private long currentStoreId;
            private String headimgurl;

            public Integer getMemberId() {
                return memberId;
            }

            public void setMemberId(Integer memberId) {
                this.memberId = memberId;
            }

            public boolean isMemberStatus() {
                return memberStatus;
            }

            public void setMemberStatus(boolean memberStatus) {
                this.memberStatus = memberStatus;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberMobile() {
                return memberMobile;
            }

            public void setMemberMobile(String memberMobile) {
                this.memberMobile = memberMobile;
            }

            public boolean isMemberGender() {
                return memberGender;
            }

            public void setMemberGender(boolean memberGender) {
                this.memberGender = memberGender;
            }

            public long getCurrentStoreId() {
                return currentStoreId;
            }

            public void setCurrentStoreId(long currentStoreId) {
                this.currentStoreId = currentStoreId;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<PurFollowMember> getList() {
            return list;
        }

        public void setList(List<PurFollowMember> list) {
            this.list = list;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
