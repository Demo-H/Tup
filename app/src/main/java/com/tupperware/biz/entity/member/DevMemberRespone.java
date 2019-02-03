package com.tupperware.biz.entity.member;

import com.tupperware.biz.entity.BaseData;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevMemberRespone extends BaseData{
    private DevMemberModel model;

    public class DevMemberModel{
        private int code;   //0新增粉丝，1粉丝已存在且属于当前门店，2粉丝已存在但属于其他门店 ,
        private String qrcode;
        private FansMember member;

        public class FansMember {
            private long memberId;
            private boolean memberGender;
            private String memberMobile;
            private String redundantCurrentStoreName;
            private int groupId;

            public long getMemberId() {
                return memberId;
            }

            public void setMemberId(long memberId) {
                this.memberId = memberId;
            }

            public boolean isMemberGender() {
                return memberGender;
            }

            public void setMemberGender(boolean memberGender) {
                this.memberGender = memberGender;
            }

            public String getMemberMobile() {
                return memberMobile;
            }

            public void setMemberMobile(String memberMobile) {
                this.memberMobile = memberMobile;
            }

            public String getRedundantCurrentStoreName() {
                return redundantCurrentStoreName;
            }

            public void setRedundantCurrentStoreName(String redundantCurrentStoreName) {
                this.redundantCurrentStoreName = redundantCurrentStoreName;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public FansMember getMember() {
            return member;
        }

        public void setMember(FansMember member) {
            this.member = member;
        }
    }

    public DevMemberModel getModel() {
        return model;
    }

    public void setModel(DevMemberModel model) {
        this.model = model;
    }
}
