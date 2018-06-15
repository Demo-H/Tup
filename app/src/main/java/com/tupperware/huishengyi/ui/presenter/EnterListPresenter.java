package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.saleenter.PostEnterBean;
import com.tupperware.huishengyi.entity.saleenter.ResponeBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.EnterListContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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
