package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.ResOrderPendingBean;

/**
 * Created by dhunter on 2018/4/8.
 */

public class ResOrderServerContract {
    public interface View {
        void setResOrderServerData(ResOrderPendingBean resOrderPending);
        void setMoreResOrderServerData(ResOrderPendingBean resOrderPending);
    }

    public interface Presenter {
        void getResOrderServerData();
        void getMoreResOrderServerData();
    }
}
