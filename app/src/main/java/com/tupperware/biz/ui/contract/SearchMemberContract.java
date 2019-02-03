package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;

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
        void reLogin();
    }

    public interface Presenter {
        void getMemberSearchData(MemberSearchConditionDTO searchCondition);
        void getMoreMemberSearchData(MemberSearchConditionDTO searchCondition);
    }
}
