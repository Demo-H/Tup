package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.GiftBean;

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
    }

    public interface Presenter {
        void getGiftListData(String memberId);
        void getMoreGiftListData(String memberId, int indexPage);
    }
}
