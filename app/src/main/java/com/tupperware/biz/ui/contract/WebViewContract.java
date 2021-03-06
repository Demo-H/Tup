package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.college.LikeBean;
import com.tupperware.biz.entity.saleenter.ImportantBean;

/**
 * Created by dhunter on 2018/7/6.
 */

public class WebViewContract {

    public interface View {
        void toast(String msg);
        void hideDialog();
        void jumpToImportSalePage(ImportantBean importantBean);
        void setIsLike(LikeBean mLikeBean);
    }

    public interface Presenter {
        void asynceImportSaleUrl();
        void getIsLike(long answerId);
        void setLike(long answerId);
        void cancelLike(long answerId);
    }

}
