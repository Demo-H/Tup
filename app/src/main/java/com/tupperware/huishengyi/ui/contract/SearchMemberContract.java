package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.MemberBean;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchMemberContract {
    public interface View {
        void setMemberSearchData(MemberBean memberBean);
        void setMoreMemberSearchData(MemberBean memberBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
    }

    public interface Presenter {
        void getMemberSearchData(String memberCode);
        void getMoreMemberSearchData(String memberCode, int pageIndex);
    }
}
