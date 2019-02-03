package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.PurFollowDetialBean;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ServerManagerDetialContract {
    public interface View {
        void setServerManagerDetialData(PurFollowDetialBean purFollowDetialBean);
    }

    public interface Presenter {
        void getServerManagerDetialData();
    }
}
