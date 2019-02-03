package com.tupperware.biz.ui.contract;

import android.content.Context;
import android.widget.RelativeLayout;

import com.tupperware.biz.entity.member.PersonalQrBean;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalQrContract {
    public interface View {
        void setPersonQrData(PersonalQrBean mBean);
        void toast(String msg);
        void setNetErrorView();
        void setNormalView();
        void setEmptyView();
        void reLogin();
    }

    public interface Presenter {
        void getPersonQrData(String storeCode);
        void saveQrImage(final RelativeLayout mRl, final Context context);
    }
}
