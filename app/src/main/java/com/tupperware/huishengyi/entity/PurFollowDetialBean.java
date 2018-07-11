package com.tupperware.huishengyi.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/30.
 * 意向客户跟进数据属性
 * 与一般
 */

public class PurFollowDetialBean {

    private boolean success;
    private String message;
    private PageInfo pageInfo;

    public class PageInfo {
        private int pageNum; //第几页
        private int pageSize; // 每页显示多少
        private int size;    //多少个
        private boolean hasNextPage;
        private List<PurFollowMember> list;

        public class PurFollowMember {
            private long id;
            private String employeeCode;
            private String userName;
            private String mobile;
            private String nickname;
            private String headimgurl;


            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getEmployeeCode() {
                return employeeCode;
            }

            public void setEmployeeCode(String employeeCode) {
                this.employeeCode = employeeCode;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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
