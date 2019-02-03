package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.product.ProductType;

/**
 * Created by dhunter on 2018/10/22.
 */

public class MemberListContract {
    public interface View {
        void setMemberListData(MemberBean memberBean);
        void setMoreMemberListData(MemberBean memberBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
        void setMemberFilterProductType(ProductType bean);
    }

    public interface Presenter {
        void getMemberListData(MemberSearchConditionDTO searchCondition);
        void getMoreMemberListData(MemberSearchConditionDTO searchCondition);
        void getMemberFilterProductType();
    }
}
