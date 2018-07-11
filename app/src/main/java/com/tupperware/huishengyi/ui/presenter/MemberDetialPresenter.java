package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.contract.MemberDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/31.
 */

public class MemberDetialPresenter extends BasePresenter implements MemberDetialContract.Presenter {

    private static final String TAG = "MemberDetialPresenter";

    private MemberDataManager mDataManager;
    private MemberDetialContract.View mView;

    @Inject
    public MemberDetialPresenter(MemberDataManager mDataManager, MemberDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberDetialData(String mobileNum) {
        addDisposabe(mDataManager.getMemberDetialData(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                mView.hideDialog();
                if(memberBean != null) {
                    if(memberBean.isSuccess()) {
                        mView.refreshUIData(memberBean);
                    } else {
                        mView.toast(memberBean.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, mobileNum));
    }
}
