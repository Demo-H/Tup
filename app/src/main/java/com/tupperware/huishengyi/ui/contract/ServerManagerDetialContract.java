package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.PurFollowDetialBean;

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
