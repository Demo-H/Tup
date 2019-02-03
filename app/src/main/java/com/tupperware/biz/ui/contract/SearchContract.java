package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.order.OrderBean;

/**
 * Created by dhunter on 2018/5/23.
 */

public class SearchContract {

    public interface View {
        void setOrderSearchData(OrderBean searchData);
        void setMemberSearchData(MemberBean memberBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getOrderSearchData(String code, String orderCode);
        void getMemberSearchData(String memberCode);
    }
}
