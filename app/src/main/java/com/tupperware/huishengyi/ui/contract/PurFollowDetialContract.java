package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.PurFollowDetialBean;

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
    }

    public interface Presenter {
        void getPurFollowDetialData(String tagCodes);
        void getMorePurFollowDetialData(String tagCodes, int pageIndex);
    }
}
