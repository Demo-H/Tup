package com.tupperware.huishengyi.entity.member;

import com.tupperware.huishengyi.entity.BaseData;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/6/12.
 */

public class MemberReportBean extends BaseData implements Serializable{

    private MemberReportModel model;

    public static class MemberReportModel implements Serializable{
//        private int addFansCount;   //今日新增粉丝数
//        private int addCommonMemberCount;  //今日新增普通会员数
//        private int addVipMemberCount;  //今日新增高级会员数
        private FansStatistics fansStatisticsVO;
        private CommonMemberStatistics commonMemberStatisticsVO;
        private VipMemberStatistics vipMemberStatisticsVO;

        public static class FansStatistics implements Serializable{
            private int monthFansTargetCount;
            private int monthFansCount;
            private int monthAddFansCount;
            private int quarterFansTargetCount;
            private int quarterFansCount;
            private int quarterAddFansCount;
            private int yearFansTargetCount;
            private int yearFansCount;
            private int yearAddFansCount;

            public int getMonthFansTargetCount() {
                return monthFansTargetCount;
            }

            public void setMonthFansTargetCount(int monthFansTargetCount) {
                this.monthFansTargetCount = monthFansTargetCount;
            }

            public int getMonthFansCount() {
                return monthFansCount;
            }

            public void setMonthFansCount(int monthFansCount) {
                this.monthFansCount = monthFansCount;
            }

            public int getMonthAddFansCount() {
                return monthAddFansCount;
            }

            public void setMonthAddFansCount(int monthAddFansCount) {
                this.monthAddFansCount = monthAddFansCount;
            }

            public int getQuarterFansTargetCount() {
                return quarterFansTargetCount;
            }

            public void setQuarterFansTargetCount(int quarterFansTargetCount) {
                this.quarterFansTargetCount = quarterFansTargetCount;
            }

            public int getQuarterFansCount() {
                return quarterFansCount;
            }

            public void setQuarterFansCount(int quarterFansCount) {
                this.quarterFansCount = quarterFansCount;
            }

            public int getQuarterAddFansCount() {
                return quarterAddFansCount;
            }

            public void setQuarterAddFansCount(int quarterAddFansCount) {
                this.quarterAddFansCount = quarterAddFansCount;
            }

            public int getYearFansTargetCount() {
                return yearFansTargetCount;
            }

            public void setYearFansTargetCount(int yearFansTargetCount) {
                this.yearFansTargetCount = yearFansTargetCount;
            }

            public int getYearFansCount() {
                return yearFansCount;
            }

            public void setYearFansCount(int yearFansCount) {
                this.yearFansCount = yearFansCount;
            }

            public int getYearAddFansCount() {
                return yearAddFansCount;
            }

            public void setYearAddFansCount(int yearAddFansCount) {
                this.yearAddFansCount = yearAddFansCount;
            }
        }

        public static class CommonMemberStatistics implements Serializable {
            private int monthCommonMemberTargetCount;
            private int monthCommonMemberCount;
            private int monthAddCommonMemberCount;
            private int quarterCommonMemberTargetCount;
            private int quarterCommonMemberCount;
            private int quarterAddCommonMemberCount;
            private int yearCommonMemberTargetCount;
            private int yearCommonMemberCount;
            private int yearAddCommonMemberCount;

            public int getMonthCommonMemberTargetCount() {
                return monthCommonMemberTargetCount;
            }

            public void setMonthCommonMemberTargetCount(int monthCommonMemberTargetCount) {
                this.monthCommonMemberTargetCount = monthCommonMemberTargetCount;
            }

            public int getMonthCommonMemberCount() {
                return monthCommonMemberCount;
            }

            public void setMonthCommonMemberCount(int monthCommonMemberCount) {
                this.monthCommonMemberCount = monthCommonMemberCount;
            }

            public int getMonthAddCommonMemberCount() {
                return monthAddCommonMemberCount;
            }

            public void setMonthAddCommonMemberCount(int monthAddCommonMemberCount) {
                this.monthAddCommonMemberCount = monthAddCommonMemberCount;
            }

            public int getQuarterCommonMemberTargetCount() {
                return quarterCommonMemberTargetCount;
            }

            public void setQuarterCommonMemberTargetCount(int quarterCommonMemberTargetCount) {
                this.quarterCommonMemberTargetCount = quarterCommonMemberTargetCount;
            }

            public int getQuarterCommonMemberCount() {
                return quarterCommonMemberCount;
            }

            public void setQuarterCommonMemberCount(int quarterCommonMemberCount) {
                this.quarterCommonMemberCount = quarterCommonMemberCount;
            }

            public int getQuarterAddCommonMemberCount() {
                return quarterAddCommonMemberCount;
            }

            public void setQuarterAddCommonMemberCount(int quarterAddCommonMemberCount) {
                this.quarterAddCommonMemberCount = quarterAddCommonMemberCount;
            }

            public int getYearCommonMemberTargetCount() {
                return yearCommonMemberTargetCount;
            }

            public void setYearCommonMemberTargetCount(int yearCommonMemberTargetCount) {
                this.yearCommonMemberTargetCount = yearCommonMemberTargetCount;
            }

            public int getYearCommonMemberCount() {
                return yearCommonMemberCount;
            }

            public void setYearCommonMemberCount(int yearCommonMemberCount) {
                this.yearCommonMemberCount = yearCommonMemberCount;
            }

            public int getYearAddCommonMemberCount() {
                return yearAddCommonMemberCount;
            }

            public void setYearAddCommonMemberCount(int yearAddCommonMemberCount) {
                this.yearAddCommonMemberCount = yearAddCommonMemberCount;
            }
        }

        public static class VipMemberStatistics implements Serializable{
            private int monthVipMemberTargetCount;
            private int monthVipMemberCount;
            private int monthAddVipMemberCount;
            private int quarterVipMemberTargetCount;
            private int quarterVipMemberCount;
            private int quarterAddVipMemberCount;
            private int yearVipMemberTargetCount;
            private int yearVipMemberCount;
            private int yearAddVipMemberCount;

            public int getMonthVipMemberTargetCount() {
                return monthVipMemberTargetCount;
            }

            public void setMonthVipMemberTargetCount(int monthVipMemberTargetCount) {
                this.monthVipMemberTargetCount = monthVipMemberTargetCount;
            }

            public int getMonthVipMemberCount() {
                return monthVipMemberCount;
            }

            public void setMonthVipMemberCount(int monthVipMemberCount) {
                this.monthVipMemberCount = monthVipMemberCount;
            }

            public int getMonthAddVipMemberCount() {
                return monthAddVipMemberCount;
            }

            public void setMonthAddVipMemberCount(int monthAddVipMemberCount) {
                this.monthAddVipMemberCount = monthAddVipMemberCount;
            }

            public int getQuarterVipMemberTargetCount() {
                return quarterVipMemberTargetCount;
            }

            public void setQuarterVipMemberTargetCount(int quarterVipMemberTargetCount) {
                this.quarterVipMemberTargetCount = quarterVipMemberTargetCount;
            }

            public int getQuarterVipMemberCount() {
                return quarterVipMemberCount;
            }

            public void setQuarterVipMemberCount(int quarterVipMemberCount) {
                this.quarterVipMemberCount = quarterVipMemberCount;
            }

            public int getQuarterAddVipMemberCount() {
                return quarterAddVipMemberCount;
            }

            public void setQuarterAddVipMemberCount(int quarterAddVipMemberCount) {
                this.quarterAddVipMemberCount = quarterAddVipMemberCount;
            }

            public int getYearVipMemberTargetCount() {
                return yearVipMemberTargetCount;
            }

            public void setYearVipMemberTargetCount(int yearVipMemberTargetCount) {
                this.yearVipMemberTargetCount = yearVipMemberTargetCount;
            }

            public int getYearVipMemberCount() {
                return yearVipMemberCount;
            }

            public void setYearVipMemberCount(int yearVipMemberCount) {
                this.yearVipMemberCount = yearVipMemberCount;
            }

            public int getYearAddVipMemberCount() {
                return yearAddVipMemberCount;
            }

            public void setYearAddVipMemberCount(int yearAddVipMemberCount) {
                this.yearAddVipMemberCount = yearAddVipMemberCount;
            }
        }


        public FansStatistics getFansStatisticsVO() {
            return fansStatisticsVO;
        }

        public void setFansStatisticsVO(FansStatistics fansStatisticsVO) {
            this.fansStatisticsVO = fansStatisticsVO;
        }

        public CommonMemberStatistics getCommonMemberStatisticsVO() {
            return commonMemberStatisticsVO;
        }

        public void setCommonMemberStatisticsVO(CommonMemberStatistics commonMemberStatisticsVO) {
            this.commonMemberStatisticsVO = commonMemberStatisticsVO;
        }

        public VipMemberStatistics getVipMemberStatisticsVO() {
            return vipMemberStatisticsVO;
        }

        public void setVipMemberStatisticsVO(VipMemberStatistics vipMemberStatisticsVO) {
            this.vipMemberStatisticsVO = vipMemberStatisticsVO;
        }
    }

    public MemberReportModel getModel() {
        return model;
    }

    public void setModel(MemberReportModel model) {
        this.model = model;
    }
}
