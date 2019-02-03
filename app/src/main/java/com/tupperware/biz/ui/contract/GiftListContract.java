package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.GiftBean;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftListContract {
    public interface View {
        void setGiftListData(GiftBean mBean);
        void setMoreGiftListData(GiftBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getGiftListData(Integer memberId);
        void getMoreGiftListData(Integer memberId, int indexPage);
    }
}
