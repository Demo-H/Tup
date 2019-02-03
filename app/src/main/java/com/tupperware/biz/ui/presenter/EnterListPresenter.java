package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.saleenter.PostEnterBean;
import com.tupperware.biz.entity.saleenter.ResponeBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.EnterListContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/5.
 */

public class EnterListPresenter extends BasePresenter implements EnterListContract.Presenter {

    private static final String TAG = "EnterListPresenter";
    private ProductDataManager mDataManager;
    private EnterListContract.View mView;

    @Inject
    public EnterListPresenter(ProductDataManager mDataManager, EnterListContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void postSaleList(PostEnterBean postEnterBean) {
        if(postEnterBean == null) {
            mView.toast("还有没有录入清单");
        } else {
            addDisposabe(mDataManager.postEnterListData(new ErrorDisposableObserver<ResponeBean>() {
                @Override
                public void onNext(ResponeBean responeBean) {
                    LogF.i(TAG, ObjectUtil.jsonFormatter(responeBean));
                    if (!responeBean.success) {
                        mView.toast(responeBean.message);
                        if(responeBean.resultCode != null && (responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        }
                    } else {
                        mView.postSuccess();
                    }
                }

                @Override
                public void onComplete() {

                }
            }, postEnterBean));
        }
    }
}
