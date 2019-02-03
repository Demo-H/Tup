package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.PurFollowDetialBean;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialContract {
    public interface View {
        void setPurFollowDetialData(PurFollowDetialBean purFollowDetialBean);
        void setMorePurFollowDetialData(PurFollowDetialBean purFollowDetialBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getPurFollowDetialData(String tagCodes, Integer currentStoreId);
        void getMorePurFollowDetialData(String tagCodes, Integer currentStoreId, int pageIndex);
    }
}
