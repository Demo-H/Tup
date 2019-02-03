package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.MemberAddBean;
import com.tupperware.biz.entity.member.MemberReportBean;

/**
 * Created by dhunter on 2018/6/12.
 */

public class MemberDataContract {

    public interface View {
        void setMemberReportData(MemberReportBean memberReportBean);
        void setTodayNewAddData(MemberAddBean memberAddBean);
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getMemberReportData(Integer storeId);
        void getTodayNewAddData(Integer storeId);
    }
}
