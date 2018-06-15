package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.MemberAddBean;
import com.tupperware.huishengyi.entity.member.MemberReportBean;

/**
 * Created by dhunter on 2018/6/12.
 */

public class MemberDataContract {

    public interface View {
        void setMemberReportData(MemberReportBean memberReportBean);
        void setTodayNewAddData(MemberAddBean memberAddBean);
        void toast(String msg);
        void hideDialog();
    }

    public interface Presenter {
        void getMemberReportData(String storeId);
        void getTodayNewAddData(String storeId);
    }
}
