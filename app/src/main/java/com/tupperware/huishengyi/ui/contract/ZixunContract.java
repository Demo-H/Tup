package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.ZixunBean;

/**
 * Created by dhunter on 2018/3/5.
 */

public class ZixunContract {
    public interface View {
        void setZixunData(ZixunBean zixun);
        void setMoreZixunData(ZixunBean zixun);
    }

    public interface Presenter {
        void getZixunData();
        void getMoreZixunData();
    }
}
